package com.alura.literatura.principal;
import com.alura.literatura.model.*;
import com.alura.literatura.repository.IAutorRepository;
import com.alura.literatura.repository.ILibrosRepository;
import com.alura.literatura.service.ConsumoApi;
import com.alura.literatura.service.ConvierteDatos;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class Principal {
    private static final String URL_BASE = "https://gutendex.com/books/";
    private static final String URL_BUSQUEDA = "?search=";
    private ConsumoApi consumoApi = new ConsumoApi();
    private ConvierteDatos conversor = new ConvierteDatos();
    private Scanner teclado = new Scanner(System.in);
    private ILibrosRepository libroRepository;
    private IAutorRepository autorRepository;
    private List<Libros> libros;
    private List<Autor> autores;

    public Principal(ILibrosRepository libroRepository, IAutorRepository autorRepository) {
        this.libroRepository = libroRepository;
        this.autorRepository = autorRepository;
    }

    public void muestraElMenu() {
        var opcion = -1;
        while (opcion != 0) {
            var menu = """
                    \n------Search Books------\n
                    1 - Buscar libro por titulo
                    2 - Listar libros registrados
                    3 - Listar autores registrados
                    4 - Listar autores vivos en un determinado año
                    5 - Listar libros por idioma
                    
                    0 - Salir
                    """;
            System.out.println(menu);
            System.out.print("Ingrese la opcion que desea realizar:");
            opcion = teclado.nextInt();
            teclado.nextLine();

            switch (opcion) {
                case 1 -> buscarLibroPorTitulo();
                case 2 -> listarLibrosRegistrados();
                case 3 -> listarAutoresRegistrados();
                case 4 -> buscarAutoresVivos();
                case 5 -> buscarLibroPorIdioma();
                case 0 -> {
                    System.out.println("Gracias por usar la aplicación para buscar y registrar libros!");
                    System.out.println("Cerrando la aplicación...");
                }
                default -> System.out.println("Opción inválida. Ingrese una opcion valida para poder continuar...");
            }
        }
    }

    private Datos busqueda() {
        System.out.print("Ingrese el titulo del libro que desea buscar: ");
        var nombreLibro = teclado.nextLine();
        var json = consumoApi.obtenerDatos(URL_BASE + URL_BUSQUEDA + nombreLibro.replace(" ", "+"));
        Datos datos = conversor.obtenerDatos(json, Datos.class);
        return datos;
    }

    private void buscarLibroPorTitulo() {
        Datos datosDeBusqueda = busqueda();
        if (datosDeBusqueda != null && !datosDeBusqueda.libros().isEmpty()) {
            DatosLibros libroBuscado = datosDeBusqueda.libros().get(0);

            Libros libro = new Libros(libroBuscado);
            System.out.println(libro);

            Optional<Libros> libroExiste = libroRepository.findByTitulo(libro.getTitulo());
            if (libroExiste.isPresent()) {
                System.out.println("""
                        \n--------------------------------
                        EL LIBRO YA SE ENCUENTRA REGISTRADO
                        -----------------------------------
                        """);
            } else {
                if (!libroBuscado.datosAutor().isEmpty()) {
                    DatosAutor autor = libroBuscado.datosAutor().get(0);
                    Autor autor1 = new Autor(autor);
                    Optional<Autor> autorBuscado = autorRepository.findByNombreAutor(autor.nombreAutor());

                    if (autorBuscado.isPresent()) {
                        Autor autorExiste = autorBuscado.get();
                        libro.setAutor(autorExiste);
                        libroRepository.save(libro);
                    } else {
                        Autor autorNuevo = autorRepository.save(autor1);
                        libro.setAutor(autorNuevo);
                        libroRepository.save(libro);
                    }

                } else {
                    System.out.println("Sin autor");
                }
            }
        } else {
            System.out.println("""
                    \n--------------------------------
                           LIBRO NO ENCONTRADO
                    -----------------------------------
                    """);
        }
    }

    private void listarLibrosRegistrados() {
        libros = libroRepository.findAll();
        libros.stream()
                .forEach(System.out::println);
    }

    private void listarAutoresRegistrados() {
        autores = autorRepository.findAll();
        autores.stream()
                .forEach(System.out::println);
    }

    private void buscarAutoresVivos() {
        System.out.print("Ingrese el año vivo del autor que desea buscar: ");
        var anioBusqueda = teclado.nextInt();
        autores = autorRepository.busquedaPorFecha(anioBusqueda);
        autores.stream()
                .forEach(System.out::println);

    }
    private void buscarLibroPorIdioma() {

        System.out.print("\nSelecciona el idioma que desea buscar");

            var menuIdioma = """
                    \n------LIBROS POR IDIOMA------\n
                    en - Ingles
                    fr - Frances
                    es - Español
                    pt - Portugues
                    
                    0 - Salir
                    """;

            System.out.println(menuIdioma);
            System.out.print("Ingrese el idioma que desea buscar:");
            var opcion = teclado.nextLine().toLowerCase();

        if (!opcion.equals("es") && !opcion.equals("en") && !opcion.equals("fr") && !opcion.equals("pt")) {
            System.out.println("Idioma no válido, intenta de nuevo");
            return;
        }
        List<Libros> iBooksRepositoryByLanguagesContaining = libroRepository.findByLenguajes(opcion);

        if (iBooksRepositoryByLanguagesContaining.isEmpty()) {
            System.out.println("No hay libros registrados en ese idioma");
            return;
        }
        System.out.println("----- LOS LIBROS REGISTRADOS EN EL IDIOMA SELECCIONADO SON: -----\n");
        iBooksRepositoryByLanguagesContaining.stream()
                .sorted(Comparator.comparing(Libros::getTitulo))
                .forEach(System.out::println);

        }


    }



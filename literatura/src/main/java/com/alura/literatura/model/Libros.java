package com.alura.literatura.model;

import jakarta.persistence.*;


@Entity
@Table(name = "libros")
public class Libros {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;
    private String titulo;
    private String lenguajes;
    private Integer descargas;
    @ManyToOne
    @JoinColumn (name = "autor_id")
    private Autor autor;

    public Libros() {
    }


    public Libros(DatosLibros datosLibros){
        this.titulo=datosLibros.titulo();
        this.autor=new Autor(datosLibros.datosAutor().get(0));
        this.lenguajes=datosLibros.lenguajes().isEmpty() ? "N/A" : datosLibros.lenguajes().get(0);
        this.descargas=datosLibros.descargas();
  }


    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        Id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public Autor getAutor() {
        return autor;
    }

    public void setAutor(Autor autor) {
        this.autor = autor;
    }

    public String getLenguajes() {
        return lenguajes;
    }

    public void setLenguajes(String lenguajes) {
        this.lenguajes = lenguajes;
    }

    public Integer getDescargas() {
        return descargas;
    }

    public void setDescargas(Integer descargas) {
        this.descargas = descargas;
    }


    @Override
    public String toString() {
        return "\n------------ LIBRO ------------\n" +
                " \nTitulo: " + titulo +
                " \nAutor: " + autor.getNombreAutor() +
                " \nIdioma: " + lenguajes +
                " \nNumero de descargas: " + descargas +
                "\n-----------------------------------\n";
    }
}




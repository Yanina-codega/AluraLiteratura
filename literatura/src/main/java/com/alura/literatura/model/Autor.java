package com.alura.literatura.model;
import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Table(name = "autores")
public class Autor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nombreAutor;
    private Integer fechaDeNacimiento;
    private Integer fechaDeFallecimiento;
    @OneToMany(mappedBy = "autor",cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    private List<Libros> libros = new ArrayList<>();

    public Autor (){}

    public Autor(String nombreAutor,Integer fechaDeNacimiento,Integer fechaDeFallecimiento){
        this.nombreAutor=nombreAutor;
        this.fechaDeNacimiento=fechaDeNacimiento;
        this.fechaDeFallecimiento=fechaDeFallecimiento;
    }

    public Autor(DatosAutor datosAutor){
        this.nombreAutor=datosAutor.nombreAutor();
        this.fechaDeNacimiento=datosAutor.fechaDeNacimiento();
        this.fechaDeFallecimiento=datosAutor.fechaDeFallecimiento();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        id = id;
    }

    public String getNombreAutor() {
        return nombreAutor;
    }

    public void setNombreAutor(String nombreAutor) {
        this.nombreAutor = nombreAutor;
    }

    public Integer getFechaDeNacimiento() {
        return fechaDeNacimiento;
    }

    public void setFechaDeNacimiento(Integer fechaDeNacimiento) {
        this.fechaDeNacimiento = fechaDeNacimiento;
    }

    public Integer getFechaDeFallecimiento() {
        return fechaDeFallecimiento;
    }

    public void setFechaDeFallecimiento(Integer fechaDeFallecimiento) {
        this.fechaDeFallecimiento = fechaDeFallecimiento;
    }

    public List<Libros> getLibros() {
        return libros;
    }

    public void setLibros(List<Libros> libros) {
        this.libros = libros;
    }

    @Override
    public String toString() {
        return "\n------------ Autor ------------\n" +
                "\nNombre: " + nombreAutor +
                "\nAño de nacimiento: " + fechaDeNacimiento +
                "\nAño de fallecimiento: " + fechaDeFallecimiento +
                "\nLibros: " + libros.stream().map(Libros::getTitulo).collect(Collectors.toList()) +
                "\n-----------------------------------\n";
    }

}

package com.alura.literatura.repository;
import com.alura.literatura.model.Autor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface IAutorRepository extends JpaRepository<Autor, Long> {
    Optional<Autor> findByNombreAutor(String nombre);


   //Buscando en la bd por fecha
    @Query("SELECT a FROM Autor a WHERE a.fechaDeNacimiento <= :anioBusqueda AND a.fechaDeFallecimiento >= :anioBusqueda")
    List<Autor>busquedaPorFecha(Integer anioBusqueda);
}

package com.alura.literatura.repository;
import com.alura.literatura.model.Libros;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface ILibrosRepository extends JpaRepository<Libros,Long> {
    Optional<Libros> findByTitulo(String titulo);


    List<Libros> findByLenguajes(String lenguajes);
}

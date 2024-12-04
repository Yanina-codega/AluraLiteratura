package com.alura.literatura;
import com.alura.literatura.principal.Principal;
import com.alura.literatura.repository.IAutorRepository;
import com.alura.literatura.repository.ILibrosRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
public class LiteraturaApplication implements CommandLineRunner {


	@Autowired
	private ILibrosRepository libroRepository;
	@Autowired
	private IAutorRepository autorRepository;

	public static void main(String[] args) {
		SpringApplication.run(LiteraturaApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {

		Principal principal = new Principal(libroRepository, autorRepository);
		principal.muestraElMenu();

	}
}

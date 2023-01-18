package com.example.TiendaComputadoras;

import com.example.TiendaComputadoras.model.Component_Product;
import com.example.TiendaComputadoras.model.Person;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class TiendaComputadorasApplication implements CommandLineRunner {

	private Person person;
	private Component_Product componet;
	//inyeccion de dependencias por constructor no lleva el autowired pero lo ponemos para mayor claridad
	@Autowired
	public TiendaComputadorasApplication(Person person, Component_Product component){
		this.person = person;
		this.componet = component;
	}

	public static void main(String[] args) {
		SpringApplication.run(TiendaComputadorasApplication.class, args);
	}

	@PostConstruct
	public void init() {
		person.setName("Victor Estupiñan");
		componet.setName("Tarjeta grafica");
	}

	@Override
	public void run(String... args) throws Exception {
		System.out.println(person.greet("Joaquin gonzalez"));
		System.out.println(componet.componentNew("Geoforce GTX"));
	}
}

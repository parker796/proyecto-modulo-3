package com.example.TiendaComputadoras.model;

import org.springframework.stereotype.Component;

@Component
public class Person {
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String greet(String CustomerService) {
        return "Hola, " + name + " mi nombre es " +  CustomerService + " que equipo necesita y con que configuracion" ;
    }
}

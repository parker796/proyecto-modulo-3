package com.example.TiendaComputadoras.model;

import org.springframework.stereotype.Component;

@Component
public class Component_Product { //este componente del producto lo puedo llevar a las diferentes marcas que utilizo e inyectarlo
    private String name; //nombre del componente que se va inyectar

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public String componentNew(String prodNew){
        return "dame el nombre del componente: " + name + " y la marca que vamos agregar: " + prodNew;
    }
}

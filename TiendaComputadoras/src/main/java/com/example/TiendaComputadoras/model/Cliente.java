package com.example.TiendaComputadoras.model;


import com.example.TiendaComputadoras.DTO.DTOApple;
import com.example.TiendaComputadoras.DTO.DTODell;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.List;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Cliente")
public class Cliente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //aqui es un pk y el valor autoincrementable
    @PositiveOrZero(message = "El id debe ser positivo")
    private Long id;
    @Column(name = "nombre", nullable = false, length = 50)
    @NotEmpty(message = "El nombre no debe de ser nulo")
    @Size(min = 5, max = 30, message = "El nombre debe tener entre 5 y 30 letras")
    private String nombre;

    @Column(name = "email", nullable = false, length = 50)
    @Email(message = "el email no es valido", regexp = "(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])")
    @NotEmpty(message = "email no puede ser vacio")
    private String email;

    //el cliente puede tener 1 a muchos productos o equipos como dell y apple al mismo tiempo en un pedido
    @OneToMany(fetch = FetchType.LAZY)
    private List<Dell> EquipoDell;
    @OneToMany(fetch = FetchType.LAZY)
    private List<Apple> EquipoApple;

    public void addDell(Dell dell) {
        this.EquipoDell.add(dell); //necesitamos inicializar la lista de equipos dell porque esta en nulo
    }//agregando dells uno a uno

    public void addApple(Apple apple) {
        this.EquipoApple.add(apple); //necesitamos inicializar la lista de equipos dell porque esta en nulo
    }//agregando dells uno a uno

    public void removeDell(DTODell dell) {
        this.EquipoDell.remove(dell); //necesitamos inicializar la lista de cliente porque esta en nulo
    }//eliminamos el equipo dell del cliente asociado

    public void removeApple(DTOApple apple) {
        this.EquipoApple.remove(apple); //necesitamos inicializar la lista de cliente porque esta en nulo
    }//eliminamos el equipo dell del cliente asociado

}

package com.example.TiendaComputadoras.model;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

//estas clases son las que vamos a utilizar en base de datos
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Dell")
public class Dell {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //aqui es un pk y el valor autoincrementable
    @PositiveOrZero(message = "El id debe ser positivo")
    private Long id;
    @Column(name = "procesador", nullable = false, length = 50)
    @NotEmpty(message = "El procesador debe de tener un nombre")
    @Size(min = 5, max = 30, message = "El procesador debe tener entre 5 y 30 letras")
    private String procesador;
    @NotEmpty(message = "La memoria ram debe de tener un nombre")
    @Size(min = 3, max = 30, message = "La memoria ram debe tener entre 5 y 30 letras")
    @Column(name = "memoria ram", nullable = false, length = 20)
    private String memoriaRam;
    @NotEmpty(message = "El disco duro debe de tener un nombre")
    @Size(min = 3, max = 30, message = "El disco duro debe tener entre 5 y 30 letras")
    @Column(name = "disco duro", nullable = false, length = 50)
    private String disco;

    /*
    //lo sustituimos por lombok
    public void setId(Long id) {
        this.id = id;
    }

    public void setDisco(String disco) {
        this.disco = disco;
    }

    public void setMemoriaRam(String memoriaRam) {
        this.memoriaRam = memoriaRam;
    }

    public void setProcesador(String procesador) {
        this.procesador = procesador;
    }

    public Long getId() {
        return id;
    }

    public String getDisco() {
        return disco;
    }

    public String getMemoriaRam() {
        return memoriaRam;
    }

    public String getProcesador() {
        return procesador;
    }
    */

}

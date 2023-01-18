package com.example.TiendaComputadoras.model;


import jakarta.persistence.*;
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
    private Long id;
    @Column(name = "procesador", nullable = false, length = 50)
    private String procesador;
    @Column(name = "memoria ram", nullable = false, length = 20)
    private String memoriaRam;
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

package com.example.TiendaComputadoras.controller;

import com.example.TiendaComputadoras.DTO.DTODell;
import com.example.TiendaComputadoras.Service.INServDell;
import com.example.TiendaComputadoras.Service.ServiceDell;
import com.example.TiendaComputadoras.model.Dell;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

@RestController
@RequestMapping("/Dell")
public class DellController {
    //igual lo mismo inyectamos la dependencia del servicio correspondiente
    private INServDell serviceDell;//polimorfismo cambiamos de estado en el objeto

    @Autowired //este no es necesario
    public DellController(INServDell serviceDell){
        this.serviceDell = serviceDell;
    }

    //@GetMapping("/obtenerDells")
    @GetMapping
    //public List<Dell> obtenerDells()
    public List<DTODell> obtenerDells(){
        return serviceDell.findAll();
    }
    @GetMapping("/{id}")
    public List<DTODell> obtenerDell(@PathVariable Long id) {

        return serviceDell.findAllById(id);
    }
    //@PostMapping("/dellCrear")
    @PostMapping
    //public Dell dellCrear(@RequestBody Dell data){ //viene un objeto json y lo convierte a una clase java
    public DTODell dellCrear(@RequestBody DTODell data){
        return serviceDell.save(data);
    }
    //@PutMapping("/dellModificar") //yo en mi put no obtengo el id en url si no viene todo en el body el id
   @PutMapping
    //public String dellModificar(@RequestBody Dell data)
    public String dellModificar(@RequestBody DTODell data){
        serviceDell.updateDell(data);
      return "actualizado correctamente ";
    }
    @DeleteMapping("/{id}")
    public String dellEliminar(@PathVariable Long id){
        serviceDell.deleteById(id);
        return "se elimino correctamente ";
    }



}

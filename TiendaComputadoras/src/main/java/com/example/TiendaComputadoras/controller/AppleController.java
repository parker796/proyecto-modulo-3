package com.example.TiendaComputadoras.controller;

import com.example.TiendaComputadoras.DTO.DTOApple;
import com.example.TiendaComputadoras.DTO.DTODell;
import com.example.TiendaComputadoras.Service.INServApple;
import com.example.TiendaComputadoras.model.Apple;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/Apple")
public class AppleController {
   private INServApple serviceApple;

   @Autowired
    public AppleController(INServApple serviceApple) {
      this.serviceApple = serviceApple;
    }

    //@GetMapping("/obtenerApples")
    @GetMapping
    //public List<Apple> obtenerApples()
    public List<DTOApple> obtenerApples(){
        return serviceApple.findAll();
    }

    @GetMapping("/{id}")
    public List<DTOApple> obtenerCurso(@PathVariable Long id) {

       return serviceApple.findAllById(id);
    }

   // @PostMapping("/appleCrear")
    @PostMapping
    public DTOApple appleCrear(@RequestBody DTOApple data){ //viene un objeto json y lo convierte a una clase java

       return serviceApple.save(data);
    }

   // @PutMapping("/appleModificar") //igual el id viene en el cuerpo del mensaje a actualizar
    @PutMapping
    public String appleModificar(@RequestBody DTOApple data){
        serviceApple.updateApple(data);
        return "se actualizo correctamente";
    }

    @DeleteMapping("/{id}")
    public String appleEliminar(@PathVariable Long id){
       serviceApple.appleEliminar(id);
       return "se elimino correctamente";
    }



}

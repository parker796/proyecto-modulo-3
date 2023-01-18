package com.example.TiendaComputadoras.controller;

import com.example.TiendaComputadoras.DTO.DTOApple;
import com.example.TiendaComputadoras.DTO.DTODell;
import com.example.TiendaComputadoras.Service.INServApple;
import com.example.TiendaComputadoras.model.Apple;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    /*
    public List<DTOApple> obtenerApples(){
        return serviceApple.findAll();
    }*/
    public ResponseEntity<?> list(){
        return ResponseEntity.ok().body(serviceApple.findAll());//construimos ya el json para el fronted 200
    }

    @GetMapping("/{id}")
    /*
    public List<DTOApple> obtenerCurso(@PathVariable Long id) {

       return serviceApple.findAllById(id);
    }*/
    public ResponseEntity<?> view(@PathVariable Long id){
        List<DTOApple> o = serviceApple.findAllById(id);
        if(o == null){
            return ResponseEntity.notFound().build();//404
        }
        return ResponseEntity.ok(o.get(0)); //200
    }

   // @PostMapping("/appleCrear")
    @PostMapping
    /*
    public DTOApple appleCrear(@RequestBody DTOApple data){ //viene un objeto json y lo convierte a una clase java

       return serviceApple.save(data);
    }*/
    public ResponseEntity<?> create(@RequestBody DTOApple data){
        DTOApple dtoApple = serviceApple.save(data);
        return ResponseEntity.status(HttpStatus.CREATED).body(dtoApple); //201
    }

   // @PutMapping("/appleModificar") //igual el id viene en el cuerpo del mensaje a actualizar
    @PutMapping("/{id}")
    /*
    public String appleModificar(@RequestBody DTOApple data){
        serviceApple.updateApple(data);
        return "se actualizo correctamente";
    }*/
    public ResponseEntity<?> edit(@RequestBody DTOApple apple, @PathVariable Long id){
        List<DTOApple> o = serviceApple.findAllById(id);
        if(o == null){
            return ResponseEntity.notFound().build(); //404
        }
        serviceApple.updateApple(apple);
        return ResponseEntity.status(HttpStatus.CREATED).body("se actualizo correctamente"); //tenemos que persistir los datos en BD
    }

    @DeleteMapping("/{id}")
    /*
    public String appleEliminar(@PathVariable Long id){
       serviceApple.appleEliminar(id);
       return "se elimino correctamente";
    }*/
    public ResponseEntity<?> delete(@PathVariable Long id){
        serviceApple.appleEliminar(id);
        return (ResponseEntity<?>) ResponseEntity.noContent().build();//204 no hay contenido
    }



}

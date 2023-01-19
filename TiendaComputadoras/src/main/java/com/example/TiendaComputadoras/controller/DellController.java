package com.example.TiendaComputadoras.controller;

import com.example.TiendaComputadoras.DTO.DTODell;
import com.example.TiendaComputadoras.Service.INServDell;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.*;

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
    //ahora esto ya cambia por los metodos de tipos rest en las respuestas
    /*
    public List<DTODell> obtenerDells(){

        return serviceDell.findAll();
    }*/
    public ResponseEntity<?> list(){
        return ResponseEntity.ok().body(serviceDell.findAll());//construimos ya el json para el fronted 200
    }
    @GetMapping("/{id}")
    /*
    //respuesta 200 o 404 si no fue encontrado
    public List<DTODell> obtenerDell(@PathVariable Long id) {

        return serviceDell.findAllById(id);
    }*/
    public ResponseEntity<?> view(@PathVariable Long id){
        List<DTODell> o = serviceDell.findAllById(id);
        if(o == null){
            return ResponseEntity.notFound().build();//404
        }
        return ResponseEntity.ok(o.get(0)); //200
    }

    //@PostMapping("/dellCrear")
    @PostMapping
    //public Dell dellCrear(@RequestBody Dell data){ //viene un objeto json y lo convierte a una clase java
    //codigo de respuesta 201
    /*
    public DTODell dellCrear(@RequestBody DTODell data){
        return serviceDell.save(data);
    }*/
    public ResponseEntity<Map<String, Object>> create(@Valid @RequestBody DTODell data){
        Map<String, Object> response = new HashMap<>();
        try {
            DTODell dtoDell = serviceDell.save(data);
        }catch (Exception e) {

            response.put("mensaje", "hubo un error");
            response.put("error", e.getMessage().concat(": ").concat(e.getMessage()));
            return new ResponseEntity<Map<String, Object>>(response,
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
        //return ResponseEntity.status(HttpStatus.CREATED).body(dtoDell); //201
        return ResponseEntity.created(URI.create("1")).build();
    }
    //manejador de errores exceptionHandler

    //@PutMapping("/dellModificar") //yo en mi put no obtengo el id en url si no viene todo en el body el id
    @PutMapping("/{id}")
    //public String dellModificar(@RequestBody Dell data) //modificamos la respuesta a 201
   /*
    public String dellModificar(@RequestBody DTODell data){
        serviceDell.updateDell(data);
      return "actualizado correctamente ";
    }*/
   public ResponseEntity<?> edit(@RequestBody DTODell dell, @PathVariable Long id){
       List<DTODell> o = serviceDell.findAllById(id);
       if(o == null){
           return ResponseEntity.notFound().build(); //404
       }
       serviceDell.updateDell(dell);
       return ResponseEntity.status(HttpStatus.CREATED).body("se actualizo correctamente"); //tenemos que persistir los datos en BD
   }
    @DeleteMapping("/{id}")
    //codigo de respuesta 204
    /*
    public String dellEliminar(@PathVariable Long id){
        serviceDell.deleteById(id);
        return "se elimino correctamente ";
    }*/
    public ResponseEntity<?> delete(@PathVariable Long id){
        serviceDell.deleteById(id);
        return (ResponseEntity<?>) ResponseEntity.noContent().build();//204 no hay contenido
    }

/*
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationExceptions(
            MethodArgumentNotValidException ex) {

        Map<String, String> errors = new HashMap<>();

        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });

        return errors;
    }

    @ExceptionHandler(value = IllegalArgumentException.class)
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    public Map<String, Object> handleException(Exception ex) {
        Map<String, Object> map = new HashMap<>();
        map.put("message", ex.getMessage());
        map.put("timestamp", new Date());
        return map;
    }
    */



}

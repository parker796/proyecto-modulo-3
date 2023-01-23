package com.example.TiendaComputadoras.controller;

import com.example.TiendaComputadoras.DTO.DTOApple;
import com.example.TiendaComputadoras.DTO.DTODell;
import com.example.TiendaComputadoras.Service.INServDell;
import com.example.TiendaComputadoras.Service.ServiceDell;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.NoHandlerFoundException;

import java.net.URI;
import java.util.*;

@RestController
@RequestMapping("/Dell")
public class DellController {
    //igual lo mismo inyectamos la dependencia del servicio correspondiente
    private INServDell serviceDell;//polimorfismo cambiamos de estado en el objeto

    @Autowired //este no es necesario
    public DellController(INServDell serviceDell) {
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
    public ResponseEntity<?> list() {
        return ResponseEntity.ok().body(serviceDell.findAll());//construimos ya el json para el fronted 200
    }

    @GetMapping("/{id}")
    /*
    //respuesta 200 o 404 si no fue encontrado
    public List<DTODell> obtenerDell(@PathVariable Long id) {

        return serviceDell.findAllById(id);
    }*/
    public ResponseEntity<?> view(@PathVariable Long id) {
        List<DTODell> o = serviceDell.findAllById(id);
        if (o == null) {
           // return ResponseEntity.notFound().build();//404
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "El equipo Dell con el id especificado no existe.");
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
    public ResponseEntity<?> create(@RequestBody DTODell data) {
        DTODell dtoDell = serviceDell.save(data);
        return ResponseEntity.status(HttpStatus.CREATED).body(dtoDell); //201
    }
   /* public ResponseEntity<Map<String, Object>> create(@Valid @RequestBody DTODell data){
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
}*/
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
         //  return ResponseEntity.notFound().build(); //404
           throw new ResponseStatusException(HttpStatus.NOT_FOUND, "El equipo Dell con el id especificado no existe.");
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

   //dejamos pendiente el controllerAdviceException para todo los controladores
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

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public Map<String, Object> handleValidationExceptions2(
            HttpMessageNotReadableException ex) {
        Map<String, Object> map = new HashMap<>();
      //  map.put("message", ex.getMessage());
        map.put("mensaje", " Se espera que ingreses datos en formato json con sus respectivas comillas, la llave y el valor");
        logger.warn("No se creo el elemento hubo una excepcion a la ahora de guardar el dato");
        map.put("timestamp", new Date());
        return map;
    }

    private static Logger logger = LoggerFactory.getLogger(DellController.class);
    @ExceptionHandler(value = ConstraintViolationException.class)
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    public Map<String, Object> handleException(Exception ex) {
        Map<String, Object> map = new HashMap<>();
        map.put("message", " Se espera que ingreses los datos de manera correcta checar el mensaje en cuanto a la validacion pedida: " + ex.getMessage());
        logger.warn("No se creo el elemento hubo una excepcion a la ahora de guardar el dato");
        map.put("timestamp", new Date());
        return map;
    }

    @ExceptionHandler(NoHandlerFoundException.class)
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    public Map<String, Object> handleNoHandlerFoundException(NoHandlerFoundException ex) {
        Map<String, Object> m = new HashMap<String, Object>();
        m.put("exception", ex.getMessage());
        m.put("errorcode", "404");
        return m;
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(NullPointerException.class)
    public String manejaNullPointerException(NullPointerException npe){
        return "Ocurrió un error en el servidor al procesar la petición";
    }




}

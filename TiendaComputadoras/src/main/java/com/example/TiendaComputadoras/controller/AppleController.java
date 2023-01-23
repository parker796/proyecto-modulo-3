package com.example.TiendaComputadoras.controller;

import com.example.TiendaComputadoras.DTO.DTOApple;
import com.example.TiendaComputadoras.DTO.DTODell;
import com.example.TiendaComputadoras.Service.INServApple;
import com.example.TiendaComputadoras.model.Apple;
import jakarta.validation.ConstraintViolationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.NoHandlerFoundException;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

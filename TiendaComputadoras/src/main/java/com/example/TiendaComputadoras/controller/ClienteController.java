package com.example.TiendaComputadoras.controller;

import com.example.TiendaComputadoras.DTO.DTOApple;
import com.example.TiendaComputadoras.DTO.DTOCliente;
import com.example.TiendaComputadoras.DTO.DTODell;
import com.example.TiendaComputadoras.Service.INServCliente;
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

import java.util.*;

@RestController
@RequestMapping("/Cliente")
public class ClienteController {
    //igual lo mismo inyectamos la dependencia del servicio correspondiente
    private INServCliente serviceCliente;//polimorfismo cambiamos de estado en el objeto

    @Autowired //este no es necesario
    public ClienteController(INServCliente serviceCliente) {
        this.serviceCliente = serviceCliente;
    }

    @GetMapping
    public ResponseEntity<?> list() {
        return ResponseEntity.ok().body(serviceCliente.findAll());//construimos ya el json para el fronted 200
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> view(@PathVariable Long id) {
        List<DTOCliente> o = serviceCliente.findAllById(id);
        if (o == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("El Cliente con el id especificado no existe.");
        }
        return ResponseEntity.ok(o.get(0)); //200
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody DTOCliente data) {
        DTOCliente dtoCliente = serviceCliente.save(data);
        return ResponseEntity.status(HttpStatus.CREATED).body(dtoCliente); //201
    }
    @PutMapping("/{id}")
    public ResponseEntity<?> edit(@RequestBody DTOCliente cliente, @PathVariable Long id){
        List<DTOCliente> o = serviceCliente.findAllById(id);
        if(o == null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("El Cliente con el id especificado no existe.");
        }
        serviceCliente.updateCliente(cliente);
        return ResponseEntity.status(HttpStatus.CREATED).body("se actualizo correctamente"); //tenemos que persistir los datos en BD
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id){
        List<DTOCliente> o = serviceCliente.findAllById(id);
        if(o == null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("El equipo Cliente con el id especificado no existe.");
        }
        serviceCliente.deleteById(id);
        return (ResponseEntity<?>) ResponseEntity.noContent().build();//204 no hay contenido
    }

    //hacemos los servicios para asociar el id del cliente con un producto seleccionado
    //se maneja como un putmapping porque editamos el cliente con la lista de la relacion en vacion puede o no tener productos tenemos opcionalidad
    @PutMapping("/{id}/asignar-productos") //id del cliente y asignacion del producto
    public ResponseEntity<?> asignarProducto(@PathVariable Long id, @RequestBody List<DTODell> dells, @RequestBody List<DTOApple> apples){
        List<DTOCliente> o = serviceCliente.findAllById(id);
        if(o.isEmpty()) { //hay como un error con o.isPresent mejor como isEmpty() u o == null
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("El Cliente con el id especificado no existe.");
        }
        if(dells.isEmpty() && apples.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Debes de mandar una lista ya sea dell u apple");
        }
        serviceCliente.asignarProducto(id, dells, apples);
        return ResponseEntity.status(HttpStatus.CREATED).body("se asigno un equipo con el id del cliente satisfactoriamente");
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


package com.example.TiendaComputadoras.Service;
import com.example.TiendaComputadoras.DTO.DTOApple;
import com.example.TiendaComputadoras.Mapper.AppleMapper;
import com.example.TiendaComputadoras.Repository.InterAppleRepository;
import com.example.TiendaComputadoras.model.Apple;
import com.example.TiendaComputadoras.model.Dell;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class ServiceApple implements INServApple{
    //en lugar de implementar toda la interfaz mejor hacemos una inyeccion de repositorio al servicio
    private InterAppleRepository interApple;
    private AppleMapper appleMapper;
    private static Logger logger = LoggerFactory.getLogger(ServiceDell.class);

    @Autowired //este no es necesario
    public ServiceApple(InterAppleRepository interApple, AppleMapper appleMapper) {

        this.interApple = interApple;
        this.appleMapper = appleMapper;
    }

    @Override
   // public List<Apple> obtenerApples()
    public List<DTOApple> findAll(){
        if(interApple.findAll().size()>0) {
            logger.info("Tenemos elementos en la BD de datos");
            List<Apple> apples = interApple.findAll();
            return apples.stream().map(appleMapper::toDTO).toList();
        }
        else{
            logger.warn("No tenemos elementos en la BD hay que agregar");
            return null;
        }
    }
    @Override
    //public List<Apple> obtenerApple(Long id)
    public List<DTOApple> findAllById(Long id){
        //aqui era un metodo solo Dell pero se casteo
        // todo service dell por una lista porque es lo que regresa por id
        if(interApple.findAllById(Collections.singleton(id)).size()>0) {
            Optional<Apple> apples = interApple.findById(id);
            logger.info("Si encontramos el elemento");
            return apples.stream().map(appleMapper::toDTO).toList();
        }
        else{
            logger.warn("No se encontro el elemento");
            return null;
        }
    }
    @Override
    //public Apple appleCrear(Apple data)
    public DTOApple save(DTOApple data){ //viene un objeto json y lo convierte a una clase java
        logger.info("se creo el elemento");
        Apple entity = appleMapper.toEntity(data);
        return appleMapper.toDTO(interApple.save(entity));
    }
    @Override
    //public Apple appleModificar(Apple data)
    public void updateApple(DTOApple data){
        Optional<Apple> result = interApple.findById(data.getId());

        if (result.isEmpty()) {
            logger.warn("No existe el equipo");
        }

        Apple updatedApple = result.get();

        updatedApple.setMemoriaRam(data.getMemoriaRam());
        updatedApple.setProcesador(data.getProcesador());
        updatedApple.setDisco(data.getDisco());
        logger.info("se actualizo correctamente");
        interApple.save(updatedApple);


      /*  Apple updatedApple = interApple.findById(data.getId()).orElse(null);
        if(interApple.findById(data.getId()).isEmpty() == true) {
            logger.warn("Elemento que deseas modificar ya no existe");
            return null;
        }else {
            updatedApple.setMemoriaRam(data.getMemoriaRam());
            updatedApple.setProcesador(data.getProcesador());
            updatedApple.setDisco(data.getDisco());
            logger.info("se actualizo correctamente");
            return interApple.save(updatedApple);
        }
        */

    }
    @Override
    public void appleEliminar(Long id) {
        if(interApple.findAllById(Collections.singleton(id)).size()>0) {
            logger.info("Se elimino correctamente");
            interApple.deleteById(id);
        }else{
            logger.warn("No se encontro el elemento a eliminar");
        }
    }
}

package com.example.TiendaComputadoras.Service;

import com.example.TiendaComputadoras.DTO.DTODell;
import com.example.TiendaComputadoras.Mapper.DellMapper;
import com.example.TiendaComputadoras.Repository.InterDellRepository;
import com.example.TiendaComputadoras.model.Dell;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Collections;
import java.util.List;
import java.util.Optional;


@Service
public class ServiceDell implements INServDell{
    //en lugar de implementar toda la interfaz mejor hacemos una inyeccion de repositorio al servicio
    private InterDellRepository interDell;
    private DellMapper dellMapper;

    private static Logger logger = LoggerFactory.getLogger(ServiceDell.class);
    @Autowired //este no es necesario
    public ServiceDell(InterDellRepository interDell, DellMapper dellMapper) {

        this.interDell = interDell;
        this.dellMapper = dellMapper;
    }

    @Override
    //public List<Dell> obtenerDells()
    public List<DTODell> findAll() {
        if(interDell.findAll().size()>0){
            logger.info("Tenemos elementos en la BD de datos");
            List<Dell> dells = interDell.findAll();
            return dells.stream().map(dellMapper::toDTO).toList();
        }else{
            logger.warn("No tenemos elementos en la BD hay que agregar");
            return null;
        }
    }

    @Override
     public List<DTODell> findAllById(Long id) {
        //aqui era un metodo solo Dell pero se casteo
        // todo service dell por una lista porque es lo que regresa por id
        if(interDell.findAllById(Collections.singleton(id)).size()>0){
            Optional<Dell> dells = interDell.findById(id);
            logger.info("Si encontramos el elemento");
            return dells.stream().map(dellMapper::toDTO).toList();
        }else{
            logger.warn("No se encontro el elemento");
            return null;
        }
    }
    @Override
   // public Dell dellCrear(Dell data){ //viene un objeto json y lo convierte a una clase java
     public DTODell save(DTODell data){
        logger.info("se creo el elemento");
        Dell entity = dellMapper.toEntity(data);
        return dellMapper.toDTO(interDell.save(entity));

    }
    @Override
   // public Dell dellModificar(Dell data)
    public void updateDell(DTODell data){
        Optional<Dell> result = interDell.findById(data.getId());

        if (result.isEmpty()) {
            logger.warn("No existe el equipo");
        }

        Dell updatedDell = result.get();

        updatedDell.setMemoriaRam(data.getMemoriaRam());
        updatedDell.setProcesador(data.getProcesador());
        updatedDell.setDisco(data.getDisco());
        logger.info("se actualizo correctamente");
        interDell.save(updatedDell);
       /* Dell updatedDell = interDell.findById(data.getId()).orElse(null);
        if(interDell.findById(data.getId()).isEmpty() == true){
            logger.warn("Elemento que deseas modificar ya no existe");
            return null;
        }else {
            updatedDell.setMemoriaRam(data.getMemoriaRam());
            updatedDell.setProcesador(data.getProcesador());
            updatedDell.setDisco(data.getDisco());
            logger.info("se actualizo correctamente");
            return interDell.save(updatedDell);
        }*/
    }
    @Override
    public void deleteById(Long id) {
        if(interDell.findAllById(Collections.singleton(id)).size()>0) {
            logger.info("Se elimino correctamente");
            interDell.deleteById(id);
        }else{
            logger.warn("No se encontro el elemento a eliminar");
        }
    }


}

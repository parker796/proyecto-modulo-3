package com.example.TiendaComputadoras.Service;

import com.example.TiendaComputadoras.DTO.DTOApple;
import com.example.TiendaComputadoras.DTO.DTOCliente;
import com.example.TiendaComputadoras.DTO.DTODell;
import com.example.TiendaComputadoras.Mapper.AppleMapper;
import com.example.TiendaComputadoras.Mapper.ClienteMapper;
import com.example.TiendaComputadoras.Mapper.DellMapper;
import com.example.TiendaComputadoras.Repository.InterClienteRepository;
import com.example.TiendaComputadoras.model.Apple;
import com.example.TiendaComputadoras.model.Cliente;
import com.example.TiendaComputadoras.model.Dell;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class ServiceCliente implements INServCliente{
    //en lugar de implementar toda la interfaz mejor hacemos una inyeccion de repositorio al servicio
    private InterClienteRepository interCliente;
    private ClienteMapper clienteMapper;

    private AppleMapper appleMapper;
    private DellMapper dellMapper;

    private static Logger logger = LoggerFactory.getLogger(ServiceDell.class);
    @Autowired //este no es necesario
    public ServiceCliente(InterClienteRepository interCliente, ClienteMapper clienteMapper,
                          AppleMapper appleMapper, DellMapper dellMapper) {

        this.interCliente = interCliente;
        this.clienteMapper = clienteMapper;
        this.appleMapper = appleMapper;
        this.dellMapper = dellMapper;
    }

    @Override
    public List<DTOCliente> findAll() {
        if(interCliente.findAll().size()>0){
            logger.info("Tenemos elementos en la BD de datos");
            List<Cliente> clientes = interCliente.findAll();
            return clientes.stream().map(clienteMapper::toDTO).toList();
        }else{
            logger.warn("No tenemos elementos en la BD hay que agregar");
            return null;
        }
    }

    @Override
    public List<DTOCliente> findAllById(Long id) {
        if(interCliente.findAllById(Collections.singleton(id)).size()>0){
            Optional<Cliente> clientes = interCliente.findById(id);
            logger.info("Si encontramos el elemento");
            return clientes.stream().map(clienteMapper::toDTO).toList();
        }else{
            logger.warn("No se encontro el elemento");
            return null;
        }
    }

    @Override
    public DTOCliente save(DTOCliente data) {
        logger.info("se creo el elemento");
        Cliente entity = clienteMapper.toEntity(data);
        return clienteMapper.toDTO(interCliente.save(entity));
    }

    @Override
    public void updateCliente(DTOCliente data) {
        Optional<Cliente> result = interCliente.findById(data.getId());

        if (result.isEmpty()) {
            logger.warn("No existe el cliente");
        }

        Cliente updatedCliente = result.get();

        updatedCliente.setNombre(data.getNombre());
        updatedCliente.setEmail(data.getEmail());
        logger.info("se actualizo correctamente");
        interCliente.save(updatedCliente);
    }

    @Override
    public void deleteById(Long id) {
        if(interCliente.findAllById(Collections.singleton(id)).size()>0) {
            logger.info("Se elimino correctamente");
            interCliente.deleteById(id);
        }else{
            logger.warn("No se encontro el elemento a eliminar");
        }
    }

    @Override
    public void asignarProducto(Long id, List<DTODell> dells, List<DTOApple> apples) {//id del cliente y lista de equipos o productos
        Optional<Cliente> o = interCliente.findById(id);
        if(o.isEmpty()){
            logger.warn("no existe ese cliente");
        }
        if(dells.isEmpty() && apples.isEmpty()){
            logger.warn("quisiero hacer una peticion tanto dell como apple en vacios");
        }
        if(dells.isEmpty() && (!o.isEmpty())){
            logger.info("no se asocio un equipo dell con el id del cliente pero si una apple");
            Cliente clienteDb = o.get();
            //transformamos la lista DTOApple a una entidad para asi poderlo guardar
            ArrayList<Apple> listaEntityApple = new ArrayList<Apple>();
            for (int i=0;i<apples.size();i++) {
                    listaEntityApple.add(appleMapper.toEntity(apples.get(i)));
            }
           listaEntityApple.forEach(a ->{
               clienteDb.addApple(a);
           });
        }
        if(apples.isEmpty() && (!o.isEmpty())){
            logger.info("no se asocio un equipo apple con el id del cliente pero si una dell");
            Cliente clienteDb = o.get();
            //transformamos la lista DTODell a una entidad para asi poderlo guardar
            ArrayList<Dell> listaEntityDell = new ArrayList<Dell>();
            for (int i=0;i<dells.size();i++) {
                listaEntityDell.add(dellMapper.toEntity(dells.get(i)));
            }
            listaEntityDell.forEach(a ->{
                clienteDb.addDell(a);
            });
        }
    }
    @Override
    public void eliminarProducto(Long id, DTODell dells, DTOApple apples) {
        Optional<Cliente> o = interCliente.findById(id);
        if(o.isEmpty()) {
            logger.warn("no existe ese cliente");
        }
        if(dells == null && (!o.isEmpty())){
            Cliente clienteDb = o.get();
            clienteDb.removeDell(dells);
            logger.info("eliminamos al equipo dell asociado al cliente");
        }
        if(apples == null && (!o.isEmpty())){
            Cliente clienteDb = o.get();
            clienteDb.removeApple(apples);
            logger.info("eliminamos al equipo dell asociado al cliente");
        }

    }
}

package com.example.TiendaComputadoras.Service;

import com.example.TiendaComputadoras.DTO.DTOApple;
import com.example.TiendaComputadoras.DTO.DTOCliente;
import com.example.TiendaComputadoras.DTO.DTODell;

import java.util.List;

public interface INServCliente {
    // public List<Dell> obtenerDells(); //sustituimos el acceso que haciamos hacia nuestra entidad con nuestro DTO
    public List<DTOCliente> findAll();
    // public List<Dell> obtenerDell(Long id);
    public List<DTOCliente> findAllById(Long id);
    // public Dell dellCrear(Dell data);
    public DTOCliente save(DTOCliente data);
    // public Dell dellModificar(Dell data);
    public void updateCliente(DTOCliente data);
    public void deleteById(Long id);
//falta agregar los dos metodos para asignar producto asociado al id del cliente y eliminar producto asociado id cliente
    public void asignarProducto(Long id, List<DTODell> dells, List<DTOApple> apples);
    public void eliminarProducto(Long id, DTODell dells, DTOApple apples);
}

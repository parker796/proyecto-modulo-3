package com.example.TiendaComputadoras.Service;

import com.example.TiendaComputadoras.DTO.DTODell;
import com.example.TiendaComputadoras.model.Dell;

import java.util.List;

public interface INServDell {
   // public List<Dell> obtenerDells(); //sustituimos el acceso que haciamos hacia nuestra entidad con nuestro DTO
    public List<DTODell> findAll();
   // public List<Dell> obtenerDell(Long id);
   public List<DTODell> findAllById(Long id);
   // public Dell dellCrear(Dell data);
    public DTODell save(DTODell data);
   // public Dell dellModificar(Dell data);
    public void updateDell(DTODell data);
    public void deleteById(Long id);
}

package com.example.TiendaComputadoras.Service;

import com.example.TiendaComputadoras.DTO.DTOApple;


import java.util.List;

public interface INServApple {
    //public List<Apple> obtenerApples();
    public List<DTOApple> findAll();
   // public List<Apple> obtenerApple(Long id);
   public List<DTOApple> findAllById(Long id);
    //public Apple appleCrear(Apple data);
    public DTOApple save(DTOApple data);
   // public Apple appleModificar(Apple data);
   public void updateApple(DTOApple data);
    public void appleEliminar(Long id);
}

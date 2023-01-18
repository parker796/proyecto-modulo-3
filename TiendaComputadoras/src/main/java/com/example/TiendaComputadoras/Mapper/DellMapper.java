package com.example.TiendaComputadoras.Mapper;

import com.example.TiendaComputadoras.DTO.DTODell;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import com.example.TiendaComputadoras.model.Dell;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface DellMapper {
    DTODell toDTO(Dell data);
    Dell toEntity(DTODell data);

}

package com.example.TiendaComputadoras.Mapper;

import com.example.TiendaComputadoras.DTO.DTOApple;
import com.example.TiendaComputadoras.model.Apple;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface AppleMapper {
    DTOApple toDTO(Apple data);
    Apple toEntity(DTOApple data);
}

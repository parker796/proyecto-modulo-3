package com.example.TiendaComputadoras.Mapper;

import com.example.TiendaComputadoras.DTO.DTOCliente;

import com.example.TiendaComputadoras.model.Cliente;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface ClienteMapper {
    DTOCliente toDTO(Cliente data);
    Cliente toEntity(DTOCliente data);
}

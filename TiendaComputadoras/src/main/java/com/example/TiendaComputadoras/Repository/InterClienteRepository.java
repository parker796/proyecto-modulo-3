package com.example.TiendaComputadoras.Repository;

import com.example.TiendaComputadoras.model.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InterClienteRepository extends JpaRepository<Cliente, Long> {

}

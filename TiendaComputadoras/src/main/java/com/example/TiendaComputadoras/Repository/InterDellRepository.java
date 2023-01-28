package com.example.TiendaComputadoras.Repository;

import com.example.TiendaComputadoras.model.Dell;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InterDellRepository extends JpaRepository<Dell, Long> {
}

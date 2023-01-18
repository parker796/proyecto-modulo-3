package com.example.TiendaComputadoras.Repository;

import com.example.TiendaComputadoras.model.Apple;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InterAppleRepository extends JpaRepository<Apple, Long> {
}

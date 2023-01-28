package com.example.TiendaComputadoras.Repository;

import com.example.TiendaComputadoras.model.Apple;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InterAppleRepository extends JpaRepository<Apple, Long> {
}

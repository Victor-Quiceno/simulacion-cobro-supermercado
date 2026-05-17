package com.supermercado.cobro.repository;

import com.supermercado.cobro.entity.HistorialSimulacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HistorialSimulacionRepository extends JpaRepository<HistorialSimulacion, Long> {
}
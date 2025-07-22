package com.sistema.gestion.repositorio;

import com.sistema.gestion.modelo.Venta;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VentaRepository extends JpaRepository<Venta, Long> {
}
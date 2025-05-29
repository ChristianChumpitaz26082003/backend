package com.sistema.gestion.repositorio;

import com.sistema.gestion.modelo.Producto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductoRepository extends  JpaRepository<Producto, Long> {
}

package com.sistema.gestion.dto;

import java.math.BigDecimal;

import lombok.Data;
@Data
public class DetalleVentaDTO {
    private Long productoId;
    private int cantidad;
    private BigDecimal precio;
}

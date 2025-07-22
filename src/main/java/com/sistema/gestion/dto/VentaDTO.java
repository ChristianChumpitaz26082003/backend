package com.sistema.gestion.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import lombok.Data;

@Data
public class VentaDTO {
    private Long id;
    private LocalDate fecha;
    private BigDecimal total;
    private String estado;
    private String usuarioNombre;
    private String clienteNombre;

    private List<DetalleVentaDTO> detalles;
}

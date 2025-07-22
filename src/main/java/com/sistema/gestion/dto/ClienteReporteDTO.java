package com.sistema.gestion.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
public class ClienteReporteDTO {
    private Long clienteId;
    private String nombreCompleto;
    private BigDecimal totalComprado;
}


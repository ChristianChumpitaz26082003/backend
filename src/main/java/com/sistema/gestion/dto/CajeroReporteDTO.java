package com.sistema.gestion.dto;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CajeroReporteDTO {
    private Long usuarioId;
    private String nombreUsuario;
    private BigDecimal totalVendido;
}

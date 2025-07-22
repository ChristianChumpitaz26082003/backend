package com.sistema.gestion.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ProductoReporteDTO {
    private Long productoId;
    private String nombre;
    private Long cantidadVendida;
}

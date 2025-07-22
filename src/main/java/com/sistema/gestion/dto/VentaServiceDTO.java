package com.sistema.gestion.dto;

import java.util.List;

public interface VentaServiceDTO {
    VentaDTO registrarVentaDesdeDTO(VentaDTO ventaDTO);
    List<VentaDTO> obtenerTodasLasVentas();
    VentaDTO obtenerVentaPorId(Long id);
}

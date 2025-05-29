package com.sistema.gestion.dto;

import lombok.Data;
import java.util.List;

@Data
public class VentaRequest {
    private Long clienteId;
    private Long usuarioId;
    private List<ItemVenta> items;

    @Data
    public static class ItemVenta {
        private Long productoId;
        private int cantidad;
    }
}

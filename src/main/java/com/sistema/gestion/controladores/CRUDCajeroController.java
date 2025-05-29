package com.sistema.gestion.controladores;

import com.sistema.gestion.dto.VentaRequest;
import com.sistema.gestion.modelo.Venta;
import com.sistema.gestion.servicios.VentaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cajero")
public class CRUDCajeroController {

    private final VentaService ventaService;

    public CRUDCajeroController(VentaService ventaService) {
        this.ventaService = ventaService;
    }

    @PostMapping("/registrar-venta")
    public ResponseEntity<?> registrarVenta(@RequestBody VentaRequest request) {
        try {
            Venta venta = ventaService.registrarVenta(request);
            return ResponseEntity.ok(venta);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }
}

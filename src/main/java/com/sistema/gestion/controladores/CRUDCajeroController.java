package com.sistema.gestion.controladores;

import com.sistema.gestion.dto.VentaRequest;
import com.sistema.gestion.modelo.Producto;
import com.sistema.gestion.modelo.Venta;
import com.sistema.gestion.servicios.ProductoService;
import com.sistema.gestion.servicios.VentaService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cajero")
public class CRUDCajeroController {

    private final VentaService ventaService;
    @Autowired
    private ProductoService productoService;

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

    @GetMapping("/mostrarProducto")
    public ResponseEntity<?> mostrarProducto() {
        try {
            List<Producto> productos = productoService.listarProductos();
            return ResponseEntity.ok(productos);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al obtener los productos: " + e.getMessage());
        }
    }
}

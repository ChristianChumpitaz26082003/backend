package com.sistema.gestion.controladores.cajero;

import com.sistema.gestion.dto.VentaRequest;
import com.sistema.gestion.modelo.Cliente;
import com.sistema.gestion.modelo.Producto;
import com.sistema.gestion.modelo.Venta;
import com.sistema.gestion.servicios.ClienteService;
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
    private final ClienteService clienteService;

    @Autowired
    private ProductoService productoService;

    @Autowired
    public CRUDCajeroController(VentaService ventaService, ClienteService clienteService) {
        this.ventaService = ventaService;
        this.clienteService = clienteService;
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
    @PostMapping("/clientes")
    public ResponseEntity<Cliente> crearCliente(@RequestBody Cliente cliente) {
        return ResponseEntity.ok(clienteService.guardarCliente(cliente));
    }

    @GetMapping("/clientes")
    public ResponseEntity<List<Cliente>> listarClientes() {
        return ResponseEntity.ok(clienteService.listarClientes());
    }

    @GetMapping("/clientes/{id}")
public ResponseEntity<?> obtenerCliente(@PathVariable Long id) {
    return clienteService.obtenerClientePorId(id)
            .<ResponseEntity<?>>map(ResponseEntity::ok)
            .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).body("Cliente no encontrado"));
}


    @PutMapping("/clientes/{id}")
    public ResponseEntity<?> actualizarCliente(@PathVariable Long id, @RequestBody Cliente cliente) {
        try {
            return ResponseEntity.ok(clienteService.actualizarCliente(id, cliente));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @DeleteMapping("/clientes/{id}")
    public ResponseEntity<?> eliminarCliente(@PathVariable Long id) {
        clienteService.eliminarCliente(id);
        return ResponseEntity.ok("Cliente eliminado correctamente");
    }
}

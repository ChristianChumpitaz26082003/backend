package com.sistema.gestion.controladores;

import com.sistema.gestion.modelo.Producto;
import com.sistema.gestion.servicios.ProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/reponedor")
public class CRUDReponedorController {

    @Autowired
    private ProductoService productoService;

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

    @PostMapping("/agregarProducto")
    public ResponseEntity<?> agregarProducto(@RequestBody Producto producto) {
        try {
            Producto nuevoProducto = productoService.agregarProducto(producto);
            return ResponseEntity.status(HttpStatus.CREATED).body(nuevoProducto);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al agregar el producto: " + e.getMessage());
        }
    }

    @DeleteMapping("/eliminarProducto/{id}")
    public ResponseEntity<?> eliminarProducto(@PathVariable Long id) {
        try {
            productoService.eliminarProducto(id);
            return ResponseEntity.ok("Producto eliminado exitosamente con ID: " + id);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al eliminar el producto: " + e.getMessage());
        }
    }

    @PutMapping("/modificarProducto/{id}")
    public ResponseEntity<?> modificarProducto(@PathVariable Long id, @RequestBody Producto productoActualizado) {
        try {
            Optional<Producto> productoExistente = productoService.obtenerProductoPorId(id);
            if (productoExistente.isPresent()) {
                Producto producto = productoExistente.get();

                // Actualiza los campos del producto
                producto.setNombre(productoActualizado.getNombre());
                producto.setDescripcion(productoActualizado.getDescripcion());
                producto.setPrecio(productoActualizado.getPrecio());
                producto.setStock(productoActualizado.getStock());
                producto.setIdCategoria(productoActualizado.getIdCategoria());
                producto.setIdProveedor(productoActualizado.getIdProveedor());
                producto.setFechaCaducidad(productoActualizado.getFechaCaducidad());
                producto.setFechaIngreso(productoActualizado.getFechaIngreso());
                producto.setEstado(productoActualizado.getEstado());

                Producto actualizado = productoService.modificarProducto(producto);
                return ResponseEntity.ok(actualizado);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Producto no encontrado con ID: " + id);
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al modificar el producto: " + e.getMessage());
        }
    }

}

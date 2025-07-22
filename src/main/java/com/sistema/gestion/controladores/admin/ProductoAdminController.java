package com.sistema.gestion.controladores.admin;

import com.sistema.gestion.modelo.Producto;
import com.sistema.gestion.servicios.ProductoService;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/admin")
public class ProductoAdminController {

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

    @PostMapping(value = "/agregarProducto", consumes = { "multipart/form-data" })
    public ResponseEntity<?> agregarProducto(
            @RequestPart("producto") Producto producto,
            @RequestPart("imagen") MultipartFile imagen) {
        try {
            String urlImagen = productoService.subirImagen(imagen);
            producto.setImagenUrl(urlImagen);
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

    @PutMapping(value = "/modificarProducto/{id}", consumes = { "multipart/form-data" })
    public ResponseEntity<?> modificarProducto(
            @PathVariable Long id,
            @RequestPart("producto") Producto productoActualizado,
            @RequestPart(value = "imagen", required = false) MultipartFile imagen) {
        try {
            Optional<Producto> productoExistente = productoService.obtenerProductoPorId(id);
            if (productoExistente.isPresent()) {
                Producto producto = productoExistente.get();

                producto.setNombre(productoActualizado.getNombre());
                producto.setDescripcion(productoActualizado.getDescripcion());
                producto.setPrecio(productoActualizado.getPrecio());
                producto.setStock(productoActualizado.getStock());
                producto.setCategoria(productoActualizado.getCategoria());
                producto.setProveedor(productoActualizado.getProveedor());
                producto.setFechaCaducidad(productoActualizado.getFechaCaducidad());
                producto.setFechaIngreso(productoActualizado.getFechaIngreso());
                producto.setEstado(productoActualizado.getEstado());

                if (imagen != null && !imagen.isEmpty()) {
                    String urlImagen = productoService.subirImagen(imagen);
                    producto.setImagenUrl(urlImagen);
                }

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

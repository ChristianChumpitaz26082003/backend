package com.sistema.gestion.servicios;

import com.sistema.gestion.dto.VentaRequest;
import com.sistema.gestion.modelo.*;
import com.sistema.gestion.repositorio.*;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class VentaService {

    private final VentaRepository ventaRepository;
    private final DetalleVentaRepository detalleVentaRepository;
    private final ProductoRepository productoRepository;
    private final UsuarioRepository usuarioRepository;
    private final ClienteRepository clienteRepository;

    public VentaService(VentaRepository ventaRepository,
                        DetalleVentaRepository detalleVentaRepository,
                        ProductoRepository productoRepository,
                        UsuarioRepository usuarioRepository,
                        ClienteRepository clienteRepository) {
        this.ventaRepository = ventaRepository;
        this.detalleVentaRepository = detalleVentaRepository;
        this.productoRepository = productoRepository;
        this.usuarioRepository = usuarioRepository;
        this.clienteRepository = clienteRepository;
    }

    public Venta registrarVenta(VentaRequest request) {
        Cliente cliente = clienteRepository.findById(request.getClienteId())
                .orElseThrow(() -> new RuntimeException("Cliente no encontrado"));
        Usuario usuario = usuarioRepository.findById(request.getUsuarioId())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        Venta venta = new Venta();
        venta.setFecha(LocalDate.now());
        venta.setCliente(cliente);
        venta.setUsuario(usuario);
        venta.setTotal(0); 
        venta = ventaRepository.save(venta);

        double totalVenta = 0;
        List<DetalleVenta> detalles = new ArrayList<>();

        for (VentaRequest.ItemVenta item : request.getItems()) {
            Producto producto = productoRepository.findById(item.getProductoId())
                    .orElseThrow(() -> new RuntimeException("Producto no encontrado"));

            double subtotal = producto.getPrecio() * item.getCantidad();
            totalVenta += subtotal;

            producto.setStock(producto.getStock() - item.getCantidad());
            productoRepository.save(producto);

            DetalleVenta detalle = new DetalleVenta(null, venta, producto, item.getCantidad(), subtotal);
            detalles.add(detalle);
        }

        detalleVentaRepository.saveAll(detalles);
        venta.setTotal(totalVenta);
        return ventaRepository.save(venta);
    }
}

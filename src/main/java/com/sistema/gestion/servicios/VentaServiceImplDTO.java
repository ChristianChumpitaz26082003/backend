package com.sistema.gestion.servicios;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.sistema.gestion.dto.DetalleVentaDTO;
import com.sistema.gestion.dto.VentaDTO;
import com.sistema.gestion.dto.VentaServiceDTO;
import com.sistema.gestion.modelo.DetalleVenta;
import com.sistema.gestion.modelo.Venta;
import com.sistema.gestion.repositorio.ProductoRepository;
import com.sistema.gestion.repositorio.VentaRepository;

@Service
public class VentaServiceImplDTO implements VentaServiceDTO {

    private final VentaRepository ventaRepository;
    private final ProductoRepository productoRepository;

    public VentaServiceImplDTO(VentaRepository ventaRepository, ProductoRepository productoRepository) {
        this.ventaRepository = ventaRepository;
        this.productoRepository = productoRepository;
    }

    @Override
    public VentaDTO registrarVentaDesdeDTO(VentaDTO ventaDTO) {
        Venta venta = new Venta();
        venta.setFecha(ventaDTO.getFecha().atStartOfDay()); // convierte LocalDate a LocalDateTime
        venta.setTotal(ventaDTO.getTotal());

        List<DetalleVenta> detalles = ventaDTO.getDetalles().stream().map(detalleDTO -> {
            DetalleVenta detalle = new DetalleVenta();
            detalle.setVenta(venta); // relación inversa
            detalle.setCantidad(detalleDTO.getCantidad());
            detalle.setPrecioUnitario(detalleDTO.getPrecio());
            detalle.setProducto(productoRepository.findById(detalleDTO.getProductoId())
                    .orElseThrow(() -> new RuntimeException(
                            "Producto no encontrado con ID: " + detalleDTO.getProductoId())));
            return detalle;
        }).collect(Collectors.toList());

        venta.setDetalles(detalles);
        Venta ventaGuardada = ventaRepository.save(venta);
        return convertirAVentaDTO(ventaGuardada);
    }

    @Override
    public List<VentaDTO> obtenerTodasLasVentas() {
        return ventaRepository.findAll()
                .stream()
                .map(this::convertirAVentaDTO)
                .collect(Collectors.toList());
    }

    @Override
    public VentaDTO obtenerVentaPorId(Long id) {
        Venta venta = ventaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Venta no encontrada con ID: " + id));
        return convertirAVentaDTO(venta);
    }

    // Métodos auxiliares
    private VentaDTO convertirAVentaDTO(Venta venta) {
        VentaDTO dto = new VentaDTO();
        dto.setId(venta.getId());
        dto.setFecha(venta.getFecha().toLocalDate());

        dto.setTotal(venta.getTotal());


        dto.setEstado(venta.getEstado().name()); // Enum a String // si tienes este campo en tu entidad

    if (venta.getUsuario() != null) {
        dto.setUsuarioNombre(venta.getUsuario().getNombre()); // ajusta según tu modelo
    }

    if (venta.getCliente() != null) {
        dto.setClienteNombre(venta.getCliente().getNombre()); // ajusta según tu modelo
    }
        dto.setDetalles(venta.getDetalles().stream().map(this::convertirADetalleVentaDTO).collect(Collectors.toList()));
        return dto;
    }

    private DetalleVentaDTO convertirADetalleVentaDTO(DetalleVenta detalle) {
        DetalleVentaDTO dto = new DetalleVentaDTO();
        dto.setProductoId(detalle.getProducto().getId());
        dto.setCantidad(detalle.getCantidad());
        dto.setPrecio(detalle.getPrecioUnitario());
        return dto;
    }
}

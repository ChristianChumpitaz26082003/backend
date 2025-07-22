package com.sistema.gestion.servicios;

import com.sistema.gestion.dto.CajeroReporteDTO;
import com.sistema.gestion.dto.ClienteReporteDTO;
import com.sistema.gestion.dto.ProductoReporteDTO;
import com.sistema.gestion.modelo.DetalleVenta;
import com.sistema.gestion.modelo.Venta;
import com.sistema.gestion.repositorio.DetalleVentaRepository;
import com.sistema.gestion.repositorio.VentaRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class ReporteService {
    private final VentaRepository ventaRepository;
    private final DetalleVentaRepository detalleVentaRepository;

    public ReporteService(VentaRepository ventaRepository, DetalleVentaRepository detalleVentaRepository) {
        this.ventaRepository = ventaRepository;
        this.detalleVentaRepository = detalleVentaRepository;
    }

    @Transactional(readOnly = true)
public List<ClienteReporteDTO> obtenerTopClientes() {
    return ventaRepository.findAll().stream()
            .filter(v -> v.getCliente() != null)
            .filter(v -> v.getEstado() == Venta.EstadoVenta.COMPLETADA) // Opcional: filtrar por estado
            .collect(Collectors.groupingBy(
                    v -> v.getCliente().getId(),
                    Collectors.collectingAndThen(
                            Collectors.toList(),
                            ventasPorCliente -> {
                                Venta muestra = ventasPorCliente.get(0);
                                String nombreCompleto = muestra.getCliente().getNombre() + " " + muestra.getCliente().getApellido();
                                BigDecimal totalComprado = ventasPorCliente.stream()
                                        .map(Venta::getTotal)
                                        .reduce(BigDecimal.ZERO, BigDecimal::add);

                                return new ClienteReporteDTO(muestra.getCliente().getId(), nombreCompleto, totalComprado);
                            }
                    )
            ))
            .values().stream()
            .sorted(Comparator.comparing(ClienteReporteDTO::getTotalComprado).reversed())
            .collect(Collectors.toList());
}




    @Transactional(readOnly = true)
public List<CajeroReporteDTO> obtenerTopCajeros() {
    return ventaRepository.findAll().stream()
            .filter(v -> v.getUsuario() != null)
            .filter(v -> v.getEstado() == Venta.EstadoVenta.COMPLETADA) // Opcional: solo ventas cerradas
            .collect(Collectors.groupingBy(
                    v -> v.getUsuario().getId(),
                    Collectors.collectingAndThen(
                            Collectors.toList(),
                            ventasPorCajero -> {
                                Venta muestra = ventasPorCajero.get(0);
                                String nombreUsuario = muestra.getUsuario().getNombre() + " " + muestra.getUsuario().getApellido();
                                BigDecimal totalVendido = ventasPorCajero.stream()
                                        .map(Venta::getTotal)
                                        .reduce(BigDecimal.ZERO, BigDecimal::add);

                                return new CajeroReporteDTO(muestra.getUsuario().getId(), nombreUsuario, totalVendido);
                            }
                    )
            ))
            .values().stream()
            .sorted(Comparator.comparing(CajeroReporteDTO::getTotalVendido).reversed())
            .collect(Collectors.toList());
}


    @Transactional(readOnly = true)
public List<ProductoReporteDTO> obtenerProductosMasVendidos() {
    return detalleVentaRepository.findAll().stream()
            .filter(d -> d.getProducto() != null)
            .collect(Collectors.groupingBy(
                    d -> d.getProducto().getId(),
                    Collectors.collectingAndThen(
                            Collectors.toList(),
                            detallesPorProducto -> {
                                var muestra = detallesPorProducto.get(0);
                                String nombre = muestra.getProducto().getNombre();
                                Long totalCantidad = detallesPorProducto.stream()
                                        .mapToLong(DetalleVenta::getCantidad)
                                        .sum();
                                return new ProductoReporteDTO(muestra.getProducto().getId(), nombre, totalCantidad);
                            }
                    )
            ))
            .values().stream()
            .sorted(Comparator.comparing(ProductoReporteDTO::getCantidadVendida).reversed())
            .collect(Collectors.toList());
}


}

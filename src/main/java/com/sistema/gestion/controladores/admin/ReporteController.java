package com.sistema.gestion.controladores.admin;

import com.sistema.gestion.dto.CajeroReporteDTO;
import com.sistema.gestion.dto.ClienteReporteDTO;
import com.sistema.gestion.dto.ProductoReporteDTO;
import com.sistema.gestion.servicios.ReporteService;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/reportes")
public class ReporteController {

    private final ReporteService reporteService;

    public ReporteController(ReporteService reporteService) {
        this.reporteService = reporteService;
    }

    // Clientes que más compran
    @GetMapping("/top-clientes")
    public List<ClienteReporteDTO> obtenerTopClientes() {
        return reporteService.obtenerTopClientes();
    }

    // Cajeros que más venden
    @GetMapping("/top-cajeros")
    public List<CajeroReporteDTO> obtenerTopCajeros() {
        return reporteService.obtenerTopCajeros();
    }

    // Productos más demandados
    @GetMapping("/productos-mas-vendidos")
    public List<ProductoReporteDTO> obtenerProductosMasVendidos() {
        return reporteService.obtenerProductosMasVendidos();
    }
}

package com.sistema.gestion.controladores.admin;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.sistema.gestion.dto.VentaDTO;
import com.sistema.gestion.dto.VentaServiceDTO;

@RestController
@RequestMapping("/api/admin/ventas")
public class VentaControllerAdmin {
     private final VentaServiceDTO ventaService;

    public VentaControllerAdmin(VentaServiceDTO ventaService) {
        this.ventaService = ventaService;
    }

    @PostMapping
    public ResponseEntity<VentaDTO> registrarVenta(@RequestBody VentaDTO ventaDTO) {
        VentaDTO ventaRegistrada = ventaService.registrarVentaDesdeDTO(ventaDTO);
        return new ResponseEntity<>(ventaRegistrada, HttpStatus.CREATED);
    }

    @GetMapping
    public List<VentaDTO> listarVentas() {
        return ventaService.obtenerTodasLasVentas();
    }

    @GetMapping("/{id}")
    public VentaDTO obtenerVentaPorId(@PathVariable Long id) {
        return ventaService.obtenerVentaPorId(id);
    }

}

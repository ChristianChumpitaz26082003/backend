package com.sistema.gestion.controladores.admin;

import com.sistema.gestion.modelo.MfaLog;
import com.sistema.gestion.repositorio.MfaLogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/mfa-logs")
public class MfaLogController {

    @Autowired
    private MfaLogRepository mfaLogRepositorio;

    @GetMapping
    public ResponseEntity<?> obtenerMfaLogs() {
        try {
            List<MfaLog> logs = mfaLogRepositorio.findAll();
            return ResponseEntity.ok(logs);
        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                    .body("Error al obtener los registros MFA: " + e.getMessage());
        }
    }
}

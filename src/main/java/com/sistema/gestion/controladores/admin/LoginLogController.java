package com.sistema.gestion.controladores.admin;

import com.sistema.gestion.modelo.LoginLog;
import com.sistema.gestion.repositorio.LoginLogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/login-logs")
public class LoginLogController {

    @Autowired
    private LoginLogRepository loginLogRepositorio;

    @GetMapping
    public ResponseEntity<?> obtenerLoginLogs() {
        try {
            List<LoginLog> logs = loginLogRepositorio.findAll();
            return ResponseEntity.ok(logs);
        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                    .body("Error al obtener los registros de login: " + e.getMessage());
        }
    }
}

package com.sistema.gestion.controladores.admin;


import com.sistema.gestion.dto.RegistroRequest;
import com.sistema.gestion.modelo.Usuario;
import com.sistema.gestion.modelo.MfaLog;
import com.sistema.gestion.modelo.LoginLog;
import com.sistema.gestion.repositorio.MfaLogRepository;
import com.sistema.gestion.repositorio.LoginLogRepository;
import com.sistema.gestion.servicios.RegistroUsuarioService;
import java.util.List;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin")
public class CRUDAdminController {

    private final RegistroUsuarioService registroUsuarioService;
    private final MfaLogRepository mfaLogRepository;
    private final LoginLogRepository loginLogRepository;

    public CRUDAdminController(RegistroUsuarioService registroUsuarioService,
            MfaLogRepository mfaLogRepository,
            LoginLogRepository loginLogRepository) {
        this.registroUsuarioService = registroUsuarioService;
        this.mfaLogRepository = mfaLogRepository;
        this.loginLogRepository = loginLogRepository;
       
    }

    @PostMapping("/registro")
    public ResponseEntity<?> registrarUsuario(@RequestBody RegistroRequest request) {
        try {
            Usuario nuevoUsuario = registroUsuarioService.registrarUsuario(request);
            return ResponseEntity.status(HttpStatus.CREATED).body(
                    Map.of("message", "Usuario registrado con éxito", "id", nuevoUsuario.getId()));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(
                    Map.of("error", "Error al registrar el usuario: " + e.getMessage()));
        }
    }

    @GetMapping("/logs/mfa")
    public ResponseEntity<List<MfaLog>> obtenerTodosLosLogsMfa() {
        List<MfaLog> logs = mfaLogRepository.findAll();
        return ResponseEntity.ok(logs);
    }

    @GetMapping("/logs/login")
    public ResponseEntity<List<LoginLog>> obtenerTodosLosLogsLogin() {
        List<LoginLog> logs = loginLogRepository.findAll();
        return ResponseEntity.ok(logs);
    }

    
}

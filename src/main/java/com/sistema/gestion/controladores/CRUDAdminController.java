package com.sistema.gestion.controladores;

import com.sistema.gestion.dto.RegistroRequest;
import com.sistema.gestion.modelo.Usuario;
import com.sistema.gestion.servicios.RegistroUsuarioService;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin")
public class CRUDAdminController {
    private final RegistroUsuarioService registroUsuarioService;

    public CRUDAdminController(RegistroUsuarioService registroUsuarioService) {
        this.registroUsuarioService = registroUsuarioService;
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
}

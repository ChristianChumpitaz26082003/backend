package com.sistema.gestion.controladores;

import com.sistema.gestion.configuracion.JwtUtils;
import com.sistema.gestion.dto.AuthRequest;
import com.sistema.gestion.dto.AuthResponse;
import com.sistema.gestion.modelo.LoginLog;
import com.sistema.gestion.repositorio.LoginLogRepository;
import com.sistema.gestion.servicios.UsuarioServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final UsuarioServiceImpl usuarioService;
    private final JwtUtils jwtUtils;
    private final LoginLogRepository loginLogRepository;
    private final HttpServletRequest request;

    public AuthController(AuthenticationManager authenticationManager,
                           UsuarioServiceImpl usuarioService,
                           JwtUtils jwtUtils,
                           LoginLogRepository loginLogRepository,
                           HttpServletRequest request) {
        this.authenticationManager = authenticationManager;
        this.usuarioService = usuarioService;
        this.jwtUtils = jwtUtils;
        this.loginLogRepository = loginLogRepository;
        this.request = request;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthRequest requestDto) {
        try {
            if (requestDto.getUsername() == null || requestDto.getUsername().trim().isEmpty() ||
                requestDto.getPassword() == null || requestDto.getPassword().trim().isEmpty()) {
                return ResponseEntity.badRequest().body("Username y password son requeridos");
            }
            authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                    requestDto.getUsername(),
                    requestDto.getPassword()
                )
            );
            
            final UserDetails userDetails = usuarioService.loadUserByUsername(requestDto.getUsername());
            LoginLog loginLog = new LoginLog();
            loginLog.setUsername(requestDto.getUsername());
            loginLog.setFechaHora(java.time.LocalDateTime.now());
            loginLog.setIp(request.getRemoteAddr());
            loginLogRepository.save(loginLog);
            final String token = jwtUtils.generateToken(userDetails);
            return ResponseEntity.ok(new AuthResponse(token));
        } catch (BadCredentialsException e) {
            return ResponseEntity.status(401).body("Credenciales inválidas");
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Error en el servidor: " + e.getMessage());
        }
    }
}

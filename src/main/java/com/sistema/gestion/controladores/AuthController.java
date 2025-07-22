package com.sistema.gestion.controladores;

import com.sistema.gestion.configuracion.JwtUtils;
import com.sistema.gestion.dto.AuthRequest;
import com.sistema.gestion.dto.AuthResponse;
import com.sistema.gestion.dto.MfaRequest;
import com.sistema.gestion.modelo.LoginLog;
import com.sistema.gestion.modelo.Usuario;
import com.sistema.gestion.repositorio.LoginLogRepository;
import com.sistema.gestion.servicios.MfaService;
import com.sistema.gestion.servicios.UsuarioService;
import jakarta.servlet.http.HttpServletRequest;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final UsuarioService usuarioService;
    private final JwtUtils jwtUtils;
    private final LoginLogRepository loginLogRepository;
    private final HttpServletRequest request;

    private final MfaService mfaService;

    public AuthController(AuthenticationManager authenticationManager,
            UsuarioService usuarioService,
            JwtUtils jwtUtils,
            LoginLogRepository loginLogRepository,
            HttpServletRequest request,
            MfaService mfaService) {
        this.authenticationManager = authenticationManager;
        this.usuarioService = usuarioService;
        this.jwtUtils = jwtUtils;
        this.loginLogRepository = loginLogRepository;
        this.request = request;
        this.mfaService = mfaService;
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
                            requestDto.getPassword()));
            Usuario usuario = usuarioService.getUsuarioByUsername(requestDto.getUsername());
            String otp = mfaService.generateOtp(requestDto.getUsername());
            mfaService.sendOtpEmail(usuario.getCorreo(), otp);
            LoginLog log = new LoginLog();
            log.setUsername(requestDto.getUsername());
            log.setIp(request.getRemoteAddr());
            log.setFechaHora(java.time.LocalDateTime.now());
            loginLogRepository.save(log);
            return ResponseEntity.ok("Código MFA enviado al correo electrónico");

        } catch (BadCredentialsException e) {
            return ResponseEntity.status(401).body("Credenciales inválidas");
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Error en el servidor: " + e.getMessage());
        }
    }

    @PostMapping("/login/mfa")
    public ResponseEntity<?> verifyMfa(@RequestBody MfaRequest mfaRequest) {
        boolean validOtp = mfaService.verifyOtp(mfaRequest.getUsername(), mfaRequest.getOtp());
        LoginLog log = new LoginLog();
        log.setUsername(mfaRequest.getUsername());
        log.setIp(request.getRemoteAddr());
        log.setFechaHora(java.time.LocalDateTime.now());
        loginLogRepository.save(log);
        if (!validOtp) {
            return ResponseEntity.status(401).body("Código MFA inválido o expirado");
        }
        Usuario usuario = usuarioService.getUsuarioByUsername(mfaRequest.getUsername());
        String token = jwtUtils.generateToken(usuario);
        return ResponseEntity.ok(new AuthResponse(token));
    }

}

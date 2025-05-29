package com.sistema.gestion.servicios;

import com.sistema.gestion.dto.RegistroRequest;
import com.sistema.gestion.modelo.Usuario;
import com.sistema.gestion.repositorio.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class RegistroUsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public Usuario registrarUsuario(RegistroRequest request) {
        Usuario nuevoUsuario = new Usuario();
        nuevoUsuario.setNombre(request.getNombre());
        nuevoUsuario.setApellido(request.getApellido());
        nuevoUsuario.setUsername(request.getUsername());
        nuevoUsuario.setCorreo(request.getCorreo());
        nuevoUsuario.setRol(request.getRol());
        String passwordEncriptada = passwordEncoder.encode(request.getPassword());
        nuevoUsuario.setPassword(passwordEncriptada);
        return usuarioRepository.save(nuevoUsuario);
    }
}

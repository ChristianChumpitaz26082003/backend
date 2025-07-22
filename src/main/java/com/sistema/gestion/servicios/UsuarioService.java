package com.sistema.gestion.servicios;

import com.sistema.gestion.modelo.Usuario;
import com.sistema.gestion.repositorio.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class UsuarioService implements UserDetailsService {

        private final UsuarioRepository usuarioRepository;

        @Autowired
        public UsuarioService(UsuarioRepository usuarioRepository) {
                this.usuarioRepository = usuarioRepository;
        }

        @Override
        public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
                Usuario usuario = usuarioRepository.findByUsername(username)
                                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado: " + username));
                List<GrantedAuthority> authorities = Collections.singletonList(
                                new SimpleGrantedAuthority("ROLE_" + usuario.getRol().toUpperCase()));
                return new User(
                                usuario.getUsername(),
                                usuario.getPassword(),
                                true,
                                true,
                                true,
                                true,
                                authorities);
        }

        public Usuario getUsuarioByUsername(String username) {
                return usuarioRepository.findByUsername(username)
                                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado: " + username));
        }

}
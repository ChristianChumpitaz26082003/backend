package com.sistema.gestion.dto;




import com.sistema.gestion.modelo.Usuario;

public class UsuarioMapper {

    public static UsuarioDTO toDTO(Usuario usuario) {
        return new UsuarioDTO(
                usuario.getId(),
                usuario.getNombre(),
                usuario.getApellido(),
                usuario.getUsername(),
                usuario.getCorreo(),
                usuario.getRol()
        );
    }

    public static Usuario toEntity(UsuarioDTO dto) {
        Usuario usuario = new Usuario();
        usuario.setId(dto.getId());
        usuario.setNombre(dto.getNombre());
        usuario.setApellido(dto.getApellido());
        usuario.setUsername(dto.getUsername());
        usuario.setCorreo(dto.getCorreo());
        usuario.setRol(dto.getRol());
        // La contraseña debe manejarse por separado (registro / cambio)
        return usuario;
    }
}

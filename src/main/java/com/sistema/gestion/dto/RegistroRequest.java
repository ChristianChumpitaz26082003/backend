package com.sistema.gestion.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data  
@NoArgsConstructor  
@AllArgsConstructor 
public class RegistroRequest {
    private String nombre;
    private String apellido;
    private String username;
    private String correo;
    private String password;
    private String rol;  
}
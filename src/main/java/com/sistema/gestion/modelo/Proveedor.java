package com.sistema.gestion.modelo;

import jakarta.persistence.*;
import lombok.*;


@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "Proveedor")
public class Proveedor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false, length = 100)
    private String nombre;
    
    @Column(length = 20)
    private String ruc;
    
    @Column(length = 200)
    private String direccion;
    
    @Column(length = 20)
    private String telefono;
    
    @Column(length = 100)
    private String correo;
    
    @Column(length = 100)
    private String contacto; 
    
    @Enumerated(EnumType.STRING)
    private TipoProveedor tipo;
    
    // Enum para tipo de proveedor
    public enum TipoProveedor {
        MAYORISTA,
        MINORISTA,
        FABRICANTE,
        IMPORTADOR
    }
}
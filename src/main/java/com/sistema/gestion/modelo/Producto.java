package com.sistema.gestion.modelo;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "Producto")
public class Producto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nombre;
    private String descripcion;
    private double precio;
    private int stock;
    private String idCategoria;
    private Long idProveedor;
    private String fechaCaducidad;
    private String fechaIngreso;
    private String estado;

}

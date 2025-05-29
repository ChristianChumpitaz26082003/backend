package com.sistema.gestion.modelo;

import java.time.LocalDate;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "Compra")
public class Compra {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDate fecha;
    private double total;
    @ManyToOne
    private Proveedor proveedor;
    @ManyToOne
    private Usuario usuario; // Quien hizo la compra
}

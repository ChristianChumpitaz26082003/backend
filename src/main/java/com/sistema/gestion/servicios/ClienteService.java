package com.sistema.gestion.servicios;

import com.sistema.gestion.modelo.Cliente;

import java.util.List;
import java.util.Optional;

public interface ClienteService {
    Cliente guardarCliente(Cliente cliente);
    List<Cliente> listarClientes();
    Optional<Cliente> obtenerClientePorId(Long id);
    Cliente actualizarCliente(Long id, Cliente cliente);
    void eliminarCliente(Long id);
}

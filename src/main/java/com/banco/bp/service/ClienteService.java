package com.banco.bp.service;

import com.banco.bp.dto.ClienteDTO;
import com.banco.bp.model.Cliente;

import java.util.List;

public interface ClienteService {
    List<ClienteDTO> findAllClientes();

    List<ClienteDTO> findActuallyAllClientes();
    ClienteDTO findClienteByNombre(String nombre);

    ClienteDTO saveCliente(ClienteDTO clienteDTO);

    void deleteClienteById(Long id);
}

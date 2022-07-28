package com.banco.bp.service.impl;

import com.banco.bp.dto.ClienteDTO;
import com.banco.bp.dto.mapper.ClienteMapper;
import com.banco.bp.model.Cliente;
import com.banco.bp.repository.ClienteRepository;
import com.banco.bp.service.ClienteService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ClienteServiceImpl implements ClienteService {
    private final ClienteMapper clienteMapper;
    private final ClienteRepository clienteRepository;

    public ClienteServiceImpl(ClienteMapper clienteMapper, ClienteRepository clienteRepository) {
        this.clienteMapper = clienteMapper;
        this.clienteRepository = clienteRepository;
    }


    @Override
    public List<ClienteDTO> findAllClientes() {
        return clienteRepository.findByEstadoTrue().stream()
                .map(clienteMapper::clienteToClienteDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<ClienteDTO> findActuallyAllClientes() {
        return clienteRepository.findAll().stream()
                .map(clienteMapper::clienteToClienteDTO)
                .collect(Collectors.toList());    }

    @Override
    public ClienteDTO findClienteByNombre(String nombre) {
        return clienteMapper.clienteToClienteDTO(clienteRepository.findByNombre(nombre));
    }

    @Override
    public ClienteDTO saveCliente(ClienteDTO clienteDTO) {
//        Cliente cliente = clienteRepository.findByIdentificacion(clienteDTO.getIdentificacion());
//        if(!cliente) return "El identificador ya lo tiene otro cliente, los clientes deben tenerlos únicos";
        Cliente cliente = clienteMapper.clienteDTOToCliente(clienteDTO);
        cliente.setEstado(true);
        Cliente savedCliente = clienteRepository.save(cliente);
        ClienteDTO returnDto = clienteMapper.clienteToClienteDTO(savedCliente);
        return returnDto;
    }

    @Override
    public void deleteClienteById(Long id) {
        Cliente cliente  = clienteRepository.findById(id).orElseThrow(null);
        cliente.setEstado(false);
        Cliente savedCliente = clienteRepository.save(cliente);
    }
}

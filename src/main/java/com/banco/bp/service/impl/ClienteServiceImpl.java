package com.banco.bp.service.impl;

import com.banco.bp.dto.ClienteDTO;
import com.banco.bp.dto.mapper.ClienteMapper;
import com.banco.bp.model.Cliente;
import com.banco.bp.repository.ClienteRepository;
import com.banco.bp.repository.CuentaRepository;
import com.banco.bp.repository.MovimientoRepository;
import com.banco.bp.service.ClienteService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
@Slf4j
@RequiredArgsConstructor
@Service
public class ClienteServiceImpl implements ClienteService {
    private final ClienteMapper clienteMapper;
    private final ClienteRepository clienteRepository;

    private final CuentaRepository cuentaRepository;

    private final MovimientoRepository movimientoRepository;

    @Override
    public List<ClienteDTO> findAllClientes() {
        log.info("Se están listando los clientes que tienen estado 'true' ");

        return clienteRepository.findByEstadoTrue().stream()
                .map(clienteMapper::clienteToClienteDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<ClienteDTO> findActuallyAllClientes() {
        log.info("Se están listando todos los clientes");

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
        log.info("Se está guardando el cliente");

        Cliente savedCliente = clienteRepository.save(cliente);
        ClienteDTO returnDto = clienteMapper.clienteToClienteDTO(savedCliente);
        return returnDto;
    }

    @Override
    public void deleteClienteById(Long id) {
        Cliente cliente  = clienteRepository.findById(id).orElse(null);
        if(cliente==null)throw new RuntimeException("El cliente con id + " + id+ " no existe");

        log.info("Se está eliminando (lógico) el cliente con ID {}, sus cuentas y movimientos", id);
        cliente.setEstado(false);
        Cliente savedCliente = clienteRepository.save(cliente);
        cuentaRepository.findByCliente(savedCliente).stream().map(c-> {
            c.setEstado(false);
            movimientoRepository.findByCuenta(c).stream().forEach(m-> movimientoRepository.deleteById(m.getId()));
            return c;
        });

    }
}

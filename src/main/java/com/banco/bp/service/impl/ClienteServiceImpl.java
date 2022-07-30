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

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
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
        Cliente cliente = clienteRepository.findByIdentificacion(clienteDTO.getIdentificacion());
        if(cliente!=null){
            try {
                throw new SQLIntegrityConstraintViolationException("El identificador ya lo tiene otro cliente, los clientes deben tenerlos únicos");
            } catch (SQLIntegrityConstraintViolationException e) {
                throw new RuntimeException(e);
            }
        }
        Cliente convertedCliente = clienteMapper.clienteDTOToCliente(clienteDTO);
        convertedCliente.setEstado(true);
        log.info("Se está guardando el cliente");

        return saveAndReturnDTO(convertedCliente);
    }
    @Override
    public ClienteDTO updateCliente(Long id, ClienteDTO clienteDTO) {
        Cliente convertedCliente = clienteMapper.clienteDTOToCliente(clienteDTO);
        convertedCliente.setId(id);
        convertedCliente.setEstado(true);
        log.info("Se está actualizando al cliente con id {}", id);
        return saveAndReturnDTO(convertedCliente);
    }


    private ClienteDTO saveAndReturnDTO(Cliente cliente){
        Cliente savedCliente = clienteRepository.save(cliente);
        ClienteDTO returnDto = clienteMapper.clienteToClienteDTO(savedCliente);
        return returnDto;
    }

    @Override
    public ClienteDTO patchCliente(Long id, ClienteDTO clienteDTO) {
        log.info("Se está actualizando al cliente con id {}", id);
        return clienteRepository.findById(id).map(cliente -> {
            if(clienteDTO.getIdentificacion()!=null){
                cliente.setIdentificacion(clienteDTO.getIdentificacion());
            }
            if(clienteDTO.getDireccion()!=null){
                cliente.setDireccion(clienteDTO.getDireccion());
            }
            if(clienteDTO.getGenero()!=null){
                cliente.setGenero(clienteDTO.getGenero());
            }
            if(clienteDTO.getNombre()!=null){
                cliente.setNombre(clienteDTO.getNombre());
            }
            if(clienteDTO.getPassword()!=null){
                cliente.setPassword(clienteDTO.getPassword());
            }
            if(clienteDTO.getTelefono()!=null){
                cliente.setTelefono(clienteDTO.getTelefono());
            }
            return clienteMapper.clienteToClienteDTO(clienteRepository.save(cliente));
        }).orElseThrow(()-> {
            throw new NoSuchElementException("El cliente con id " + id+ " no existe");
        });
    }
    @Override
    public void deleteClienteById(Long id) {
        Cliente cliente  = clienteRepository.findById(id).orElseThrow(()-> {
            throw new NoSuchElementException("El cliente con id " + id+ " no existe");
        });

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

package com.banco.bp.service.impl;

import com.banco.bp.dto.CuentaDTO;
import com.banco.bp.dto.mapper.CuentaMapper;
import com.banco.bp.model.Cliente;
import com.banco.bp.model.Cuenta;
import com.banco.bp.repository.CuentaRepository;
import com.banco.bp.service.CuentaService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
@Slf4j
@RequiredArgsConstructor
@Service
public class CuentaServiceImpl implements CuentaService {

    private final CuentaMapper cuentaMapper;

    private final CuentaRepository cuentaRepository;



    @Override
    public List<CuentaDTO> findAllCuentas() {
        log.info("Se están listando las cuentas que tienen estado 'true' ");

        return cuentaRepository.findByEstadoTrue().stream()
                .map(c->{
                    CuentaDTO c2 =cuentaMapper.cuentaToCuentaDTO(c);
                    c2.setClienteId(c.getCliente().getId());
                    return c2;
                })
                .collect(Collectors.toList());
    }

    @Override
    public List<CuentaDTO> findActuallyAllCuentas() {
        log.info("Se están listando todas las cuentas");

        return cuentaRepository.findAll().stream()
                .map(c->{
                    CuentaDTO c2 =cuentaMapper.cuentaToCuentaDTO(c);
                    c2.setClienteId(c.getCliente().getId());
                    return c2;
                })
                .collect(Collectors.toList());
    }

    @Override
    public CuentaDTO saveCuenta(CuentaDTO cuentaDTO) {
        Cuenta cuenta = cuentaMapper.cuentaDTOToCuenta(cuentaDTO);
        cuenta.setEstado(true);
        Cliente cliente = new Cliente();
        cliente.setId(cuentaDTO.getClienteId());
        cuenta.setCliente(cliente);
        log.info("Se está guardando la cuenta del cliente con ID {}", cuentaDTO.getClienteId());

        Cuenta savedCuenta = cuentaRepository.save(cuenta);
        CuentaDTO returnDto = cuentaMapper.cuentaToCuentaDTO(savedCuenta);
        returnDto.setClienteId(savedCuenta.getCliente().getId());
        return returnDto;
    }

    @Override
    public void deleteCuentaById(Long id) {
        Cuenta cuenta  = cuentaRepository.findById(id).orElse(null);
        if(cuenta==null)throw new RuntimeException("La cuenta con id + " + id+ " no existe");

        log.info("Se está eliminando (lógico) la cuenta con ID {}", id);
        cuenta.setEstado(false);
        Cuenta savedCuenta = cuentaRepository.save(cuenta);
    }
}

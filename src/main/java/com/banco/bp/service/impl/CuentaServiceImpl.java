package com.banco.bp.service.impl;

import com.banco.bp.dto.ClienteDTO;
import com.banco.bp.dto.CuentaDTO;
import com.banco.bp.dto.mapper.CuentaMapper;
import com.banco.bp.model.Cliente;
import com.banco.bp.model.Cuenta;
import com.banco.bp.repository.CuentaRepository;
import com.banco.bp.service.CuentaService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CuentaServiceImpl implements CuentaService {

    private final CuentaMapper cuentaMapper;

    private final CuentaRepository cuentaRepository;

    public CuentaServiceImpl(CuentaMapper cuentaMapper, CuentaRepository cuentaRepository) {
        this.cuentaMapper = cuentaMapper;
        this.cuentaRepository = cuentaRepository;
    }


    @Override
    public List<CuentaDTO> findAllCuentas() {
        return cuentaRepository.findByEstadoTrue().stream()
                .map(cuentaMapper::cuentaToCuentaDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<CuentaDTO> findActuallyAllCuentas() {
        return cuentaRepository.findAll().stream()
                .map(cuentaMapper::cuentaToCuentaDTO)
                .collect(Collectors.toList());
    }

    @Override
    public CuentaDTO saveCuenta(CuentaDTO cuentaDTO) {
        Cuenta cuenta = cuentaMapper.cuentaDTOToCuenta(cuentaDTO);
        cuenta.setEstado(true);
        Cuenta savedCuenta = cuentaRepository.save(cuenta);
        CuentaDTO returnDto = cuentaMapper.cuentaToCuentaDTO(savedCuenta);
        return returnDto;
    }

    @Override
    public void deleteCuentaById(Long id) {
        Cuenta cuenta  = cuentaRepository.findById(id).orElseThrow(null);
        cuenta.setEstado(false);
        Cuenta savedCuenta = cuentaRepository.save(cuenta);
    }
}

package com.banco.bp.service;

import com.banco.bp.dto.ClienteDTO;
import com.banco.bp.dto.CuentaDTO;
import com.banco.bp.model.Cuenta;
import org.springframework.stereotype.Service;

import java.util.List;

public interface CuentaService {
    List<CuentaDTO> findAllCuentas();
    List<CuentaDTO> findActuallyAllCuentas();

    CuentaDTO saveCuenta(CuentaDTO cuentaDTO);

    CuentaDTO updateCuenta(Long id, CuentaDTO cuentaDTO);
    CuentaDTO patchCuenta(Long id, CuentaDTO cuentaDTO);


    void deleteCuentaById(Long id);

}

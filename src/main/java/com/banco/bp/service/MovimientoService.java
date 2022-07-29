package com.banco.bp.service;

import com.banco.bp.dto.CuentaDTO;
import com.banco.bp.dto.MovimientoDTO;
import com.banco.bp.dto.response.MovimientoListDTO;

import java.util.List;

public interface MovimientoService {
    List<MovimientoDTO> findAllMovimientos();

    MovimientoDTO saveMovimiento(MovimientoDTO movimientoDTO);

    void deleteMovimientoById(Long id);
}

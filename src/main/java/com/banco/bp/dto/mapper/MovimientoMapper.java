package com.banco.bp.dto.mapper;

import com.banco.bp.dto.CuentaDTO;
import com.banco.bp.dto.MovimientoDTO;
import com.banco.bp.model.Movimiento;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface MovimientoMapper {

    MovimientoMapper INSTANCE = Mappers.getMapper(MovimientoMapper.class);

    MovimientoDTO movimientoToMovimientoDTO(Movimiento movimiento);
    Movimiento movimientoDTOToMovimiento(MovimientoDTO movimientoDTO);
}

package com.banco.bp.dto.mapper;

import com.banco.bp.dto.CuentaDTO;
import com.banco.bp.model.Cuenta;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface CuentaMapper {

    CuentaMapper INSTANCE = Mappers.getMapper(CuentaMapper.class);

    CuentaDTO cuentaToCuentaDTO(Cuenta cuenta);
    Cuenta cuentaDTOToCuenta(CuentaDTO cuentaDTO);
}

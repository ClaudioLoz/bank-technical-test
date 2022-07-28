package com.banco.bp.dto.mapper;

import com.banco.bp.dto.ClienteDTO;
import com.banco.bp.model.Cliente;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ClienteMapper {

    ClienteMapper INSTANCE = Mappers.getMapper(ClienteMapper.class);
    ClienteDTO clienteToClienteDTO(Cliente cliente);
    Cliente clienteDTOToCliente(ClienteDTO clienteDTO);
}

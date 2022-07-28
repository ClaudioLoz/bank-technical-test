package com.banco.bp.dto.response;

import com.banco.bp.dto.ClienteDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ClienteListDTO {
    List<ClienteDTO> clientes;
}

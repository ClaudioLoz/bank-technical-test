package com.banco.bp.dto.response;

import com.banco.bp.dto.MovimientoDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MovimientoListDTO {
    List<MovimientoDTO> movimientos;
}

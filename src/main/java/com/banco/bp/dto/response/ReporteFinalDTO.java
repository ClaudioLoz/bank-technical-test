package com.banco.bp.dto.response;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ReporteFinalDTO {
    List<ReporteDTO> movimientos;
    Double montoTotalRetiro;
    Double montoTotalDeposito;
}

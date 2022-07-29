package com.banco.bp.dto;

import com.banco.bp.enums.TipoCuenta;
import com.banco.bp.enums.TipoMovimiento;
import com.banco.bp.model.Cuenta;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MovimientoDTO {

    private Long id;
    private Date fecha;
    private TipoMovimiento tipoMovimiento;
    private Double valor;
    private Double saldoDisponible;
    private Long cuentaId;

}

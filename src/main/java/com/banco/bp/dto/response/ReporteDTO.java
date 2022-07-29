package com.banco.bp.dto.response;

import com.banco.bp.dto.MovimientoDTO;
import com.banco.bp.enums.TipoCuenta;
import com.banco.bp.enums.TipoMovimiento;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Date;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ReporteDTO {
    private Date fecha;
    private String cliente;
    private String numeroCuenta;
    private TipoCuenta tipoCuenta;
    private Double saldoInicial;
    private Boolean estado;
    private String movimiento;
    private Double saldoDisponible;

}

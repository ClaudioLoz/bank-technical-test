package com.banco.bp.service.impl;

import com.banco.bp.dto.response.ReporteDTO;
import com.banco.bp.dto.response.ReporteFinalDTO;
import com.banco.bp.enums.TipoMovimiento;
import com.banco.bp.model.Cliente;
import com.banco.bp.model.Cuenta;
import com.banco.bp.model.Movimiento;
import com.banco.bp.repository.ClienteRepository;
import com.banco.bp.repository.CuentaRepository;
import com.banco.bp.repository.MovimientoRepository;
import com.banco.bp.service.ReporteService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
public class ReporteServiceImpl implements ReporteService {

    private final ClienteRepository clienteRepository;
    private final CuentaRepository cuentaRepository;
    private final MovimientoRepository movimientoRepository;

    @Override
    public ReporteFinalDTO getReporte(String startDate,String endDate, Long clientId) {
        log.info("Se está elaborando el reporte para el rango de fechas {} - {} del cliente con ID {} ", startDate,endDate,clientId);
        Cliente cliente  = clienteRepository.findById(clientId).orElse(null);
        List<Cuenta> cuentas = cuentaRepository.findByCliente(cliente);
        List<Movimiento> movimientos = movimientoRepository.findByCuentaInAndFechaBetween(cuentas
                ,Date.valueOf(startDate),Date.valueOf(endDate));
        log.info("La cantidad de movimientos encontrados es {}",movimientos.size());
        List<ReporteDTO> movimientosReporte = movimientos.stream().map(m->{
            ReporteDTO reporteDTO = new ReporteDTO();
            reporteDTO.setFecha(m.getFecha());
            reporteDTO.setCliente(cliente.getNombre());
            reporteDTO.setNumeroCuenta(m.getCuenta().getNumeroCuenta());
            reporteDTO.setTipoCuenta(m.getCuenta().getTipoCuenta());
            reporteDTO.setSaldoInicial(m.getCuenta().getSaldoInicial());
            reporteDTO.setEstado(m.getCuenta().getEstado());
            String sign= "";
            if(m.getTipoMovimiento()== TipoMovimiento.RETIRO) sign="-";
            reporteDTO.setMovimiento(sign + m.getValor());
            reporteDTO.setSaldoDisponible(m.getSaldoDisponible());
            return reporteDTO;
        }).collect(Collectors.toList());

        Double montoTotalRetiro = movimientos.stream().map(m->{
            if(m.getTipoMovimiento()== TipoMovimiento.RETIRO) return m.getValor();
            return (double)0;
        }).collect(Collectors.toList()).stream().reduce((double)0,Double::sum);

        Double montoTotalDeposito = movimientos.stream().map(m->{
            if(m.getTipoMovimiento()== TipoMovimiento.DEPOSITO) return m.getValor();
            return (double)0;
        }).collect(Collectors.toList()).stream().reduce((double)0,Double::sum);

        return new ReporteFinalDTO(movimientosReporte,montoTotalRetiro,montoTotalDeposito);
    }
}

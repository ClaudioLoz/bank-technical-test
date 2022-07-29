package com.banco.bp.service.impl;

import com.banco.bp.dto.CuentaDTO;
import com.banco.bp.dto.MovimientoDTO;
import com.banco.bp.dto.mapper.MovimientoMapper;
import com.banco.bp.dto.response.MovimientoListDTO;
import com.banco.bp.enums.TipoMovimiento;
import com.banco.bp.model.Cliente;
import com.banco.bp.model.Cuenta;
import com.banco.bp.model.Movimiento;
import com.banco.bp.repository.CuentaRepository;
import com.banco.bp.repository.MovimientoRepository;
import com.banco.bp.service.MovimientoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.util.List;
import java.util.stream.Collectors;
@Slf4j
@RequiredArgsConstructor
@Transactional
@Service
public class MovimientoServiceImpl implements MovimientoService {

    @Value("${limit.daily.withdrawal}")
    private Double limitDailyWithdrawal;
    private final MovimientoMapper movimientoMapper;
    private final MovimientoRepository movimientoRepository;
    private final CuentaRepository cuentaRepository;

    @Override
    public List<MovimientoDTO> findAllMovimientos() {
        log.info("Se están listando todos los movimientos");
        return movimientoRepository.findAll().stream()
                .map(mv->{
                    MovimientoDTO mv2 =movimientoMapper.movimientoToMovimientoDTO(mv);
                    mv2.setCuentaId(mv.getCuenta().getId());
                    return mv2;
                })
                .collect(Collectors.toList());
    }
    @Override
    public MovimientoDTO saveMovimiento(MovimientoDTO movimientoDTO) {
        Movimiento movimiento = movimientoMapper.movimientoDTOToMovimiento(movimientoDTO);
        Cuenta cuenta = new Cuenta();
        cuenta.setId(movimientoDTO.getCuentaId());
        movimiento.setCuenta(cuenta);
        movimiento.setFecha(new Date(new java.util.Date().getTime()));

        List<Movimiento> previousMovimientos = movimientoRepository.findByCuenta(cuenta);
        Double newSaldo;
        if(previousMovimientos.size()==0){
            Cuenta cuenta2 = cuentaRepository.findById(movimientoDTO.getCuentaId()).orElse(null);
            newSaldo = cuenta2.getSaldoInicial();
        }
        else newSaldo = previousMovimientos.get(previousMovimientos.size()-1).getSaldoDisponible();

        if(movimiento.getTipoMovimiento() == TipoMovimiento.DEPOSITO){
            newSaldo+=movimiento.getValor();
        }else if(movimiento.getTipoMovimiento() == TipoMovimiento.RETIRO){
            if(newSaldo==0 || movimiento.getValor()>newSaldo) log.warn("Saldo no disponible"); //TODO:Exception
            Double totalDayWithdrawal = movimientoRepository
                    .findByCuentaAndFechaAndTipoMovimiento(cuenta,new Date(new java.util.Date().getTime()),TipoMovimiento.RETIRO)
                    .stream().map(m->m.getValor()).collect(Collectors.toList())
                    .stream().reduce((double) 0,Double::sum);
            log.info("El monto de retiros en el dia de hoy es igual a {}$ para la cuenta con ID {} ",totalDayWithdrawal,movimientoDTO.getCuentaId());
            if(limitDailyWithdrawal - (totalDayWithdrawal+ movimiento.getValor())<0) log.warn("Cupo diario excedido");  //TODO:Exception
            newSaldo-=movimiento.getValor();
        }
        movimiento.setSaldoDisponible(newSaldo);

        log.info("Se está guardando el movimiento de la cuenta con ID {}", movimientoDTO.getCuentaId());
        Movimiento savedMovimiento = movimientoRepository.save(movimiento);
        MovimientoDTO returnDto = movimientoMapper.movimientoToMovimientoDTO(savedMovimiento);
        returnDto.setCuentaId(savedMovimiento.getCuenta().getId());
        return returnDto;
    }

    @Override
    public void deleteMovimientoById(Long id) {

        Movimiento movimientoToDelete = movimientoRepository.findById(id).orElse(null);
        if(movimientoToDelete==null)throw new RuntimeException("El movimiento con id + " + id+ " no existe");
        log.info("Se está eliminando (físico) el movimiento con ID {}", id);
        Cuenta cuenta =  movimientoToDelete.getCuenta();
        if ( movimientoToDelete.getTipoMovimiento() == TipoMovimiento.DEPOSITO)
            cuenta.setSaldoInicial( cuenta.getSaldoInicial() - movimientoToDelete.getValor());
        else if ( movimientoToDelete.getTipoMovimiento() == TipoMovimiento.RETIRO)
            cuenta.setSaldoInicial( cuenta.getSaldoInicial() + movimientoToDelete.getValor());

        movimientoRepository.deleteById(id);
    }
}

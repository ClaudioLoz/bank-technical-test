package com.banco.bp.repository;

import com.banco.bp.enums.TipoMovimiento;
import com.banco.bp.model.Cliente;
import com.banco.bp.model.Cuenta;
import com.banco.bp.model.Movimiento;
import org.springframework.data.jpa.repository.JpaRepository;

import java.sql.Date;
import java.util.List;

public interface MovimientoRepository extends JpaRepository<Movimiento,Long> {
    List<Movimiento> findByCuenta(Cuenta cuenta);
    List<Movimiento> findByCuentaInAndFechaBetween(List<Cuenta> cuentas,Date startDate,Date endDate );
    List<Movimiento> findByCuentaAndFechaAndTipoMovimiento(Cuenta cuenta, Date fecha, TipoMovimiento tipoMovimiento);

}

package com.banco.bp.repository;

import com.banco.bp.model.Cuenta;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CuentaRepository extends JpaRepository<Cuenta,Long> {
    List<Cuenta> findByEstadoTrue();

}

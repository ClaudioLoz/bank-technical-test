package com.banco.bp.repository;

import com.banco.bp.model.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ClienteRepository extends JpaRepository<Cliente,Long> {
    Cliente findByIdentificacion(String identificacion);
    Cliente findByNombre(String nombre);
    List<Cliente> findByEstadoTrue();

}

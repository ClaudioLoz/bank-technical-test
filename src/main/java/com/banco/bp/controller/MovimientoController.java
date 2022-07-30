package com.banco.bp.controller;

import com.banco.bp.dto.ClienteDTO;
import com.banco.bp.dto.CuentaDTO;
import com.banco.bp.dto.MovimientoDTO;
import com.banco.bp.dto.response.CuentaListDTO;
import com.banco.bp.dto.response.MovimientoListDTO;
import com.banco.bp.service.MovimientoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/movimientos")
public class MovimientoController {
    private final MovimientoService movimientoService;

    @GetMapping
    public ResponseEntity<MovimientoListDTO> findAllMovimientos(){
        return new ResponseEntity<MovimientoListDTO>(
                new MovimientoListDTO(movimientoService.findAllMovimientos()), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<MovimientoDTO>saveMovimiento(@RequestBody MovimientoDTO movimientoDTO){
        return new ResponseEntity<MovimientoDTO>(movimientoService.saveMovimiento(movimientoDTO), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<MovimientoDTO>updateMovimiento(@PathVariable Long id, @RequestBody MovimientoDTO movimientoDTO){
        return new ResponseEntity<MovimientoDTO>(movimientoService.updateMovimiento(id,movimientoDTO), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void>deleteMovimientoById(@PathVariable Long id){
        movimientoService.deleteMovimientoById(id);
        return new ResponseEntity<Void>(HttpStatus.OK);
    }
}

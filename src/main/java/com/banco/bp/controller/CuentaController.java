package com.banco.bp.controller;

import com.banco.bp.dto.ClienteDTO;
import com.banco.bp.dto.CuentaDTO;
import com.banco.bp.dto.response.ClienteListDTO;
import com.banco.bp.dto.response.CuentaListDTO;
import com.banco.bp.service.CuentaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/cuentas")
public class CuentaController {
    private final CuentaService cuentaService;

    @GetMapping("/auditoria")
    public ResponseEntity<CuentaListDTO> findActuallyAllCuentas(){
        return new ResponseEntity<CuentaListDTO>(
                new CuentaListDTO(cuentaService.findActuallyAllCuentas()), HttpStatus.OK);
    }

//    @GetMapping("/{clienteId}")
//    public ResponseEntity<CuentaDTO>findCuentasByIdCliente(@PathVariable String clienteId){
//        return new ResponseEntity<CuentaDTO>(cuentaService.findClienteByNombre(clienteId), HttpStatus.OK);
//    }
    @GetMapping
    public ResponseEntity<CuentaListDTO>findAllCuentas(){
        return new ResponseEntity<CuentaListDTO>(
                new CuentaListDTO(cuentaService.findAllCuentas()), HttpStatus.OK);
    }


    @PostMapping
    public ResponseEntity<CuentaDTO>saveCuenta(@RequestBody CuentaDTO cuentaDTO){
        return new ResponseEntity<CuentaDTO>(cuentaService.saveCuenta(cuentaDTO), HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void>deleteCuentaById(@PathVariable Long id){
        cuentaService.deleteCuentaById(id);
        return new ResponseEntity<Void>(HttpStatus.OK);
    }



}

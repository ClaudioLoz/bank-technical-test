package com.banco.bp.controller;


import com.banco.bp.dto.ClienteDTO;
import com.banco.bp.dto.response.ClienteListDTO;
import com.banco.bp.service.ClienteService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
@RequiredArgsConstructor
@RestController
@RequestMapping("/clientes")
public class ClienteController {
    private final ClienteService clienteService;


    @GetMapping("/auditoria")
    public ResponseEntity<ClienteListDTO>findActuallyAllClientes(){
        return new ResponseEntity<ClienteListDTO>(
                new ClienteListDTO(clienteService.findActuallyAllClientes()), HttpStatus.OK);
    }

//    @GetMapping("/{nombre}")
//    public ResponseEntity<ClienteDTO>findClienteByName(@PathVariable String nombre){
//        return new ResponseEntity<ClienteDTO>(clienteService.findClienteByNombre(nombre), HttpStatus.OK);
//    }
    @GetMapping
    public ResponseEntity<ClienteListDTO>findAllClientes(){
        return new ResponseEntity<ClienteListDTO>(
                new ClienteListDTO(clienteService.findAllClientes()), HttpStatus.OK);
    }


    @PostMapping
    public ResponseEntity<ClienteDTO>saveCliente(@RequestBody ClienteDTO clienteDTO){
        return new ResponseEntity<ClienteDTO>(clienteService.saveCliente(clienteDTO), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ClienteDTO>updateCliente(@PathVariable Long id,@RequestBody ClienteDTO clienteDTO){
        return new ResponseEntity<ClienteDTO>(clienteService.updateCliente(id,clienteDTO), HttpStatus.OK);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ClienteDTO>patchCliente(@PathVariable Long id,@RequestBody ClienteDTO clienteDTO){
        return new ResponseEntity<ClienteDTO>(clienteService.patchCliente(id,clienteDTO), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void>deleteClienteById(@PathVariable Long id){
        clienteService.deleteClienteById(id);
        return new ResponseEntity<Void>(HttpStatus.OK);
    }

}

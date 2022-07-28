package com.banco.bp.controller;


import com.banco.bp.dto.ClienteDTO;
import com.banco.bp.dto.response.ClienteListDTO;
import com.banco.bp.service.ClienteService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/clientes")
public class ClienteController {
    private final ClienteService clienteService;

    public ClienteController(ClienteService clienteService) {
        this.clienteService = clienteService;
    }

    @GetMapping("/auditoria")
    public ResponseEntity<ClienteListDTO>findActuallyAllClientes(){
        return new ResponseEntity<ClienteListDTO>(
                new ClienteListDTO(clienteService.findActuallyAllClientes()), HttpStatus.OK);
    }

    @GetMapping("/{nombre}")
    public ResponseEntity<ClienteDTO>findClienteByName(@PathVariable String nombre){
        return new ResponseEntity<ClienteDTO>(clienteService.findClienteByNombre(nombre), HttpStatus.OK);
    }
    @GetMapping
    public ResponseEntity<ClienteListDTO>findAllClientes(){
        return new ResponseEntity<ClienteListDTO>(
                new ClienteListDTO(clienteService.findAllClientes()), HttpStatus.OK);
    }


    @PostMapping
    public ResponseEntity<ClienteDTO>saveCliente(@RequestBody ClienteDTO clienteDTO){
        return new ResponseEntity<ClienteDTO>(clienteService.saveCliente(clienteDTO), HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void>deleteClienteById(@PathVariable Long id){
        clienteService.deleteClienteById(id);
        return new ResponseEntity<Void>(HttpStatus.OK);
    }

}

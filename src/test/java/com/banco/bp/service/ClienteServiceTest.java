package com.banco.bp.service;

import com.banco.bp.dto.ClienteDTO;
import com.banco.bp.dto.mapper.ClienteMapper;
import com.banco.bp.model.Cliente;
import com.banco.bp.repository.ClienteRepository;
import com.banco.bp.repository.CuentaRepository;
import com.banco.bp.repository.MovimientoRepository;
import com.banco.bp.service.impl.ClienteServiceImpl;
import com.mysql.cj.xdevapi.Client;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

public class ClienteServiceTest {

    ClienteService clienteService;

    @Mock
    ClienteRepository clienteRepository;

    @Mock
    CuentaRepository cuentaRepository;

    @Mock
    MovimientoRepository movimientoRepository;
    private AutoCloseable closeable;

    @BeforeEach
    void setUp() {
        closeable = MockitoAnnotations.openMocks(this);
        clienteService = new ClienteServiceImpl(ClienteMapper.INSTANCE,clienteRepository,cuentaRepository,movimientoRepository);
    }

    @Test
    void findActuallyAllClientes() {
        //given
        List<Cliente> clients = Arrays.asList(new Cliente(),new Cliente(),new Cliente(),new Cliente());

        when(clienteRepository.findAll()).thenReturn(clients);
        //when
        List<ClienteDTO> clientDTOS = clienteService.findActuallyAllClientes();

        //then
        assertEquals(2,clientDTOS.size());

    }
}

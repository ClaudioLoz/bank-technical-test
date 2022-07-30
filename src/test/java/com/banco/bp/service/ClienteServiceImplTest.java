package com.banco.bp.service;

import com.banco.bp.dto.ClienteDTO;
import com.banco.bp.dto.mapper.ClienteMapper;
import com.banco.bp.enums.Genero;
import com.banco.bp.model.Cliente;
import com.banco.bp.repository.ClienteRepository;
import com.banco.bp.repository.CuentaRepository;
import com.banco.bp.repository.MovimientoRepository;
import com.banco.bp.service.impl.ClienteServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class ClienteServiceImplTest {

    private final static Long ID = 77L;
    private final static String NAME = "Jose";
    private final static Genero GENDER = Genero.HOMBRE;
    private final static String IDENTIFICATION = "77003397";
    private final static String ADDRESS = "Calle Navarra 221";
    private final static String PHONE = "999333666";
    private final static String PASSWORD = "A1B2C3";
    private final static Boolean STATE = true;
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
        List<Cliente> clients = Arrays.asList(new Cliente(),new Cliente());

        when(clienteRepository.findAll()).thenReturn(clients);
        //when
        List<ClienteDTO> clientDTOS = clienteService.findActuallyAllClientes();

        //then
        assertEquals(2,clientDTOS.size());

    }

    @Test
    void saveCliente() {
        //given
        ClienteDTO clienteDTO = new ClienteDTO();
        clienteDTO.setNombre(NAME);
        clienteDTO.setIdentificacion(IDENTIFICATION);
        clienteDTO.setPassword(PASSWORD);
        clienteDTO.setDireccion(ADDRESS);
        clienteDTO.setTelefono(PHONE);
        clienteDTO.setGenero(GENDER);

        Cliente savedCliente = new Cliente();
        savedCliente.setNombre(NAME);
        savedCliente.setIdentificacion(IDENTIFICATION);
        savedCliente.setPassword(PASSWORD);
        savedCliente.setDireccion(ADDRESS);
        savedCliente.setTelefono(PHONE);
        savedCliente.setGenero(GENDER);
        savedCliente.setEstado(STATE);
        savedCliente.setId(ID);

        when(clienteRepository.save(any(Cliente.class))).thenReturn(savedCliente);

        //when
        ClienteDTO savedDTO = clienteService.saveCliente(clienteDTO);

        //then
        assertEquals(clienteDTO.getNombre(), savedDTO.getNombre());
        assertEquals(clienteDTO.getIdentificacion(), savedDTO.getIdentificacion());
        assertEquals(clienteDTO.getPassword(), savedDTO.getPassword());
        assertEquals(clienteDTO.getDireccion(), savedDTO.getDireccion());
        assertEquals(clienteDTO.getTelefono(), savedDTO.getTelefono());
        assertEquals(clienteDTO.getGenero(), savedDTO.getGenero());
    }
}

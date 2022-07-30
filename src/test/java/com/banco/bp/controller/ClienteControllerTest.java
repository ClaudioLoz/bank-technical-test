package com.banco.bp.controller;

import com.banco.bp.dto.ClienteDTO;
import com.banco.bp.dto.response.ClienteListDTO;
import com.banco.bp.enums.Genero;
import com.banco.bp.exception.RestResponseEntityExceptionHandler;
import com.banco.bp.service.ClienteService;
import org.junit.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;

import static com.banco.bp.controller.AbstractRestControllerTest.asJsonString;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.refEq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(controllers = {ClienteController.class})
public class ClienteControllerTest {

    private final static Long ID = 77L;
    private final static String NAME = "Jose";
    private final static Genero GENDER = Genero.HOMBRE;
    private final static String IDENTIFICATION = "77003397";
    private final static String ADDRESS = "Calle Navarra 221";
    private final static String PHONE = "999333666";
    private final static String PASSWORD = "A1B2C3";
    private final static Boolean STATE = true;

    @MockBean
    ClienteService clienteService; //provided by Spring Context

    @Autowired
    MockMvc mockMvc; //provided by Spring Context
    ClienteDTO clienteDTO ;

    @BeforeEach
    public void setUp() {
        clienteDTO = new ClienteDTO();
        clienteDTO.setNombre(NAME);
        clienteDTO.setIdentificacion(IDENTIFICATION);
        clienteDTO.setPassword(PASSWORD);
        clienteDTO.setDireccion(ADDRESS);
        clienteDTO.setTelefono(PHONE);
        clienteDTO.setGenero(GENDER);
        clienteDTO.setEstado(STATE);
        clienteDTO.setId(ID);
    }

    @Test
    public void findActuallyAllClientes() throws Exception {
        //given
        given(clienteService.findActuallyAllClientes()).willReturn(Arrays.asList(clienteDTO,clienteDTO,clienteDTO));

        //when/then
         mockMvc.perform(get("/clientes/auditoria")
                        .contentType(MediaType.APPLICATION_JSON))
                        .andExpect(status().isOk())
                        .andExpect(jsonPath("$.clientes", hasSize(3)));
    }
}

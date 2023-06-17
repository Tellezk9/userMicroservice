package com.pragma.powerup.usermicroservice.adapters.driving.http.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pragma.powerup.usermicroservice.adapters.driving.http.dto.request.ClientRequestDto;
import com.pragma.powerup.usermicroservice.adapters.driving.http.dto.response.ClientResponseDto;
import com.pragma.powerup.usermicroservice.adapters.driving.http.handlers.IClientHandler;
import com.pragma.powerup.usermicroservice.configuration.Constants;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class ClientControllerRestTest {

    @Mock
    private IClientHandler clientHandler;
    @InjectMocks
    private ClientControllerRest clientControllerRest;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(clientControllerRest).build();
    }

    @Test
    void saveClient() throws Exception {
        ClientRequestDto clientRequestDto = new ClientRequestDto("testName", "testLastName", 1234, "+439094230412", "2002/05/01", "test@gmail.com", "string");
        doNothing().when(clientHandler).saveClient((Mockito.any(ClientRequestDto.class)));

        mockMvc.perform(post("/client/saveClient")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(clientRequestDto)))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.message").value(Constants.USER_CREATED_MESSAGE));
    }

    @Test
    void getProviderByDni() throws Exception{
        Integer idClient = 1;
        ClientResponseDto clientResponseDto = new ClientResponseDto(1,"testName", "testLastName", 1234, "+439094230412", "2002/05/01", "test@gmail.com");
        when(clientHandler.getClientById(idClient)).thenReturn(clientResponseDto);

        mockMvc.perform(get("/client/getClient/"+idClient))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(clientResponseDto.getId()))
                .andExpect(jsonPath("$.name").value(clientResponseDto.getName()))
                .andExpect(jsonPath("$.lastName").value(clientResponseDto.getLastName()))
                .andExpect(jsonPath("$.dniNumber").value(clientResponseDto.getDniNumber()))
                .andExpect(jsonPath("$.phone").value(clientResponseDto.getPhone()))
                .andExpect(jsonPath("$.birthDate").value(clientResponseDto.getBirthDate()))
                .andExpect(jsonPath("$.mail").value(clientResponseDto.getMail()));
    }
}
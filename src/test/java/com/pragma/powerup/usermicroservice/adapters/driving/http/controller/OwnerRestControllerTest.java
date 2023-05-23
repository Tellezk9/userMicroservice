package com.pragma.powerup.usermicroservice.adapters.driving.http.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pragma.powerup.usermicroservice.adapters.driving.http.dto.request.OwnerRequestDto;
import com.pragma.powerup.usermicroservice.adapters.driving.http.dto.response.OwnerResponseDto;
import com.pragma.powerup.usermicroservice.adapters.driving.http.handlers.IOwnerHandler;
import com.pragma.powerup.usermicroservice.adapters.driving.http.handlers.IUserHandler;
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

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class OwnerRestControllerTest {

    @Mock
    private IOwnerHandler ownerHandler;
    @InjectMocks
    private OwnerRestController ownerRestController;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {mockMvc = MockMvcBuilders.standaloneSetup(ownerRestController).build();
    }

    @Test
    void saveOwnerSuccess() throws Exception {
        OwnerRequestDto ownerRequestDto = new OwnerRequestDto("testName", "testLastName", 1234, "+439094230412", "2002/05/01", "test@gmail.com", "string");
        doNothing().when(ownerHandler).saveOwner((Mockito.any(OwnerRequestDto.class)));

        mockMvc.perform(post("/owner")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(ownerRequestDto)))
                .andDo(print())
                .andExpect(status().isAccepted())
                .andExpect(jsonPath("$.message").value(Constants.USER_CREATED_MESSAGE));
    }
    @Test
    void getOwner() throws Exception{
         OwnerResponseDto ownerResponseDto= new OwnerResponseDto(1L,"testName", "testLastName", 1234, "+439094230412", "2002/05/01", "test@gmail.com", "string");
         when(ownerHandler.getOwnerByDni(1)).thenReturn(ownerResponseDto);

         mockMvc.perform(get("/owner/getOwner/"+1))
                 .andDo(print())
                 .andExpect(status().isOk())
                 .andExpect(jsonPath("$.id").value(ownerResponseDto.getId()))
                 .andExpect(jsonPath("$.name").value(ownerResponseDto.getName()))
                 .andExpect(jsonPath("$.lastName").value(ownerResponseDto.getLastName()))
                 .andExpect(jsonPath("$.dniNumber").value(ownerResponseDto.getDniNumber()))
                 .andExpect(jsonPath("$.phone").value(ownerResponseDto.getPhone()))
                 .andExpect(jsonPath("$.birthDate").value(ownerResponseDto.getBirthDate()))
                 .andExpect(jsonPath("$.mail").value(ownerResponseDto.getMail()))
                 .andExpect(jsonPath("$.password").value(ownerResponseDto.getPassword()));

         verify(ownerHandler, times(1)).getOwnerByDni(1);
    }
}
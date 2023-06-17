package com.pragma.powerup.usermicroservice.adapters.driving.http.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pragma.powerup.usermicroservice.adapters.driving.http.dto.request.EmployeeRequestDto;
import com.pragma.powerup.usermicroservice.adapters.driving.http.dto.response.EmployeeResponseDto;
import com.pragma.powerup.usermicroservice.adapters.driving.http.handlers.IEmployeeHandler;
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
import static org.mockito.Mockito.times;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class EmployeeRestControllerTest {

    @Mock
    private IEmployeeHandler employeeHandler;
    @InjectMocks
    private EmployeeRestController employeeRestController;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(employeeRestController).build();
    }

    @Test
    void saveEmployee() throws Exception {
        EmployeeRequestDto employeeRequestDto = new EmployeeRequestDto("testName", "testLastName", 1234, "+439094230412", "2002/05/01", "test@gmail.com", "string");
        doNothing().when(employeeHandler).saveEmployee((Mockito.any(EmployeeRequestDto.class)));

        mockMvc.perform(post("/employee")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(employeeRequestDto)))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.message").value(Constants.USER_CREATED_MESSAGE));
    }

    @Test
    void getEmployee() throws Exception {
        EmployeeResponseDto employeeResponseDto = new EmployeeResponseDto(1L, "testName", "testLastName", 1234, "+439094230412", "2002/05/01", "test@gmail.com");
        Integer dniNumber = 1;
        when(employeeHandler.findByDni(dniNumber)).thenReturn(employeeResponseDto);

        mockMvc.perform(get("/employee/getEmployee/" + dniNumber))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(employeeResponseDto.getId()))
                .andExpect(jsonPath("$.name").value(employeeResponseDto.getName()))
                .andExpect(jsonPath("$.lastName").value(employeeResponseDto.getLastName()))
                .andExpect(jsonPath("$.dniNumber").value(employeeResponseDto.getDniNumber()))
                .andExpect(jsonPath("$.phone").value(employeeResponseDto.getPhone()))
                .andExpect(jsonPath("$.birthDate").value(employeeResponseDto.getBirthDate()))
                .andExpect(jsonPath("$.mail").value(employeeResponseDto.getMail()));

        verify(employeeHandler, times(1)).findByDni(dniNumber);
    }
}
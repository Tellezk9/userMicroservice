package com.pragma.powerup.usermicroservice.adapters.driving.http.controller;

import com.pragma.powerup.usermicroservice.adapters.driving.http.dto.request.ClientRequestDto;
import com.pragma.powerup.usermicroservice.adapters.driving.http.dto.response.ClientResponseDto;
import com.pragma.powerup.usermicroservice.adapters.driving.http.dto.response.UserResponseDto;
import com.pragma.powerup.usermicroservice.adapters.driving.http.handlers.IClientHandler;
import com.pragma.powerup.usermicroservice.configuration.Constants;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.Map;

@RestController
@RequestMapping("/client")
@RequiredArgsConstructor
@SecurityRequirement(name = "jwt")

public class ClientControllerRest {
    private final IClientHandler clientHandler;

    @Operation(summary = "Add a new client",
            responses = {
                    @ApiResponse(responseCode = "201", description = "client created",
                            content = @Content(mediaType = "application/json", schema = @Schema(ref = "#/components/schemas/Map"))),
                    @ApiResponse(responseCode = "409", description = "client already exists",
                            content = @Content(mediaType = "application/json", schema = @Schema(ref = "#/components/schemas/Error")))})
    @PostMapping("/saveClient")
    public ResponseEntity<Map<String, String>> saveClient(@Valid @RequestBody ClientRequestDto clientRequestDto) {
        clientHandler.saveClient(clientRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(Collections.singletonMap(Constants.RESPONSE_MESSAGE_KEY, Constants.USER_CREATED_MESSAGE));
    }

    @Operation(summary = "Get a client by id",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Provider owner returned",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserResponseDto.class))),
                    @ApiResponse(responseCode = "404", description = "User not found with owner role",
                            content = @Content(mediaType = "application/json", schema = @Schema(ref = "#/components/schemas/Error")))})
    @GetMapping("/getClient/{id}")
    public ResponseEntity<ClientResponseDto> getProviderByDni(@PathVariable Integer id) {
        return ResponseEntity.ok(clientHandler.getClientById(id));
    }
}

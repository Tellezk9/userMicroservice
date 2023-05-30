package com.pragma.powerup.usermicroservice.adapters.driving.http.controller;

import com.pragma.powerup.usermicroservice.adapters.driving.http.dto.request.OwnerRequestDto;
import com.pragma.powerup.usermicroservice.adapters.driving.http.dto.response.OwnerResponseDto;
import com.pragma.powerup.usermicroservice.adapters.driving.http.dto.response.UserResponseDto;
import com.pragma.powerup.usermicroservice.adapters.driving.http.handlers.IOwnerHandler;
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
@RequestMapping("/owner")
@RequiredArgsConstructor
@SecurityRequirement(name = "jwt")
public class OwnerRestController {

    private final IOwnerHandler ownerHandler;

    @Operation(summary = "Add a new owner",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Owner created",
                            content = @Content(mediaType = "application/json", schema = @Schema(ref = "#/components/schemas/Map"))),
                    @ApiResponse(responseCode = "409", description = "Owner already exists",
                            content = @Content(mediaType = "application/json", schema = @Schema(ref = "#/components/schemas/Error"))),
                    @ApiResponse(responseCode = "403", description = "Role not allowed for owner creation",
                            content = @Content(mediaType = "application/json", schema = @Schema(ref = "#/components/schemas/Error")))})
    @PostMapping()
    public ResponseEntity<Map<String, String>> saveOwner(@Valid @RequestBody OwnerRequestDto ownerRequestDto) {
        ownerHandler.saveOwner(ownerRequestDto);
        return ResponseEntity.status(HttpStatus.ACCEPTED)
                .body(Collections.singletonMap(Constants.RESPONSE_MESSAGE_KEY, Constants.USER_CREATED_MESSAGE));
    }

    @Operation(summary = "Get a owner by dni",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Provider owner returned",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserResponseDto.class))),
                    @ApiResponse(responseCode = "404", description = "User not found with owner role",
                            content = @Content(mediaType = "application/json", schema = @Schema(ref = "#/components/schemas/Error")))})
    @GetMapping("/getOwner/{id}")
    public ResponseEntity<OwnerResponseDto> getProviderByDni(@PathVariable Integer id) {
        return ResponseEntity.ok(ownerHandler.getOwnerById(id));
    }
}

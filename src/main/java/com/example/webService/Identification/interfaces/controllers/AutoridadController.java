package com.example.webService.Identification.interfaces.controllers;


import com.example.webService.Identification.domain.services.AutoridadService;
import com.example.webService.Identification.presentation.dtos.AutoridadCreateDto;
import com.example.webService.Identification.presentation.dtos.AutoridadResponseDto;
import com.example.webService.Identification.presentation.dtos.LoginRequestDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/autoridad")
public class AutoridadController {

    private final AutoridadService autoridadService;

    public AutoridadController(AutoridadService autoridadService) {
        this.autoridadService = autoridadService;
    }

    @PostMapping
    public ResponseEntity<AutoridadResponseDto> createAutoridad(@RequestBody AutoridadCreateDto createDto) {
        return new ResponseEntity<>(autoridadService.createAutoridad(createDto), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<AutoridadResponseDto>> getAllAutoridades() {
        return ResponseEntity.ok(autoridadService.getAllAutoridades());
    }


    @PostMapping("/login")
    public ResponseEntity<AutoridadResponseDto> login(@RequestBody LoginRequestDto loginRequest) {
        return ResponseEntity.ok(autoridadService.loginByIdDigital(loginRequest.getIdDigital()));
    }
}

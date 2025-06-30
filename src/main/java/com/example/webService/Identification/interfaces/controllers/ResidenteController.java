package com.example.webService.Identification.interfaces.controllers;

import com.example.webService.Identification.domain.services.ResidenteService;
import com.example.webService.Identification.presentation.dtos.LoginRequestDto;
import com.example.webService.Identification.presentation.dtos.ResidenteCreateDto;
import com.example.webService.Identification.presentation.dtos.ResidenteResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/residente")
public class ResidenteController {

    private final ResidenteService residenteService;

    @Autowired
    public ResidenteController(ResidenteService residenteService) {
        this.residenteService = residenteService;
    }

    @PostMapping
    public ResponseEntity<ResidenteResponseDto> createIdentification(@RequestBody ResidenteCreateDto createDto) {
        return new ResponseEntity<>(residenteService.createIdentification(createDto), HttpStatus.CREATED);
    }

    @PostMapping("/save-images")
    public ResponseEntity<ResidenteResponseDto> saveImages(@RequestBody ResidenteCreateDto createDto) {
        return new ResponseEntity<>(residenteService.saveImages(createDto), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<ResidenteResponseDto>> getAllIdentifications() {
        return ResponseEntity.ok(residenteService.getAllIdentifications());
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<ResidenteResponseDto> getIdentificationById(@PathVariable("id") int id) {
        return ResponseEntity.ok(residenteService.getIdentificationById(id));
    }

    /*
    * @GetMapping("/id/{id}")
    public ResponseEntity<List<ResidenteResponseDto>> getIdentificationById(@PathVariable("id") int id) {
        return ResponseEntity.ok(residenteService.getIdentificationById(id));
    }*/


    @PostMapping("/login")
    public ResponseEntity<ResidenteResponseDto> login(@RequestBody LoginRequestDto loginRequest) {
        return ResponseEntity.ok(residenteService.loginByIdDigital(loginRequest.getIdDigital()));
    }

    @GetMapping("/is-admin-or-no-admin")
    public ResponseEntity<List<ResidenteResponseDto>> getAllIdentificationsIsAdminOrNoAdmin() {
        return ResponseEntity.ok(residenteService.getAllIdentificationsIsAdminOrNoAdmin());
    }
}

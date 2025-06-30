package com.example.webService.SocialServices.interfaces.controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.webService.SocialServices.domain.models.ServicioBasicoType;
import com.example.webService.SocialServices.domain.services.ServicioBasicoService;
import com.example.webService.SocialServices.presentation.dtos.ServicioBasicoCreateDto;
import com.example.webService.SocialServices.presentation.dtos.ServicioBasicoResponseDto;
import com.example.webService.SocialServices.presentation.dtos.ServicioBasicoUpdateDto;

@RestController
@RequestMapping("/api/servicio")
@CrossOrigin(origins = "*") // Permite acceso desde cualquier origen
public class ServicioBasicoController {
    
    private final ServicioBasicoService servicioBasicoService;

    public ServicioBasicoController(ServicioBasicoService servicioBasicoService) {
        this.servicioBasicoService = servicioBasicoService;
    }

    @PostMapping
    public ResponseEntity<ServicioBasicoResponseDto> createSocialService(@RequestBody ServicioBasicoCreateDto createDto) {
        return new ResponseEntity<>(servicioBasicoService.createSocialService(createDto), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<ServicioBasicoResponseDto>> getAllSocialServices() {
        return ResponseEntity.ok(servicioBasicoService.getAllSocialServices());
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<List<ServicioBasicoResponseDto>> getSocialServicesById(@PathVariable("id") int id) {
        return ResponseEntity.ok(servicioBasicoService.getSocialServicesById(id));
    }
    @GetMapping("/type/{type}")
    public ResponseEntity<List<ServicioBasicoResponseDto>> getSocialServicesByType(@PathVariable("type") String type) {
        ServicioBasicoType serviceType = ServicioBasicoType.valueOf(type.toUpperCase());
        return ResponseEntity.ok(servicioBasicoService.getSocialServicesByType(serviceType));
    }

    @GetMapping("/type/{type}/not-expired")
    public ResponseEntity<List<ServicioBasicoResponseDto>> getSocialServicesByTypeAndNotExpired(@PathVariable("type") String type) {
        ServicioBasicoType serviceType = ServicioBasicoType.valueOf(type.toUpperCase());
        return ResponseEntity.ok(servicioBasicoService.getSocialServicesByTypeAndNotExpired(serviceType));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSocialService(@PathVariable("id") int id) {
        servicioBasicoService.deleteSocialService(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<ServicioBasicoResponseDto> updateSocialService(
            @PathVariable("id") int id, 
            @RequestBody ServicioBasicoUpdateDto updateDto) {
        ServicioBasicoResponseDto updatedService = servicioBasicoService.updateSocialService(id, updateDto);
        return ResponseEntity.ok(updatedService);
    }
}

package com.example.webService.SocialServices.interfaces.controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.webService.SocialServices.domain.models.SocialServicesType;
import com.example.webService.SocialServices.domain.services.SocialServicesService;
import com.example.webService.SocialServices.presentation.dtos.SocialServicesCreateDto;
import com.example.webService.SocialServices.presentation.dtos.SocialServicesResponseDto;

@RestController
@RequestMapping("/api/social-services")
public class SocialServicesController {
    
    private final SocialServicesService socialServicesService;

    public SocialServicesController(SocialServicesService socialServicesService) {
        this.socialServicesService = socialServicesService;
    }

    @PostMapping
    public ResponseEntity<SocialServicesResponseDto> createSocialService(@RequestBody SocialServicesCreateDto createDto) {
        return new ResponseEntity<>(socialServicesService.createSocialService(createDto), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<SocialServicesResponseDto>> getAllSocialServices() {
        return ResponseEntity.ok(socialServicesService.getAllSocialServices());
    }

    @GetMapping("/type/{type}")
    public ResponseEntity<List<SocialServicesResponseDto>> getSocialServicesByType(@PathVariable("type") String type) {
        SocialServicesType serviceType = SocialServicesType.valueOf(type.toUpperCase());
        return ResponseEntity.ok(socialServicesService.getSocialServicesByType(serviceType));
    }

    @GetMapping("/type/{type}/not-expired")
    public ResponseEntity<List<SocialServicesResponseDto>> getSocialServicesByTypeAndNotExpired(@PathVariable("type") String type) {
        SocialServicesType serviceType = SocialServicesType.valueOf(type.toUpperCase());
        return ResponseEntity.ok(socialServicesService.getSocialServicesByTypeAndNotExpired(serviceType));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSocialService(@PathVariable("id") int id) {
        socialServicesService.deleteSocialService(id);
        return ResponseEntity.noContent().build();
    }
}

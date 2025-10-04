package com.example.webService.SocialServices.domain.services;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.time.ZoneId;
import java.time.ZonedDateTime;


import org.springframework.stereotype.Service;

import com.example.webService.SocialServices.domain.mappers.ServicioBasicoMapper;
import com.example.webService.SocialServices.domain.models.ServicioBasico;
import com.example.webService.SocialServices.domain.models.ServicioBasicoType;
import com.example.webService.SocialServices.infrastructure.repository.ServicioBasicoRepository;
import com.example.webService.SocialServices.presentation.dtos.ServicioBasicoCreateDto;
import com.example.webService.SocialServices.presentation.dtos.ServicioBasicoResponseDto;
import com.example.webService.SocialServices.presentation.dtos.ServicioBasicoUpdateDto;

import jakarta.transaction.Transactional;

@Service
public class ServicioBasicoService {
    private final ServicioBasicoRepository servicioBasicoRepository;
    private final ServicioBasicoMapper servicioBasicoMapper;

    public ServicioBasicoService(ServicioBasicoRepository servicioBasicoRepository, ServicioBasicoMapper servicioBasicoMapper) {
        this.servicioBasicoRepository = servicioBasicoRepository;
        this.servicioBasicoMapper = servicioBasicoMapper;
    }

    public ServicioBasicoResponseDto createSocialService(ServicioBasicoCreateDto createDto) {
        ServicioBasico entity = servicioBasicoMapper.toEntity(createDto);
        ServicioBasico savedEntity = servicioBasicoRepository.save(entity);
        return servicioBasicoMapper.toResponseDto(savedEntity);
    }

    public List<ServicioBasicoResponseDto> getAllSocialServices() {
        return servicioBasicoRepository.findAll().stream()
                .map(servicioBasicoMapper::toResponseDto)
                .collect(Collectors.toList());
    }

    public List<ServicioBasicoResponseDto> getSocialServicesByType(ServicioBasicoType type) {
        return servicioBasicoRepository.findByServicioBasicoType(type).stream()
                .map(servicioBasicoMapper::toResponseDto)
                .collect(Collectors.toList());
            }


    public List<ServicioBasicoResponseDto> getSocialServicesById(int id) {
        return servicioBasicoRepository.findById(id).stream()
                .map(servicioBasicoMapper::toResponseDto)
                .collect(Collectors.toList());
    }

    public List<ServicioBasicoResponseDto> getSocialServicesByTypeAndNotExpired(ServicioBasicoType type) {
        // Obtener la fecha actual de Perú
        LocalDate currentDateInPeru = ZonedDateTime.now(ZoneId.of("America/Lima")).toLocalDate();

        // Pasar la fecha de Perú al repositorio
        return servicioBasicoRepository.findByTypeAndNotExpired(type, currentDateInPeru).stream()
                .map(servicioBasicoMapper::toResponseDto)
                .collect(Collectors.toList());
    }

    @Transactional
    public void deleteSocialService(int id) {
        Optional<ServicioBasico> socialService = servicioBasicoRepository.findById(id);
        if (socialService.isPresent()) {
            servicioBasicoRepository.delete(socialService.get());
        } else {
            throw new RuntimeException("Social service not found with id: " + id);
        }
    }

    @Transactional
    public ServicioBasicoResponseDto updateSocialService(int id, ServicioBasicoUpdateDto updateDto) {
        ServicioBasico existingService = servicioBasicoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Social service not found with id: " + id));
        
        existingService.setResumen(updateDto.resumen);
        existingService.setImagen(updateDto.imagen);
        existingService.setLugar(updateDto.lugar);
        existingService.setFecha(updateDto.fecha);
        existingService.setHora(updateDto.hora);
        existingService.setDescripcion(updateDto.descripcion);

        ServicioBasico updatedService = servicioBasicoRepository.save(existingService);

        return servicioBasicoMapper.toResponseDto(updatedService);
    }
}
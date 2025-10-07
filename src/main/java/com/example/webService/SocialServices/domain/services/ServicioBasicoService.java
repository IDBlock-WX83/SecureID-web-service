package com.example.webService.SocialServices.domain.services;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.time.ZoneId;
import java.time.ZonedDateTime;


import lombok.extern.slf4j.Slf4j;
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
@Slf4j
public class ServicioBasicoService {
    private final ServicioBasicoRepository servicioBasicoRepository;
    private final ServicioBasicoMapper servicioBasicoMapper;

    public ServicioBasicoService(ServicioBasicoRepository servicioBasicoRepository, ServicioBasicoMapper servicioBasicoMapper) {
        this.servicioBasicoRepository = servicioBasicoRepository;
        this.servicioBasicoMapper = servicioBasicoMapper;
    }

    public ServicioBasicoResponseDto createSocialService(ServicioBasicoCreateDto createDto) {
        long startTime = System.currentTimeMillis();  // Tiempo de inicio
        log.info("Start creating social service");

        ServicioBasico entity = servicioBasicoMapper.toEntity(createDto);
        ServicioBasico savedEntity = servicioBasicoRepository.save(entity);

        long endTime = System.currentTimeMillis();  // Tiempo de fin
        log.info("End creating social service. Latency: {} ms", endTime - startTime);  // Log de latencia
        return servicioBasicoMapper.toResponseDto(savedEntity);
    }

    public List<ServicioBasicoResponseDto> getAllSocialServices() {
        long startTime = System.currentTimeMillis();  // Tiempo de inicio
        log.info("Start retrieving all social services");
        List<ServicioBasicoResponseDto> response = servicioBasicoRepository.findAll().stream()
                .map(servicioBasicoMapper::toResponseDto)
                .collect(Collectors.toList());

        long endTime = System.currentTimeMillis();  // Tiempo de fin
        log.info("End retrieving all social services. Latency: {} ms", endTime - startTime);  // Log de latencia

        return response;
    }

    public List<ServicioBasicoResponseDto> getSocialServicesByType(ServicioBasicoType type) {
        long startTime = System.currentTimeMillis();  // Tiempo de inicio
        log.info("Start retrieving social services by type: {}", type);

        List<ServicioBasicoResponseDto> response = servicioBasicoRepository.findByServicioBasicoType(type).stream()
                .map(servicioBasicoMapper::toResponseDto)
                .collect(Collectors.toList());

        long endTime = System.currentTimeMillis();  // Tiempo de fin
        log.info("End retrieving social services by type: {}. Latency: {} ms", type, endTime - startTime);  // Log de latencia

        return response;
    }


    public List<ServicioBasicoResponseDto> getSocialServicesById(int id) {
        long startTime = System.currentTimeMillis();  // Tiempo de inicio
        log.info("Start retrieving social services by ID: {}", id);

        List<ServicioBasicoResponseDto> response = servicioBasicoRepository.findById(id).stream()
                .map(servicioBasicoMapper::toResponseDto)
                .collect(Collectors.toList());

        long endTime = System.currentTimeMillis();  // Tiempo de fin
        log.info("End retrieving social services by ID: {}. Latency: {} ms", id, endTime - startTime);  // Log de latencia

        return response;
    }

    public List<ServicioBasicoResponseDto> getSocialServicesByTypeAndNotExpired(ServicioBasicoType type) {
        long startTime = System.currentTimeMillis();  // Tiempo de inicio
        log.info("Start retrieving social services by type: {} and not expired", type);

        // Obtener la fecha actual de Perú
        LocalDate currentDateInPeru = ZonedDateTime.now(ZoneId.of("America/Lima")).toLocalDate();

        List<ServicioBasicoResponseDto> response = servicioBasicoRepository.findByTypeAndNotExpired(type, currentDateInPeru).stream()
                .map(servicioBasicoMapper::toResponseDto)
                .collect(Collectors.toList());

        long endTime = System.currentTimeMillis();  // Tiempo de fin
        log.info("End retrieving social services by type: {} and not expired. Latency: {} ms", type, endTime - startTime);  // Log de latencia

        return response;
    }

    @Transactional
    public void deleteSocialService(int id) {
        long startTime = System.currentTimeMillis();  // Tiempo de inicio
        log.info("Start deleting social service with ID: {}", id);

        Optional<ServicioBasico> socialService = servicioBasicoRepository.findById(id);
        if (socialService.isPresent()) {
            servicioBasicoRepository.delete(socialService.get());
        } else {
            throw new RuntimeException("Social service not found with id: " + id);
        }

        long endTime = System.currentTimeMillis();  // Tiempo de fin
        log.info("End deleting social service with ID: {}. Latency: {} ms", id, endTime - startTime);  // Log de latencia
    }

    @Transactional
    public ServicioBasicoResponseDto updateSocialService(int id, ServicioBasicoUpdateDto updateDto) {
        long startTime = System.currentTimeMillis();  // Tiempo de inicio
        log.info("Start updating social service with ID: {}", id);

        ServicioBasico existingService = servicioBasicoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Social service not found with id: " + id));

        existingService.setResumen(updateDto.resumen);
        existingService.setImagen(updateDto.imagen);
        existingService.setLugar(updateDto.lugar);
        existingService.setFecha(updateDto.fecha);
        existingService.setHora(updateDto.hora);
        existingService.setDescripcion(updateDto.descripcion);

        ServicioBasico updatedService = servicioBasicoRepository.save(existingService);

        long endTime = System.currentTimeMillis();  // Tiempo de fin
        log.info("End updating social service with ID: {}. Latency: {} ms", id, endTime - startTime);  // Log de latencia

        return servicioBasicoMapper.toResponseDto(updatedService);
    }
}
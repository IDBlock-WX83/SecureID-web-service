package com.example.webService.SocialServices.domain.services;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


import lombok.RequiredArgsConstructor;
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
@RequiredArgsConstructor
public class ServicioBasicoService {
    private final ServicioBasicoRepository servicioBasicoRepository;
    private final ServicioBasicoMapper servicioBasicoMapper;
    private final NotificationService notificationService;

    public ServicioBasicoResponseDto createSocialService(ServicioBasicoCreateDto createDto) {
        // 1. Save the service in the database
        // Note: You should validate the DTO before mapping it to the entity
        ServicioBasico entity = servicioBasicoMapper.toEntity(createDto);
        ServicioBasico savedEntity = servicioBasicoRepository.save(entity);

        LocalDateTime fechaHoraServicio = savedEntity.getFecha().atTime(savedEntity.getHora());

        // Notificación inmediata
        notificationService.sendToTopic(
                "services-basics",
                "Nuevo servicio",
                "Se publicó: " + savedEntity.getResumen()
        );

        // 15 minutos antes
        if (fechaHoraServicio.minusMinutes(15).isAfter(LocalDateTime.now())) {
            notificationService.scheduleNotification(
                    "services-basics",
                    "Recordatorio",
                    "El servicio " + savedEntity.getResumen() + " empieza en 15 minutos",
                    fechaHoraServicio.minusMinutes(15),
                    "15min_" + savedEntity.getId()
            );
        }

        // A la hora exacta
        if (fechaHoraServicio.isAfter(LocalDateTime.now())) {
            notificationService.scheduleNotification(
                    "services-basics",
                    "¡Ya empezó!",
                    "El servicio " + savedEntity.getResumen() + " acaba de iniciar",
                    fechaHoraServicio,
                    "hora_" + savedEntity.getId()
            );
        }
        // 3. Return the saved entity as a DTO
        // Note: You might want to return a more detailed DTO with additional information
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
        return servicioBasicoRepository.findByTypeAndNotExpired(type, LocalDate.now()).stream()
                .map(servicioBasicoMapper::toResponseDto)
                .collect(Collectors.toList());
    }

    @Transactional
    public void deleteSocialService(int id) {
        Optional<ServicioBasico> socialService = servicioBasicoRepository.findById(id);
        if (socialService.isPresent()) {
            // 1️⃣ Cancelar notificaciones programadas
            notificationService.cancelNotification("15min_" + id);
            notificationService.cancelNotification("hora_" + id);

            // 2️⃣ Eliminar el servicio de la base de datos
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
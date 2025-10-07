package com.example.webService.Identification.domain.services;

import com.example.webService.Identification.domain.mappers.AutoridadMapper;
import com.example.webService.Identification.domain.model.Autoridad;
import com.example.webService.Identification.infrastructure.repository.AutoridadRepository;
import com.example.webService.Identification.presentation.dtos.AutoridadCreateDto;
import com.example.webService.Identification.presentation.dtos.AutoridadResponseDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class AutoridadService {

    private final AutoridadRepository autoridadRepository;
    private final AutoridadMapper autoridadMapper;

    public AutoridadService(AutoridadRepository autoridadRepository,
                            AutoridadMapper autoridadMapper) {
        this.autoridadRepository = autoridadRepository;
        this.autoridadMapper=autoridadMapper;

    }

    public AutoridadResponseDto createAutoridad(AutoridadCreateDto createDto) {
        long startTime = System.currentTimeMillis();  // Tiempo de inicio
        log.info("Start creating authority");

        Autoridad entity = autoridadMapper.toEntity(createDto);
        Autoridad savedEntity = autoridadRepository.save(entity);

        long endTime = System.currentTimeMillis();  // Tiempo de fin
        log.info("End creating authority. Latency: {} ms", endTime - startTime);  // Log de latencia

        return autoridadMapper.toResponseDto(savedEntity);
    }

    public List<AutoridadResponseDto> getAllAutoridades() {
        long startTime = System.currentTimeMillis();  // Tiempo de inicio
        log.info("Start fetching all authorities");

        List<AutoridadResponseDto> autoridades = autoridadRepository.findAll().stream()
                .map(autoridadMapper::toResponseDto)
                .collect(Collectors.toList());

        long endTime = System.currentTimeMillis();  // Tiempo de fin
        log.info("End fetching all authorities. Latency: {} ms", endTime - startTime);  // Log de latencia

        return autoridades;
    }

    public AutoridadResponseDto loginByIdDigital(String idDigital) {
        long startTime = System.currentTimeMillis();  // Tiempo de inicio
        log.info("Start login by digital ID: {}", idDigital);

        Autoridad autoridad = autoridadRepository.findByIdDigital(idDigital)
                .orElseThrow(() -> new RuntimeException("ID Digital no válido"));

        long endTime = System.currentTimeMillis();  // Tiempo de fin
        log.info("End login by digital ID. Latency: {} ms", endTime - startTime);  // Log de latencia

        return autoridadMapper.toResponseDto(autoridad);
    }
}

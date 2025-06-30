package com.example.webService.Identification.domain.services;

import com.example.webService.Identification.domain.mappers.AutoridadMapper;
import com.example.webService.Identification.domain.model.Autoridad;
import com.example.webService.Identification.infrastructure.repository.AutoridadRepository;
import com.example.webService.Identification.presentation.dtos.AutoridadCreateDto;
import com.example.webService.Identification.presentation.dtos.AutoridadResponseDto;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AutoridadService {

    private final AutoridadRepository autoridadRepository;
    private final AutoridadMapper autoridadMapper;


    public AutoridadService(AutoridadRepository autoridadRepository,
                            AutoridadMapper autoridadMapper) {
        this.autoridadRepository = autoridadRepository;
        this.autoridadMapper=autoridadMapper;

    }




    public AutoridadResponseDto createAutoridad(AutoridadCreateDto createDto) {
        Autoridad entity = autoridadMapper.toEntity(createDto);
        Autoridad savedEntity = autoridadRepository.save(entity);
        return autoridadMapper.toResponseDto(savedEntity);
    }

    public List<AutoridadResponseDto> getAllAutoridades() {
        return autoridadRepository.findAll().stream()
                .map(autoridadMapper::toResponseDto)
                .collect(Collectors.toList());
    }


    public AutoridadResponseDto loginByIdDigital(String idDigital) {
        Autoridad autoridad = autoridadRepository.findByIdDigital(idDigital)
                .orElseThrow(() -> new RuntimeException("ID Digital no válido"));

        return autoridadMapper.toResponseDto(autoridad);
    }
}

package com.example.webService.SocialServices.domain.services;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


import org.springframework.stereotype.Service;

import com.example.webService.SocialServices.domain.mappers.SocialServicesMapper;
import com.example.webService.SocialServices.domain.models.SocialServices;
import com.example.webService.SocialServices.domain.models.SocialServicesType;
import com.example.webService.SocialServices.infrastructure.repository.SocialServicesRepository;
import com.example.webService.SocialServices.presentation.dtos.SocialServicesCreateDto;
import com.example.webService.SocialServices.presentation.dtos.SocialServicesResponseDto;
import com.example.webService.SocialServices.presentation.dtos.SocialServicesUpdateDto;

import jakarta.transaction.Transactional;

@Service
public class SocialServicesService {
    private final SocialServicesRepository socialServicesRepository;
    private final SocialServicesMapper socialServicesMapper;

    public SocialServicesService(SocialServicesRepository socialServicesRepository, SocialServicesMapper socialServicesMapper) {
        this.socialServicesRepository = socialServicesRepository;
        this.socialServicesMapper = socialServicesMapper;
    }

    public SocialServicesResponseDto createSocialService(SocialServicesCreateDto createDto) {
        SocialServices entity = socialServicesMapper.toEntity(createDto);
        SocialServices savedEntity = socialServicesRepository.save(entity);
        return socialServicesMapper.toResponseDto(savedEntity);
    }

    public List<SocialServicesResponseDto> getAllSocialServices() {
        return socialServicesRepository.findAll().stream()
                .map(socialServicesMapper::toResponseDto)
                .collect(Collectors.toList());
    }

    public List<SocialServicesResponseDto> getSocialServicesByType(SocialServicesType type) {
        return socialServicesRepository.findBySocialServicesType(type).stream()
                .map(socialServicesMapper::toResponseDto)
                .collect(Collectors.toList());
            }

    public List<SocialServicesResponseDto> getSocialServicesByTypeAndNotExpired(SocialServicesType type) {
        return socialServicesRepository.findByTypeAndNotExpired(type, LocalDate.now()).stream()
                .map(socialServicesMapper::toResponseDto)
                .collect(Collectors.toList());
    }

    @Transactional
    public void deleteSocialService(int id) {
        Optional<SocialServices> socialService = socialServicesRepository.findById(id);
        if (socialService.isPresent()) {
            socialServicesRepository.delete(socialService.get());
        } else {
            throw new RuntimeException("Social service not found with id: " + id);
        }
    }

    @Transactional
    public SocialServicesResponseDto updateSocialService(int id, SocialServicesUpdateDto updateDto) {
        SocialServices existingService = socialServicesRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Social service not found with id: " + id));
        
        existingService.setTitle(updateDto.title);
        existingService.setImageUrl(updateDto.imageUrl);
        existingService.setLocation(updateDto.location);
        existingService.setSchedule(updateDto.schedule);
        existingService.setExpirationDate(updateDto.expirationDate);

        SocialServices updatedService = socialServicesRepository.save(existingService);

        return socialServicesMapper.toResponseDto(updatedService);
    }
}
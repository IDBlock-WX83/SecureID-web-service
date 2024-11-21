package com.example.webService.SocialServices.domain.mappers;

import org.springframework.stereotype.Component;

import com.example.webService.SocialServices.domain.models.SocialServices;
import com.example.webService.SocialServices.domain.models.SocialServicesType;
import com.example.webService.SocialServices.presentation.dtos.SocialServicesCreateDto;
import com.example.webService.SocialServices.presentation.dtos.SocialServicesResponseDto;

@Component
public class SocialServicesMapper {

    public SocialServicesResponseDto toResponseDto(SocialServices socialService) {
        SocialServicesResponseDto dto = new SocialServicesResponseDto();
        dto.id = socialService.getId();
        dto.title = socialService.getTitle();
        dto.imageUrl = socialService.getImageUrl();
        dto.location = socialService.getLocation();
        dto.schedule = socialService.getSchedule();
        dto.expirationDate = socialService.getExpirationDate();
        dto.socialServicesType = socialService.getSocialServicesType();
        return dto;
    }

    public SocialServices toEntity(SocialServicesCreateDto createDto) {
        SocialServices socialService = new SocialServices();
        socialService.setTitle(createDto.title);
        socialService.setImageUrl(createDto.imageUrl);
        socialService.setLocation(createDto.location);
        socialService.setSchedule(createDto.schedule);
        socialService.setExpirationDate(createDto.expirationDate);

        try {
            SocialServicesType type = SocialServicesType.valueOf(createDto.socialServicesType.toUpperCase());
            socialService.setSocialServicesType(type);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid socialServicesType: " + createDto.socialServicesType);
        }

        return socialService;
    }
}

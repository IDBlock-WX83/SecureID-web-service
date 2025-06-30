package com.example.webService.SocialServices.domain.mappers;

import org.springframework.stereotype.Component;

import com.example.webService.SocialServices.domain.models.ServicioBasico;
import com.example.webService.SocialServices.domain.models.ServicioBasicoType;
import com.example.webService.SocialServices.presentation.dtos.ServicioBasicoCreateDto;
import com.example.webService.SocialServices.presentation.dtos.ServicioBasicoResponseDto;

@Component
public class ServicioBasicoMapper {

    public ServicioBasicoResponseDto toResponseDto(ServicioBasico socialService) {
        ServicioBasicoResponseDto dto = new ServicioBasicoResponseDto();
        dto.id = socialService.getId();
        dto.resumen = socialService.getResumen();
        dto.imagen = socialService.getImagen();
        dto.lugar = socialService.getLugar();
        dto.fecha = socialService.getFecha();
        dto.hora = socialService.getHora();
        dto.distrito = socialService.getDistrito();
        dto.autoridad = socialService.getAutoridadLocal();

        dto.descripcion = socialService.getDescripcion();
        dto.servicioBasicoType = socialService.getServicioBasicoType();
        return dto;
    }

    public ServicioBasico toEntity(ServicioBasicoCreateDto createDto) {
        ServicioBasico socialService = new ServicioBasico();
        socialService.setResumen(createDto.resumen);
        socialService.setImagen(createDto.imagen);
        socialService.setLugar(createDto.lugar);
        socialService.setFecha(createDto.fecha);
        socialService.setHora(createDto.hora);
        socialService.setDistrito(createDto.distrito);
        socialService.setAutoridadLocal(createDto.autoridad);

        socialService.setDescripcion(createDto.descripcion);
        socialService.setServicioBasicoType(ServicioBasicoType.valueOf(createDto.socialServicesType));
        /*try {
            SocialServicesType type = SocialServicesType.valueOf(createDto.socialServicesType.toUpperCase());
            socialService.setSocialServicesType(type);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid socialServicesType: " + createDto.socialServicesType);
        }*/

        return socialService;
    }
}

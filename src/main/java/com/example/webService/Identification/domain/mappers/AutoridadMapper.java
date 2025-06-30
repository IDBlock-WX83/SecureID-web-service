package com.example.webService.Identification.domain.mappers;


import com.example.webService.Identification.domain.model.Autoridad;
import com.example.webService.Identification.presentation.dtos.AutoridadCreateDto;
import com.example.webService.Identification.presentation.dtos.AutoridadResponseDto;
import org.springframework.stereotype.Component;

@Component
public class AutoridadMapper {

    public AutoridadResponseDto toResponseDto(Autoridad autoridad) {
        AutoridadResponseDto dto = new AutoridadResponseDto();
        dto.id = autoridad.getId();
        dto.preNombres = autoridad.getPreNombres();
        dto.primerApellido = autoridad.getPrimerApellido();
        dto.segundoApellido = autoridad.getSegundoApellido();
        dto.fechaNacimiento = autoridad.getFechaNacimiento();
        dto.sexo = autoridad.getSexos();
        dto.estadoCivil = autoridad.getEstadoCivil();
        dto.fechaInscripcion = autoridad.getFechaInscripcion();
        dto.direccion=autoridad.getDireccion();
        dto.distrito=autoridad.getDistrito();
        dto.telefonoCelular=autoridad.getTelefonoCelular();
        dto.idDigital=autoridad.getIdDigital();
        return dto;
    }

    public Autoridad toEntity(AutoridadCreateDto createDto) {



        Autoridad autoridad = new Autoridad();
        autoridad.setPreNombres(createDto.preNombres);
        autoridad.setPrimerApellido(createDto.primerApellido);
        autoridad.setSegundoApellido(createDto.segundoApellido);
        autoridad.setFechaNacimiento(createDto.fechaNacimiento);
        autoridad.setSexos(createDto.sexo);
        autoridad.setEstadoCivil(createDto.estadoCivil);
        autoridad.setFechaInscripcion(createDto.fechaInscripcion);
        autoridad.setDireccion(createDto.direccion);
        autoridad.setDistrito(createDto.distrito);
        autoridad.setTelefonoCelular(createDto.telefonoCelular);
        autoridad.setIdDigital(createDto.idDigital);


        return autoridad;
    }
}

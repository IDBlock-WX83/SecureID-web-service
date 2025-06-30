package com.example.webService.Identification.domain.mappers;

import com.example.webService.Identification.domain.model.Residente;
import com.example.webService.Identification.presentation.dtos.ResidenteCreateDto;
import com.example.webService.Identification.presentation.dtos.ResidenteResponseDto;
import org.springframework.stereotype.Component;

@Component
public class ResidenteMapper {

    public ResidenteResponseDto toResponseDto(Residente residente) {
        ResidenteResponseDto dto = new ResidenteResponseDto();
        dto.id = residente.getId();
        dto.preNombres = residente.getPreNombres();
        dto.primerApellido = residente.getPrimerApellido();
        dto.segundoApellido = residente.getSegundoApellido();
        dto.fechaNacimiento = residente.getFechaNacimiento();
        dto.sexo = residente.getSexos();
        dto.estadoCivil = residente.getEstadoCivil();
        dto.fechaInscripcion = residente.getFechaInscripcion();
        dto.direccion = residente.getDireccion();
        dto.distrito = residente.getDistrito();
        dto.telefonoCelular = residente.getTelefonoCelular();

        dto.fotoHash = residente.getFotoHash();
        dto.firmaHash = residente.getFirmaHash();

        dto.idDigital = residente.getIdDigital();
        return dto;
    }

    public Residente toEntity(ResidenteCreateDto createDto) {
        Residente residente = new Residente();
        residente.setPreNombres(createDto.preNombres);
        residente.setPrimerApellido(createDto.primerApellido);
        residente.setSegundoApellido(createDto.segundoApellido);
        residente.setFechaNacimiento(createDto.fechaNacimiento);
        residente.setSexos(createDto.sexo);
        residente.setEstadoCivil(createDto.estadoCivil);
        residente.setFechaInscripcion(createDto.fechaInscripcion);
        residente.setDireccion(createDto.direccion);
        residente.setDistrito(createDto.distrito);
        residente.setTelefonoCelular(createDto.telefonoCelular);

        residente.setFotoHash(createDto.fotoHash);
        residente.setFirmaHash(createDto.firmaHash);

        residente.setIdDigital(createDto.idDigital);

        return residente;
    }
}

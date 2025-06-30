package com.example.webService.Identification.presentation.dtos;

import com.example.webService.Identification.domain.model.Distrito;
import com.example.webService.Identification.domain.model.EstadoCivil;
import com.example.webService.Identification.domain.model.Sexo;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
@Getter
@Setter
public class ResidenteResponseDto {

    public int id;
    public String preNombres;
    public String primerApellido;
    public String segundoApellido;
    public LocalDate fechaNacimiento;
    public Sexo sexo;
    public EstadoCivil estadoCivil;
    public LocalDate fechaInscripcion;
    public String direccion;
    public Distrito distrito;
    public String telefonoCelular;

    public byte[] fotoHash;
    public byte[] firmaHash;

    public String idDigital;
    public String txHash;



}

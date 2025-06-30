package com.example.webService.Identification.presentation.dtos;

import com.example.webService.Identification.domain.model.Distrito;
import com.example.webService.Identification.domain.model.EstadoCivil;
import com.example.webService.Identification.domain.model.Sexo;

import java.time.LocalDate;

public class AutoridadResponseDto {
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
    public String idDigital;
}

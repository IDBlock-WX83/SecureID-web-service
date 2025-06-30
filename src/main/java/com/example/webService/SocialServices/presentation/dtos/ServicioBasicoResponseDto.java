package com.example.webService.SocialServices.presentation.dtos;

import java.time.LocalDate;
import java.time.LocalTime;

import com.example.webService.Identification.domain.model.Autoridad;
import com.example.webService.Identification.domain.model.Distrito;
import com.example.webService.SocialServices.domain.models.ServicioBasicoType;

public class ServicioBasicoResponseDto {

    public int id;
    public String resumen;
    public byte[] imagen;  // Aquí almacenamos la imagen como byte[]
    public String lugar;  // Ubicación del servicio
    public LocalDate fecha;  // Fecha de expiración del servicio
    public LocalTime hora;  // Almacena la hora como LocalTime
    public String descripcion;  // Ubicación del servicio
    public ServicioBasicoType servicioBasicoType;
    public Distrito distrito;
    public Autoridad autoridad;
}

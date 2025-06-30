package com.example.webService.SocialServices.presentation.dtos;

import java.time.LocalDate;
import java.time.LocalTime;

public class ServicioBasicoUpdateDto {

    public String resumen;
    public byte[] imagen;  // Aquí almacenamos la imagen como byte[]
    public String lugar;  // Ubicación del servicio
    public LocalDate fecha;  // Fecha de expiración del servicio
    public LocalTime hora;  // Almacena la hora como LocalTime
    public String descripcion;  // Ubicación del servicio


}

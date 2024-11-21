package com.example.webService.Blockchain.domain.model;

import jakarta.persistence.*;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@Getter
@Setter
@Entity
@Table(name = "identification")  // Opcional: Especifica el nombre de la tabla
public class Identification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)  // O usa otro tipo de estrategia si es necesario
    private Long id;

    @Column(name = "pre_nombres", nullable = false)
    private String preNombres;

    @Column(name = "apellido_paterno", nullable = false)
    private String apellidoPaterno;

    @Column(name = "apellido_materno", nullable = false)
    private String apellidoMaterno;

    @Column(name = "id_digital", nullable = false)
    private String idDigital;

    @Column(name = "fecha_nacimiento", nullable = false)
    private String fechaNacimiento;

    @Column(name = "direccion", nullable = false)
    private String direccion;

    @Column(name = "telefono", nullable = false)
    private String telefono;

    @Column(name = "region", nullable = false)
    private String region;

    @Column(name = "provincia", nullable = false)
    private String provincia;

    @Column(name = "distrito", nullable = false)
    private String distrito;

    @Column(name = "sexo", nullable = false)
    private String sexo; // M para masculino, F para femenino, u otro valor adecuado

   /* @Column(name = "firma", nullable = true)
    private String firma; // URL o path de la firma cargada


    @Column(name = "dni_frontal", nullable = true)
    private String dniFrontal;

    @Column(name = "dni_posterior", nullable = true)
    private String dniPosterior;*/

    @Column(name = "active", nullable = false)
    private boolean active; // Propiedad para indicar si está activa

    public Identification(String preNombres, String apellidoPaterno, String apellidoMaterno,
                          String idDigital, String fechaNacimiento, String direccion, String telefono,
                          String region, String provincia, String distrito, String sexo, Boolean active) {
        this.preNombres = preNombres;
        this.apellidoPaterno = apellidoPaterno;
        this.apellidoMaterno = apellidoMaterno;
        this.idDigital = idDigital;
        this.fechaNacimiento = fechaNacimiento;
        this.direccion = direccion;
        this.telefono = telefono;
        this.region = region;
        this.provincia = provincia;
        this.distrito = distrito;
        this.sexo = sexo;
       /* this.firma = firma;
        this.dniFrontal = dniFrontal;
        this.dniPosterior = dniPosterior;*/
        this.active = active;
    }




    public void updateDetails(String preNombres, String apellidoPaterno, String apellidoMaterno,
                              String idDigital, String fechaNacimiento, String direccion, String telefono,
                              String region, String provincia, String distrito, String sexo, Boolean active)  {
        this.preNombres = preNombres;
        this.apellidoPaterno = apellidoPaterno;
        this.apellidoMaterno = apellidoMaterno;
        this.idDigital = idDigital;
        this.fechaNacimiento = fechaNacimiento;
        this.direccion = direccion;
        this.telefono = telefono;
        this.region = region;
        this.provincia = provincia;
        this.distrito = distrito;
        this.sexo = sexo;
        this.active = active;
    }

    @Override
    public String toString() {
        return "ID: " + id + ", Pre Nombres: " + preNombres + ", Apellido Paterno: " + apellidoPaterno +
                ", Apellido Materno: " + apellidoMaterno + ", ID Digital: " + idDigital +
                ", Fecha de Nacimiento: " + fechaNacimiento + ", Dirección: " + direccion +
                ", Teléfono: " + telefono + ", Región: " + region + ", Provincia: " + provincia +
                ", Distrito: " + distrito + ", Sexo: " + sexo  +
                ", DNI Frontal: " +
                ", Activo: " + active;
    }
}

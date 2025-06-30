package com.example.webService.Identification.domain.model;


import com.example.webService.SocialServices.domain.models.ServicioBasico;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@Getter
@Setter
@Entity
@Table(name = "autoridad_local")
public class Autoridad {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)  // O usa otro tipo de estrategia si es necesario
    private int id;

    @Column(name = "pre_nombres", nullable = false)
    private String preNombres;

    @Column(name = "primer_apellido", nullable = false)
    private String primerApellido;

    @Column(name = "segundo_apellido", nullable = false)
    private String segundoApellido;

    @Column(name = "fecha_nacimiento", nullable = false)
    private LocalDate fechaNacimiento;

    @Column(name = "fecha_inscripcion", nullable = false)
    private LocalDate fechaInscripcion;

    @Column(name = "direccion", nullable = false)
    private String direccion;


    @Column(name = "telefono_celular", nullable = true,length = 9)
    private String telefonoCelular;

    @Column(name = "id_digital", nullable = false,length = 10)
    private String idDigital;

    @ManyToOne
    @JoinColumn(name = "estado_civil_id", nullable = false)
    private EstadoCivil estadoCivil;


    @ManyToOne
    @JoinColumn(name = "sexo_id", nullable = false)
    private Sexo sexos;


    // Relación con Distrito
    @ManyToOne
    @JoinColumn(name = "distrito_id", nullable = false)  // Relacionamos con la tabla Distrito
    private Distrito distrito;


    // Relación uno a muchos con los servicios creados por la autoridad
    @OneToMany(mappedBy = "autoridadLocal")
    @JsonIgnore
    private List<ServicioBasico> servicios;
}

package com.example.webService.Identification.domain.model;

import com.example.webService.SocialServices.domain.models.ServicioBasico;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;

import jakarta.persistence.*;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@Getter
@Setter
@Entity
@Table(name = "distrito")
public class Distrito {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "distrito", nullable = false)
    private String distrito;

    // Relación muchos a uno con Provincia
    @ManyToOne
    @JoinColumn(name = "provincia_id")
    private Provincia provincia;


    // Relación uno a muchos con Identificación
    @OneToMany(mappedBy = "distrito")
    @JsonIgnore
    private List<Residente> residentes;


    // Relación uno a muchos con Autoridad
    @OneToMany(mappedBy = "distrito")
    @JsonIgnore
    private List<Autoridad> autoridades;



    // Relación uno a muchos con Autoridad
    @OneToMany(mappedBy = "distrito")
    @JsonIgnore
    private List<ServicioBasico> serviciosBasicos;
}

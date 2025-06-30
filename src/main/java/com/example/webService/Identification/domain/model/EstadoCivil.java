package com.example.webService.Identification.domain.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@Getter
@Setter
@Entity
@Table(name = "estado_civil")
//@JsonIgnoreProperties({"identificaciones"})
public class EstadoCivil {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "estado_civil", nullable = false,length = 15)
    private String estadoCivil;

    @OneToMany(mappedBy = "estadoCivil",cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Residente> identificaciones;


    @OneToMany(mappedBy = "estadoCivil",cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Autoridad> autoridades;

}

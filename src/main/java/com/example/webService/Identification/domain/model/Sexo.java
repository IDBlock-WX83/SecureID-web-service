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
@Table(name = "sexo")
public class Sexo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "sexo", nullable = false,length = 10)
    private String sexo;

    @OneToMany(mappedBy = "sexos",cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Residente> identificaciones;

    @OneToMany(mappedBy = "sexos",cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Autoridad> autoridades;
}

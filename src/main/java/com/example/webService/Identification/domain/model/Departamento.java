package com.example.webService.Identification.domain.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
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
@Table(name = "departamento")
@JsonIgnoreProperties({"provincias", "distritos"})
public class Departamento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "departamento", nullable = false)
    private String departamento;

    // Relación uno a muchos con Provincias
    @OneToMany(mappedBy = "departamento", cascade = CascadeType.ALL)
    private List<Provincia> provincias;

}

package com.example.webService.Identification.domain.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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
@Table(name = "provincia")
@JsonIgnoreProperties("distritos")
public class Provincia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "provincia", nullable = false)
    private String provincia;

    // Relación muchos a uno con Departamento
    @ManyToOne
    @JoinColumn(name = "departamento_id")
    private Departamento departamento;

    // Relación uno a muchos con Distritos
    @OneToMany(mappedBy = "provincia", cascade = CascadeType.ALL)
    private List<Distrito> distritos;
}

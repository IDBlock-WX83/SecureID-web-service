package com.example.webService.Identification.domain.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@Getter
@Setter
@Entity
@Table(name = "residente_local")
public class Residente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "pre_nombres", nullable = true)
    private String preNombres;

    @Column(name = "primer_apellido", nullable = true)
    private String primerApellido;

    @Column(name = "segundo_apellido", nullable = true)
    private String segundoApellido;

    @Column(name = "fecha_nacimiento", nullable = true)
    private LocalDate fechaNacimiento;

    @Column(name = "fecha_inscripcion", nullable = true)
    private LocalDate fechaInscripcion;

    @Column(name = "direccion", nullable = true)
    private String direccion;

    @Column(name = "telefono_celular", nullable = true, length = 20)
    private String telefonoCelular;

    /*@Column(name = "foto_hash", nullable = true, length = 512)
    private String fotoHash;

    @Column(name = "firma_hash", nullable = true, length = 512)
    private String firmaHash;*/

    @Lob
    @Column(name = "foto_hash", nullable = true, columnDefinition = "LONGBLOB")
    private byte[] fotoHash;  // Aquí almacenamos la imagen como byte[]

    @Lob
    @Column(name = "firma_hash", nullable = true, columnDefinition = "LONGBLOB")
    private byte[] firmaHash;


    @Column(name = "id_digital", nullable = true, length = 10)
    private String idDigital;

    @ManyToOne
    @JoinColumn(name = "estado_civil_id", nullable = true)
    private EstadoCivil estadoCivil;

    @ManyToOne
    @JoinColumn(name = "sexo_id", nullable = true)
    private Sexo sexos;

    @ManyToOne
    @JoinColumn(name = "distrito_id", nullable = true)
    private Distrito distrito;

}

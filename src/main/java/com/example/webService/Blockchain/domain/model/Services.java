package com.example.webService.Blockchain.domain.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "services")
@Data
public class Services {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column
    private String imageUrl; // URL de la imagen

    @Column
    private String location;

    @Column
    private String availability; // Horario de atención

    @Column
    private LocalDate expirationDate; // Fecha de vencimiento

    private LocalDateTime createdAt = LocalDateTime.now();
    private LocalDateTime updatedAt = LocalDateTime.now();
}

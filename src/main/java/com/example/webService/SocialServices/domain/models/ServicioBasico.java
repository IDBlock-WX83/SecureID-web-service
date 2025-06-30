package com.example.webService.SocialServices.domain.models;

import java.time.LocalDate;
import java.time.LocalTime;

import com.example.webService.Identification.domain.model.Autoridad;
import com.example.webService.Identification.domain.model.Distrito;
import jakarta.persistence.*;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@Getter
@Setter
@Entity
@Table(name = "servicio_basico")
public class ServicioBasico {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "resumen", nullable = false)
    private String resumen;  // Título o nombre del servicio social
    @Lob
    @Column(name = "imagen", nullable = true, columnDefinition = "LONGBLOB")
    private byte[] imagen;  // Aquí almacenamos la imagen como byte[]

    @Column(name = "lugar", nullable = false)
    private String lugar;  // Ubicación del servicio

    @Column(name = "fecha", nullable = false)
    private LocalDate fecha;  // Fecha de expiración del servicio

    @Column(name = "hora", nullable = false)
    private LocalTime hora;  // Almacena la hora como LocalTime

    @Column(name = "descripcion", nullable = false)
    private String descripcion;  // Almacena la hora como LocalTime

    @Enumerated(EnumType.STRING)
    @Column(name = "servicio_tipo", nullable = false)
    private ServicioBasicoType servicioBasicoType;

    public boolean isExpired() {
        return fecha != null && LocalDate.now().isAfter(fecha);
    }

    // Relación muchos a uno con Autoridad Local
    @ManyToOne
    @JoinColumn(name = "autoridad_local_id", nullable = false)
    private Autoridad autoridadLocal;


    // Relación con Distrito
    @ManyToOne
    @JoinColumn(name = "distrito_id", nullable = false)  // Relacionamos con la tabla Distrito
    private Distrito distrito;
}

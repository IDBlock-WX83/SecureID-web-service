package com.example.webService.SocialServices.infrastructure.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.webService.SocialServices.domain.models.ServicioBasico;
import com.example.webService.SocialServices.domain.models.ServicioBasicoType;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ServicioBasicoRepository extends JpaRepository<ServicioBasico, Integer> {

    List<ServicioBasico> findByServicioBasicoType(ServicioBasicoType servicioBasicoType);

    @Query("SELECT s FROM ServicioBasico s WHERE s.servicioBasicoType = :type AND s.fecha >= :currentDate")
    List<ServicioBasico> findByTypeAndNotExpired(ServicioBasicoType type, LocalDate currentDate);
}
package com.example.webService.Identification.infrastructure.repository;

import com.example.webService.Identification.domain.model.Autoridad;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AutoridadRepository extends JpaRepository<Autoridad, Integer> {
    Optional<Autoridad> findByIdDigital(String idDigital);

}

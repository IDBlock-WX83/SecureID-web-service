package com.example.webService.Identification.infrastructure.repository;

import com.example.webService.Identification.domain.model.Residente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ResidenteRepository extends JpaRepository<Residente, Integer> {
    Residente findByIdDigital(String idDigital);

}

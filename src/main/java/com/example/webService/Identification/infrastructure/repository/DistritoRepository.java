package com.example.webService.Identification.infrastructure.repository;

import com.example.webService.Identification.domain.model.Distrito;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DistritoRepository extends JpaRepository<Distrito, Integer> {
    // Buscar distritos por provincia
    List<Distrito> findByProvinciaId(int provinciaId);
}

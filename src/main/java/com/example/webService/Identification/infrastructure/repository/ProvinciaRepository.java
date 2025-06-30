package com.example.webService.Identification.infrastructure.repository;

import com.example.webService.Identification.domain.model.Provincia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface  ProvinciaRepository extends JpaRepository<Provincia, Integer> {
    // Buscar provincias por departamento
    List<Provincia> findByDepartamentoId(int departamentoId);
}

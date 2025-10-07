package com.example.webService.Identification.infrastructure.repository;

import com.example.webService.Identification.domain.model.Sexo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface SexoRepository extends JpaRepository<Sexo, Integer> {
}

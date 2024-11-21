package com.example.webService.Blockchain.infrastructure.persistence.jpa.repositories;


import com.example.webService.Blockchain.domain.model.Services;
import org.springframework.data.jpa.repository.JpaRepository;



public interface ServiceRepository extends JpaRepository<Services, Long> {
    // Métodos CRUD básicos están disponibles por defecto
}

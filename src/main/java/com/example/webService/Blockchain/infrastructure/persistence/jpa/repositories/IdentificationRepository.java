package com.example.webService.Blockchain.infrastructure.persistence.jpa.repositories;

import com.example.webService.Blockchain.domain.model.Identification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IdentificationRepository extends JpaRepository<Identification, Long> {

}

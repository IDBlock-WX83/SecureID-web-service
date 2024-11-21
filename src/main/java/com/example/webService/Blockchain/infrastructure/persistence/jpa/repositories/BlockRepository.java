package com.example.webService.Blockchain.infrastructure.persistence.jpa.repositories;

import com.example.webService.Blockchain.domain.model.Block;
import com.example.webService.Blockchain.domain.model.Identification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BlockRepository extends JpaRepository<Block, Long> {

}

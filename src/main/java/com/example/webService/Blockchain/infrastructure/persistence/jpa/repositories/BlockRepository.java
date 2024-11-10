package com.example.webService.Blockchain.infrastructure.persistence.jpa.repositories;

import com.example.webService.Blockchain.domain.model.Block;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BlockRepository extends JpaRepository<Block, Long> {
}

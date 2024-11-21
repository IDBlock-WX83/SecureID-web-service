package com.example.webService.SocialServices.infrastructure.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.webService.SocialServices.domain.models.SocialServices;
import com.example.webService.SocialServices.domain.models.SocialServicesType;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface SocialServicesRepository extends JpaRepository<SocialServices, Integer> {

    List<SocialServices> findBySocialServicesType(SocialServicesType socialServicesType);

    @Query("SELECT s FROM SocialServices s WHERE s.socialServicesType = :type AND s.expirationDate > :currentDate")
    List<SocialServices> findByTypeAndNotExpired(SocialServicesType type, LocalDate currentDate);
}
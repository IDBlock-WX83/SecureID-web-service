package com.example.webService.Blockchain.domain.services;
import com.example.webService.Blockchain.domain.model.Services;
import com.example.webService.Blockchain.infrastructure.persistence.jpa.repositories.ServiceRepository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;


@Service
public class ServiceService {
    private final ServiceRepository serviceRepository;

    public ServiceService(ServiceRepository serviceRepository) {
        this.serviceRepository = serviceRepository;
    }

    public List<Services> getAllServices() {
        return serviceRepository.findAll();
    }

    public Services getServiceById(Long id) {
        return serviceRepository.findById(id).orElseThrow(() -> new RuntimeException("Service not found"));
    }

    public Services createService(Services services) {
        return serviceRepository.save(services);
    }

    public Services updateService(Long id, Services updatedService) {
        Services services = serviceRepository.findById(id).orElseThrow(() -> new RuntimeException("Service not found"));
        services.setTitle(updatedService.getTitle());
        services.setImageUrl(updatedService.getImageUrl());
        services.setLocation(updatedService.getLocation());
        services.setAvailability(updatedService.getAvailability());
        services.setExpirationDate(updatedService.getExpirationDate());
        services.setUpdatedAt(LocalDateTime.now());
        return serviceRepository.save(services);
    }

    public void deleteService(Long id) {
        serviceRepository.deleteById(id);
    }
}

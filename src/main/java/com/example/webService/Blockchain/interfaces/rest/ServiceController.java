package com.example.webService.Blockchain.interfaces.rest;

import com.example.webService.Blockchain.domain.model.Services;
import com.example.webService.Blockchain.domain.services.ServiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/services")
public class ServiceController {

    @Autowired
    private ServiceService serviceService;

    @GetMapping("/show")
    public List<Services> getAllServices() {
        return serviceService.getAllServices();
    }

    @GetMapping("/{id}")
    public Services getServiceById(@PathVariable Long id) {
        return serviceService.getServiceById(id);
    }

    @PostMapping("/add")
    public Services addService(@RequestBody Services services) {
        return serviceService.createService(services);
    }

    @PutMapping("/{id}")
    public Services updateService(@PathVariable Long id, @RequestBody Services updatedService) {
        return serviceService.updateService(id, updatedService);
    }

    @DeleteMapping("/{id}")
    public void deleteService(@PathVariable Long id) {
        serviceService.deleteService(id);
    }
}

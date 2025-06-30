package com.example.webService.Identification.interfaces.controllers;

import com.example.webService.Identification.domain.model.Departamento;
import com.example.webService.Identification.domain.model.Distrito;
import com.example.webService.Identification.domain.model.Provincia;
import com.example.webService.Identification.domain.services.LocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
public class LocationController {

    @Autowired
    private LocationService locationService;

    // Endpoint para obtener todos los departamentos
    @GetMapping("/departamentos")
    public List<Departamento> getAllDepartments() {
        return locationService.getAllDepartments();
    }

    // Endpoint para obtener provincias por departamento
    @GetMapping("/departamentos/{id}/provincias")
    public List<Provincia> getProvincesByDepartment(@PathVariable("id") int departmentId) {
        return locationService.getProvincesByDepartment(departmentId);
    }

    // Endpoint para obtener distritos por provincia
    @GetMapping("/provincias/{id}/distritos")
    public List<Distrito> getDistrictsByProvince(@PathVariable("id") int provinceId) {
        return locationService.getDistrictsByProvince(provinceId);
    }
}

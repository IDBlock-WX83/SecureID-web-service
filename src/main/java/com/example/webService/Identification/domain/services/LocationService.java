package com.example.webService.Identification.domain.services;

import com.example.webService.Identification.domain.model.Departamento;
import com.example.webService.Identification.domain.model.Distrito;
import com.example.webService.Identification.domain.model.Provincia;
import com.example.webService.Identification.infrastructure.repository.DepartamentoRepository;
import com.example.webService.Identification.infrastructure.repository.DistritoRepository;
import com.example.webService.Identification.infrastructure.repository.ProvinciaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LocationService {
    @Autowired
    private DepartamentoRepository departamentoRepository;

    @Autowired
    private ProvinciaRepository provinciaRepository;

    @Autowired
    private DistritoRepository distritoRepository;

    // Obtener todos los departamentos
    public List<Departamento> getAllDepartments() {
        return departamentoRepository.findAll();
    }

    // Obtener provincias por departamento
    public List<Provincia> getProvincesByDepartment(int departmentId) {
        return provinciaRepository.findByDepartamentoId(departmentId);
    }

    // Obtener distritos por provincia
    public List<Distrito> getDistrictsByProvince(int provinceId) {
        return distritoRepository.findByProvinciaId(provinceId);
    }

}

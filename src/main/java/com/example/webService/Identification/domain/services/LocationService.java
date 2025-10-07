package com.example.webService.Identification.domain.services;

import com.example.webService.Identification.domain.model.Departamento;
import com.example.webService.Identification.domain.model.Distrito;
import com.example.webService.Identification.domain.model.Provincia;
import com.example.webService.Identification.infrastructure.repository.DepartamentoRepository;
import com.example.webService.Identification.infrastructure.repository.DistritoRepository;
import com.example.webService.Identification.infrastructure.repository.ProvinciaRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class LocationService {
    @Autowired
    private DepartamentoRepository departamentoRepository;

    @Autowired
    private ProvinciaRepository provinciaRepository;

    @Autowired
    private DistritoRepository distritoRepository;

    // Obtener todos los departamentos
    public List<Departamento> getAllDepartments() {
        long startTime = System.currentTimeMillis();  // Tiempo de inicio
        log.info("Start getting all departments");

        List<Departamento> departments = departamentoRepository.findAll();

        long endTime = System.currentTimeMillis();  // Tiempo de fin
        log.info("End getting all departments. Latency: {} ms", endTime - startTime);  // Log de latencia

        return departments;
    }

    // Obtener provincias por departamento
    public List<Provincia> getProvincesByDepartment(int departmentId) {
        long startTime = System.currentTimeMillis();  // Tiempo de inicio
        log.info("Start getting provinces by department ID: {}", departmentId);

        List<Provincia> provinces = provinciaRepository.findByDepartamentoId(departmentId);

        long endTime = System.currentTimeMillis();  // Tiempo de fin
        log.info("End getting provinces by department ID: {}. Latency: {} ms", departmentId, endTime - startTime);  // Log de latencia

        return provinces;
    }

    // Obtener distritos por provincia
    public List<Distrito> getDistrictsByProvince(int provinceId) {
        long startTime = System.currentTimeMillis();  // Tiempo de inicio
        log.info("Start getting districts by province ID: {}", provinceId);

        List<Distrito> districts = distritoRepository.findByProvinciaId(provinceId);

        long endTime = System.currentTimeMillis();  // Tiempo de fin
        log.info("End getting districts by province ID: {}. Latency: {} ms", provinceId, endTime - startTime);  // Log de latencia

        return districts;
    }
}

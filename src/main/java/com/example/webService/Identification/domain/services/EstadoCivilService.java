package com.example.webService.Identification.domain.services;

import com.example.webService.Identification.domain.model.EstadoCivil;
import com.example.webService.Identification.infrastructure.repository.EstadoCivilRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class EstadoCivilService {

    @Autowired
    private EstadoCivilRepository estadoCivilRepository;

    // Obtener todos los estados civil
    public List<EstadoCivil> getAllEstadosCivil() {
        long startTime = System.currentTimeMillis();  // Tiempo de inicio
        log.info("Start fetching all civil states");

        List<EstadoCivil> estadosCivil = estadoCivilRepository.findAll();

        long endTime = System.currentTimeMillis();  // Tiempo de fin
        log.info("End fetching all civil states. Latency: {} ms", endTime - startTime);  // Log de latencia

        return estadosCivil;
    }
}

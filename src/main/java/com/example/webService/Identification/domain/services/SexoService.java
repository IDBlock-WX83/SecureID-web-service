package com.example.webService.Identification.domain.services;

import com.example.webService.Identification.domain.model.Sexo;
import com.example.webService.Identification.infrastructure.repository.SexoRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class SexoService {

    @Autowired
    private SexoRepository sexoRepository;

    // Obtener todos los estados civil
    public List<Sexo> getAllSexos() {
        long startTime = System.currentTimeMillis(); // Tiempo de inicio
        log.info("Start getAllSexos");

        List<Sexo> result = sexoRepository.findAll(); // Obtiene todos los sexos.

        long endTime = System.currentTimeMillis(); // Tiempo de fin
        log.info("End getAllSexos. Latency: {} ms", endTime - startTime); // Log de latencia

        return result;
    }
}

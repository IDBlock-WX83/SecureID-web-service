package com.example.webService.Identification.domain.services;

import com.example.webService.Identification.domain.model.EstadoCivil;
import com.example.webService.Identification.infrastructure.repository.EstadoCivilRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EstadoCivilService {

    @Autowired
    private EstadoCivilRepository estadoCivilRepository;

    // Obtener todos los estados civil
    public List<EstadoCivil> getAllEstadosCivil() {
        return estadoCivilRepository.findAll();
    }
}

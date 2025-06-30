package com.example.webService.Identification.domain.services;

import com.example.webService.Identification.domain.model.Sexo;
import com.example.webService.Identification.infrastructure.repository.SexoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SexoService {


    @Autowired
    private SexoRepository sexoRepository;

    // Obtener todos los estados civil
    public List<Sexo> getAllSexos() {
        return sexoRepository.findAll();
    }
}

package com.example.webService.Identification.interfaces.controllers;


import com.example.webService.Identification.domain.model.EstadoCivil;
import com.example.webService.Identification.domain.model.Sexo;
import com.example.webService.Identification.domain.services.EstadoCivilService;
import com.example.webService.Identification.domain.services.SexoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
public class SexoController {

    @Autowired
    private SexoService sexoService;

    // Endpoint para obtener todos los estados civil
    @GetMapping("/sexos")
    public List<Sexo> getAllSexos() {
        return sexoService.getAllSexos();
    }
}

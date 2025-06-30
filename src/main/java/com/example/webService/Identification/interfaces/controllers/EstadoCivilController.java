package com.example.webService.Identification.interfaces.controllers;


import com.example.webService.Identification.domain.model.EstadoCivil;
import com.example.webService.Identification.domain.services.EstadoCivilService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
public class EstadoCivilController {


    @Autowired
    private EstadoCivilService estadoCivilService;

    // Endpoint para obtener todos los estados civil
    @GetMapping("/estados-civil")
    public List<EstadoCivil> getAllDepartments() {
        return estadoCivilService.getAllEstadosCivil();
    }
}

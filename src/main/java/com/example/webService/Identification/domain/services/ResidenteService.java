package com.example.webService.Identification.domain.services;

import com.example.webService.Identification.domain.mappers.ResidenteMapper;
import com.example.webService.Identification.domain.model.Distrito;
import com.example.webService.Identification.domain.model.EstadoCivil;
import com.example.webService.Identification.domain.model.Residente;
import com.example.webService.Identification.domain.model.Sexo;
import com.example.webService.Identification.infrastructure.repository.DistritoRepository;
import com.example.webService.Identification.infrastructure.repository.EstadoCivilRepository;
import com.example.webService.Identification.infrastructure.repository.ResidenteRepository;
import com.example.webService.Identification.infrastructure.repository.SexoRepository;
import com.example.webService.Identification.presentation.dtos.ResidenteCreateDto;
import com.example.webService.Identification.presentation.dtos.ResidenteResponseDto;
import com.example.webService.SocialServices.presentation.dtos.ServicioBasicoResponseDto;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.Instant;
import java.time.ZoneId;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ResidenteService {

    private final SexoRepository sexoRepository;
    private final EstadoCivilRepository estadoCivilRepository;
    private final DistritoRepository distritoRepository;
    private final ResidenteRepository residenteRepository;

    private final RestTemplate restTemplate = new RestTemplate();

    private final ResidenteMapper residenteMapper;

    public ResidenteService(
            SexoRepository sexoRepository,
            EstadoCivilRepository estadoCivilRepository,
            DistritoRepository distritoRepository,
            ResidenteRepository residenteRepository,
            ResidenteMapper residenteMapper
    ) {
        this.sexoRepository = sexoRepository;
        this.estadoCivilRepository = estadoCivilRepository;
        this.distritoRepository = distritoRepository;
        this.residenteRepository=residenteRepository;
        this.residenteMapper = residenteMapper;
    }

    public ResidenteResponseDto createIdentification(ResidenteCreateDto createDto) {
        String idDigital = generarIdentificadorUnico();

        Map<String, Object> blockchainRequest = new HashMap<>();
        blockchainRequest.put("preNombres", createDto.preNombres);
        blockchainRequest.put("primerApellido", createDto.primerApellido);
        blockchainRequest.put("segundoApellido", createDto.segundoApellido);
        blockchainRequest.put("fechaNacimiento", createDto.fechaNacimiento.toEpochDay() * 86400);
        blockchainRequest.put("fechaInscripcion", createDto.fechaInscripcion.toEpochDay() * 86400);
        blockchainRequest.put("direccion", createDto.direccion);
        blockchainRequest.put("telefonoCelular", createDto.telefonoCelular != null ? createDto.telefonoCelular : "");


        /*blockchainRequest.put("fotoHash", createDto.fotoHash);
        blockchainRequest.put("firmaHash", createDto.firmaHash);*/

        blockchainRequest.put("fotoHash", "");
        blockchainRequest.put("firmaHash", "");

        blockchainRequest.put("idDigital", idDigital);
        blockchainRequest.put("estadoCivilId", createDto.estadoCivil.getId());
        blockchainRequest.put("sexoId", createDto.sexo.getId());
        blockchainRequest.put("distritoId", createDto.distrito.getId());

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Map<String, Object>> requestEntity = new HttpEntity<>(blockchainRequest, headers);

        String blockchainUrl = "http://localhost:5000/api/residentes/registrar";

        String txHash;
        try {
            ResponseEntity<String> response = restTemplate.postForEntity(blockchainUrl, requestEntity, String.class);
            System.out.println(" Blockchain response: " + response.getBody());

            ObjectMapper mapper = new ObjectMapper();
            JsonNode jsonNode = mapper.readTree(response.getBody());
            txHash = jsonNode.get("txHash").asText();
            System.out.println("Transacción Hash Blockchain: " + txHash);

        } catch (Exception e) {
            throw new RuntimeException("Error al registrar en Blockchain: " + e.getMessage());
        }

        ResidenteResponseDto dto = loginByIdDigital(idDigital);
        dto.txHash = txHash;
        return dto;
    }

    private String generarIdentificadorUnico() {
        StringBuilder sb = new StringBuilder(8);
        for (int i = 0; i < 8; i++) {
            sb.append((int) (Math.random() * 10));
        }
        return sb.toString();
    }

    /*public List<ResidenteResponseDto> getAllIdentifications() {
        String blockchainUrl = "http://localhost:5000/api/residentes";
        ResponseEntity<Map> response = restTemplate.getForEntity(blockchainUrl, Map.class);

        List<Map<String, Object>> residentesRaw = (List<Map<String, Object>>) response.getBody().get("residentes");

        return residentesRaw.stream().map(this::mapBlockchainResidente).toList();
    }*/


    public List<ResidenteResponseDto> getAllIdentifications() {
        String blockchainUrl = "http://localhost:5000/api/residentes";
        ResponseEntity<Map> response = restTemplate.getForEntity(blockchainUrl, Map.class);

        List<Map<String, Object>> residentesRaw = (List<Map<String, Object>>) response.getBody().get("residentes");

        // Aquí usamos map para combinar los datos de la blockchain con los de la base de datos.
        return residentesRaw.stream()
                .map(blockchainResidente -> {
                    // Convertir el residente de la blockchain en un DTO
                    ResidenteResponseDto dto = mapBlockchainResidente(blockchainResidente);

                    // Buscar los datos adicionales de la base de datos usando idDigital
                    Residente residenteDb = residenteRepository.findByIdDigital(dto.getIdDigital());

                    // Si se encuentra en la base de datos, asignar la foto y la firma
                    if (residenteDb != null) {
                        dto.setFotoHash(residenteDb.getFotoHash());
                        dto.setFirmaHash(residenteDb.getFirmaHash());
                    }

                    return dto;
                })
                .collect(Collectors.toList());
    }


    public ResidenteResponseDto getIdentificationById(int id) {
        // Hacemos la consulta a la blockchain usando el id
        String blockchainUrl = "http://localhost:5000/api/residentes/" + id;
        ResponseEntity<Map> response = restTemplate.getForEntity(blockchainUrl, Map.class);

        // Obtenemos los datos del residente desde la blockchain
        Map<String, Object> raw = (Map<String, Object>) response.getBody().get("residente");

        // Convertimos los datos obtenidos de la blockchain a un DTO
        ResidenteResponseDto dto = mapBlockchainResidente(raw);

        // Obtenemos el idDigital desde el DTO, lo cual proviene de la blockchain
        String idDigital = dto.getIdDigital();

        // Ahora buscamos en la base de datos el residente utilizando el idDigital
        Residente residenteDb = residenteRepository.findByIdDigital(idDigital);

        // Si encontramos el residente en la base de datos, asignamos la foto y firma
        if (residenteDb != null) {
            dto.setFotoHash(residenteDb.getFotoHash());
            dto.setFirmaHash(residenteDb.getFirmaHash());
        }

        // Devolvemos el DTO con los datos combinados (Blockchain + Base de Datos)
        return dto;
    }



    /* public List<ResidenteResponseDto> getIdentificationById(int id) {
        String blockchainUrl = "http://localhost:5000/api/residentes/" + id;
        ResponseEntity<Map> response = restTemplate.getForEntity(blockchainUrl, Map.class);

        Map<String, Object> raw = (Map<String, Object>) response.getBody().get("residente");

        return List.of(mapBlockchainResidente(raw));
    }*/

    public ResidenteResponseDto loginByIdDigital(String idDigital) {
        String blockchainUrl = "http://localhost:5000/api/residentes/login";

        Map<String, String> request = new HashMap<>();
        request.put("idDigital", idDigital);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Map<String, String>> requestEntity = new HttpEntity<>(request, headers);

        ResponseEntity<Map> response = restTemplate.postForEntity(blockchainUrl, requestEntity, Map.class);

        Map<String, Object> raw = (Map<String, Object>) response.getBody().get("residente");

        return mapBlockchainResidente(raw);
    }

    public List<ResidenteResponseDto> getAllIdentificationsIsAdminOrNoAdmin() {
        return getAllIdentifications();
    }

    private ResidenteResponseDto mapBlockchainResidente(Map<String, Object> raw) {
        ResidenteResponseDto dto = new ResidenteResponseDto();

        dto.id = (int) raw.get("id");
        dto.preNombres = (String) raw.get("preNombres");
        dto.primerApellido = (String) raw.get("primerApellido");
        dto.segundoApellido = (String) raw.get("segundoApellido");

        Long fechaNacUnix = raw.get("fechaNacimiento") != null
                ? Long.valueOf(raw.get("fechaNacimiento").toString()) : null;
        Long fechaInsUnix = raw.get("fechaInscripcion") != null
                ? Long.valueOf(raw.get("fechaInscripcion").toString()) : null;

        dto.fechaNacimiento = fechaNacUnix != null
                ? Instant.ofEpochSecond(fechaNacUnix).atZone(ZoneId.systemDefault()).toLocalDate()
                : null;

        dto.fechaInscripcion = fechaInsUnix != null
                ? Instant.ofEpochSecond(fechaInsUnix).atZone(ZoneId.systemDefault()).toLocalDate()
                : null;

        dto.direccion = (String) raw.get("direccion");
        dto.telefonoCelular = (String) raw.get("telefonoCelular");
        dto.idDigital = (String) raw.get("idDigital");

        dto.fotoHash = ((String) raw.get("fotoHash")).getBytes();
        dto.firmaHash = ((String) raw.get("firmaHash")).getBytes();
        /* dto.fotoHash = (String) raw.get("fotoHash");
        dto.firmaHash = (String) raw.get("firmaHash");*/

        Integer sexoId = (Integer) raw.get("sexoId");
        Integer estadoCivilId = (Integer) raw.get("estadoCivilId");
        Integer distritoId = (Integer) raw.get("distritoId");

        dto.sexo = sexoRepository.findById(sexoId).orElse(null);
        dto.estadoCivil = estadoCivilRepository.findById(estadoCivilId).orElse(null);
        dto.distrito = distritoRepository.findById(distritoId).orElse(null);

        return dto;
    }


    public ResidenteResponseDto saveImages(ResidenteCreateDto createDto) {
        Residente entity = residenteMapper.toEntity(createDto);
        Residente savedEntity = residenteRepository.save(entity);
        return residenteMapper.toResponseDto(savedEntity);
    }


}

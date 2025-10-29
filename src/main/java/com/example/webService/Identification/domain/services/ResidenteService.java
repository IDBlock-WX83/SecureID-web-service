package com.example.webService.Identification.domain.services; // Define el paquete en el que se encuentra el servicio.

import com.example.webService.Identification.domain.mappers.ResidenteMapper; // Importa el mapeador que convierte entre entidades y DTOs.
import com.example.webService.Identification.domain.model.Distrito; // Importa el modelo Distrito.
import com.example.webService.Identification.domain.model.EstadoCivil; // Importa el modelo EstadoCivil.
import com.example.webService.Identification.domain.model.Residente; // Importa el modelo Residente.
import com.example.webService.Identification.domain.model.Sexo; // Importa el modelo Sexo.
import com.example.webService.Identification.infrastructure.repository.DistritoRepository; // Importa el repositorio Distrito.
import com.example.webService.Identification.infrastructure.repository.EstadoCivilRepository; // Importa el repositorio EstadoCivil.
import com.example.webService.Identification.infrastructure.repository.ResidenteRepository; // Importa el repositorio Residente.
import com.example.webService.Identification.infrastructure.repository.SexoRepository; // Importa el repositorio Sexo.
import com.example.webService.Identification.presentation.dtos.ResidenteCreateDto; // Importa el DTO para crear un residente.
import com.example.webService.Identification.presentation.dtos.ResidenteResponseDto; // Importa el DTO para la respuesta del residente.
import com.example.webService.SocialServices.presentation.dtos.ServicioBasicoResponseDto; // Importa el DTO de servicios básicos (no se usa en este código).
import com.fasterxml.jackson.databind.JsonNode; // Importa JsonNode para trabajar con JSON.
import com.fasterxml.jackson.databind.ObjectMapper; // Importa ObjectMapper para convertir entre objetos Java y JSON.
import org.springframework.http.*; // Importa clases relacionadas con solicitudes HTTP.
import org.springframework.stereotype.Service; // Marca la clase como un servicio de Spring.
import org.springframework.web.client.RestTemplate; // Importa RestTemplate para realizar solicitudes HTTP.

import java.time.Instant; // Importa Instant para manejar fechas.
import java.time.ZoneId; // Importa ZoneId para convertir fechas con zonas horarias.
import java.util.HashMap; // Importa HashMap para almacenar pares clave-valor.
import java.util.List; // Importa List para manejar listas de objetos.
import java.util.Map; // Importa Map para manejar mapas clave-valor.
import java.util.stream.Collectors; // Importa Collectors para trabajar con streams.

@Service // Marca la clase como un servicio en Spring.
public class ResidenteService { // Define la clase de servicio ResidenteService.

    private final SexoRepository sexoRepository; // Repositorio para acceder a datos de Sexo.
    private final EstadoCivilRepository estadoCivilRepository; // Repositorio para acceder a datos de Estado Civil.
    private final DistritoRepository distritoRepository; // Repositorio para acceder a datos de Distrito.
    private final ResidenteRepository residenteRepository; // Repositorio para acceder a datos de Residente.

    private final RestTemplate restTemplate = new RestTemplate(); // Instancia de RestTemplate para hacer solicitudes HTTP.

    private final ResidenteMapper residenteMapper; // Instancia de ResidenteMapper para convertir entre DTOs y entidades.

    // Constructor que inicializa los repositorios y el mapper.
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
        this.residenteRepository = residenteRepository;
        this.residenteMapper = residenteMapper;
    }

    // Función para crear una identificación, que incluye el registro de un residente en la blockchain y en la base de datos.
    public ResidenteResponseDto createIdentification(ResidenteCreateDto createDto) {
        String idDigital = generarIdentificadorUnico(); // Genera un identificador único para el residente.

        // Prepara los datos que se enviarán a la blockchain para registrar al residente.
        Map<String, Object> blockchainRequest = new HashMap<>();
        blockchainRequest.put("preNombres", createDto.preNombres); // Nombres del residente.
        blockchainRequest.put("primerApellido", createDto.primerApellido); // Primer apellido.
        blockchainRequest.put("segundoApellido", createDto.segundoApellido); // Segundo apellido.
        //blockchainRequest.put("fechaNacimiento", createDto.fechaNacimiento.toEpochDay() * 86400); // Convierte la fecha de nacimiento a segundos desde la época.
        //blockchainRequest.put("fechaInscripcion", createDto.fechaInscripcion.toEpochDay() * 86400); // Convierte la fecha de inscripción a segundos desde la época.
        blockchainRequest.put("fechaNacimiento", createDto.fechaNacimiento); // Convierte la fecha de nacimiento a segundos desde la época.
        blockchainRequest.put("fechaInscripcion", createDto.fechaInscripcion); // Convierte la fecha de inscripción a segundos desde la época.
        blockchainRequest.put("direccion", createDto.direccion); // Dirección del residente.
        blockchainRequest.put("telefonoCelular", createDto.telefonoCelular != null ? createDto.telefonoCelular : ""); // Teléfono celular, si está disponible.

        blockchainRequest.put("fotoHash", ""); // Deja en blanco el campo de foto.
        blockchainRequest.put("firmaHash", ""); // Deja en blanco el campo de firma.

        blockchainRequest.put("idDigital", idDigital); // Asigna el ID digital generado.
        blockchainRequest.put("estadoCivilId", createDto.estadoCivil.getId()); // ID de estado civil del residente.
        blockchainRequest.put("sexoId", createDto.sexo.getId()); // ID de sexo del residente.
        blockchainRequest.put("distritoId", createDto.distrito.getId()); // ID de distrito del residente.

        HttpHeaders headers = new HttpHeaders(); // Crea los encabezados de la solicitud.
        headers.setContentType(MediaType.APPLICATION_JSON); // Establece el tipo de contenido como JSON.
        HttpEntity<Map<String, Object>> requestEntity = new HttpEntity<>(blockchainRequest, headers); // Crea la entidad de la solicitud.

        String blockchainUrl = "https://blockchain-secure-id.azurewebsites.net/api/residentes/registrar"; // URL del servicio de la blockchain.

        String txHash; // Variable para almacenar el hash de la transacción de la blockchain.
        try {
            // Realiza la solicitud POST para registrar el residente en la blockchain.
            ResponseEntity<String> response = restTemplate.postForEntity(blockchainUrl, requestEntity, String.class);
            System.out.println(" Blockchain response: " + response.getBody()); // Muestra la respuesta de la blockchain.

            ObjectMapper mapper = new ObjectMapper(); // Crea un objeto ObjectMapper para manejar la respuesta JSON.
            JsonNode jsonNode = mapper.readTree(response.getBody()); // Convierte la respuesta JSON en un árbol de nodos.
            txHash = jsonNode.get("txHash").asText(); // Extrae el hash de la transacción de la blockchain.
            System.out.println("Transacción Hash Blockchain: " + txHash); // Muestra el hash de la transacción.

        } catch (Exception e) {
            throw new RuntimeException("Error al registrar en Blockchain: " + e.getMessage()); // Manejo de errores en caso de que falle la solicitud a la blockchain.
        }

        // Se realiza el login utilizando el ID digital y se devuelve el DTO con el hash de la transacción.
        ResidenteResponseDto dto = loginByIdDigital(idDigital);
        dto.txHash = txHash; // Asigna el hash de la transacción al DTO.
        return dto; // Retorna el DTO con los datos del residente.
    }

    // Función para generar un identificador único (8 dígitos aleatorios).
    private String generarIdentificadorUnico() {
        StringBuilder sb = new StringBuilder(8); // Crea un StringBuilder para el ID.
        for (int i = 0; i < 8; i++) {
            sb.append((int) (Math.random() * 10)); // Genera un número aleatorio entre 0 y 9.
        }
        return sb.toString(); // Retorna el identificador único generado.
    }

    // Función que obtiene todas las identificaciones de los residentes, combinando los datos de la blockchain y la base de datos.
    public List<ResidenteResponseDto> getAllIdentifications() {
        String blockchainUrl = "https://blockchain-secure-id.azurewebsites.net/api/residentes"; // URL para obtener todos los residentes de la blockchain.
        ResponseEntity<Map> response = restTemplate.getForEntity(blockchainUrl, Map.class); // Realiza la solicitud GET a la blockchain.

        List<Map<String, Object>> residentesRaw = (List<Map<String, Object>>) response.getBody().get("residentes"); // Obtiene la lista de residentes desde la respuesta.

        // Combinamos los datos de la blockchain con los de la base de datos.
        return residentesRaw.stream()
                .map(blockchainResidente -> {
                    // Convierte el residente de la blockchain en un DTO.
                    ResidenteResponseDto dto = mapBlockchainResidente(blockchainResidente);

                    // Busca el residente en la base de datos utilizando el ID digital.
                    Residente residenteDb = residenteRepository.findByIdDigital(dto.getIdDigital());

                    // Si el residente está en la base de datos, asignamos la foto y la firma.
                    if (residenteDb != null) {
                        dto.setFotoHash(residenteDb.getFotoHash());
                        dto.setFirmaHash(residenteDb.getFirmaHash());
                    }

                    return dto; // Devuelve el DTO con los datos combinados.
                })
                .collect(Collectors.toList()); // Recoge todos los resultados en una lista.
    }

    // Función que obtiene un residente por su ID, combinando los datos de la blockchain y la base de datos.
    public ResidenteResponseDto getIdentificationById(int id) {
        String blockchainUrl = "https://blockchain-secure-id.azurewebsites.net/api/residentes/" + id; // URL para obtener un residente de la blockchain por su ID.
        ResponseEntity<Map> response = restTemplate.getForEntity(blockchainUrl, Map.class); // Realiza la solicitud GET a la blockchain.

        Map<String, Object> raw = (Map<String, Object>) response.getBody().get("residente"); // Obtiene los datos del residente desde la blockchain.

        // Convierte los datos de la blockchain en un DTO.
        ResidenteResponseDto dto = mapBlockchainResidente(raw);

        String idDigital = dto.getIdDigital(); // Obtiene el ID digital del DTO.

        // Busca el residente en la base de datos utilizando el ID digital.
        Residente residenteDb = residenteRepository.findByIdDigital(idDigital);

        // Si el residente está en la base de datos, asigna la foto y la firma.
        if (residenteDb != null) {
            dto.setFotoHash(residenteDb.getFotoHash());
            dto.setFirmaHash(residenteDb.getFirmaHash());
        }

        return dto; // Retorna el DTO con los datos combinados.
    }

    // Función de login por ID digital, que obtiene los datos del residente.
    public ResidenteResponseDto loginByIdDigital(String idDigital) {
        String blockchainUrl = "https://blockchain-secure-id.azurewebsites.net/api/residentes/login"; // URL para hacer login con el ID digital.

        Map<String, String> request = new HashMap<>();
        request.put("idDigital", idDigital); // Añade el ID digital a la solicitud.

        HttpHeaders headers = new HttpHeaders(); // Crea los encabezados HTTP.
        headers.setContentType(MediaType.APPLICATION_JSON); // Establece el tipo de contenido como JSON.

        HttpEntity<Map<String, String>> requestEntity = new HttpEntity<>(request, headers); // Crea la entidad HTTP con el cuerpo y los encabezados.

        // Realiza la solicitud POST para hacer el login.
        ResponseEntity<Map> response = restTemplate.postForEntity(blockchainUrl, requestEntity, Map.class);

        Map<String, Object> raw = (Map<String, Object>) response.getBody().get("residente"); // Obtiene los datos del residente.

        return mapBlockchainResidente(raw); // Mapea los datos obtenidos de la blockchain a un DTO y lo retorna.
    }

    // Función que obtiene todas las identificaciones, tanto para administradores como no administradores.
    public List<ResidenteResponseDto> getAllIdentificationsIsAdminOrNoAdmin() {
        return getAllIdentifications(); // Llama a la función que obtiene todas las identificaciones.
    }

    // Función auxiliar para mapear los datos de la blockchain a un DTO de ResidenteResponseDto.
    private ResidenteResponseDto mapBlockchainResidente(Map<String, Object> raw) {
        ResidenteResponseDto dto = new ResidenteResponseDto();

        dto.id = (int) raw.get("id"); // Asigna el ID del residente.
        dto.preNombres = (String) raw.get("preNombres"); // Asigna los primeros nombres.
        dto.primerApellido = (String) raw.get("primerApellido"); // Asigna el primer apellido.
        dto.segundoApellido = (String) raw.get("segundoApellido"); // Asigna el segundo apellido.

        Long fechaNacUnix = raw.get("fechaNacimiento") != null
                ? Long.valueOf(raw.get("fechaNacimiento").toString()) : null; // Convierte la fecha de nacimiento desde un valor de epoch.
        Long fechaInsUnix = raw.get("fechaInscripcion") != null
                ? Long.valueOf(raw.get("fechaInscripcion").toString()) : null; // Convierte la fecha de inscripción desde un valor de epoch.

        dto.fechaNacimiento = fechaNacUnix != null
                ? Instant.ofEpochSecond(fechaNacUnix).atZone(ZoneId.systemDefault()).toLocalDate()
                : null; // Convierte la fecha de nacimiento a una fecha legible.

        dto.fechaInscripcion = fechaInsUnix != null
                ? Instant.ofEpochSecond(fechaInsUnix).atZone(ZoneId.systemDefault()).toLocalDate()
                : null; // Convierte la fecha de inscripción a una fecha legible.

        dto.direccion = (String) raw.get("direccion"); // Asigna la dirección.
        dto.telefonoCelular = (String) raw.get("telefonoCelular"); // Asigna el teléfono celular.
        dto.idDigital = (String) raw.get("idDigital"); // Asigna el ID digital.

        dto.fotoHash = ((String) raw.get("fotoHash")).getBytes(); // Convierte el hash de la foto a bytes.
        dto.firmaHash = ((String) raw.get("firmaHash")).getBytes(); // Convierte el hash de la firma a bytes.

        Integer sexoId = (Integer) raw.get("sexoId"); // Obtiene el ID de sexo.
        Integer estadoCivilId = (Integer) raw.get("estadoCivilId"); // Obtiene el ID de estado civil.
        Integer distritoId = (Integer) raw.get("distritoId"); // Obtiene el ID de distrito.

        dto.sexo = sexoRepository.findById(sexoId).orElse(null); // Busca el sexo en la base de datos.
        dto.estadoCivil = estadoCivilRepository.findById(estadoCivilId).orElse(null); // Busca el estado civil en la base de datos.
        dto.distrito = distritoRepository.findById(distritoId).orElse(null); // Busca el distrito en la base de datos.

        return dto; // Retorna el DTO con los datos combinados.
    }

    // Función que guarda las imágenes de un residente en la base de datos.
    public ResidenteResponseDto saveImages(ResidenteCreateDto createDto) {
        Residente entity = residenteMapper.toEntity(createDto); // Convierte el DTO a una entidad Residente.
        Residente savedEntity = residenteRepository.save(entity); // Guarda la entidad en la base de datos.
        return residenteMapper.toResponseDto(savedEntity); // Convierte la entidad guardada a un DTO y lo retorna.
    }
}

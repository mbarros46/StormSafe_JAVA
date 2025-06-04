package br.com.fiap.stormsafe.controller;

import java.util.Optional;

import org.springdoc.core.annotations.ParameterObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.fiap.stormsafe.dto.SensorRequestDTO;
import br.com.fiap.stormsafe.dto.SensorResponseDTO;
import br.com.fiap.stormsafe.model.Sensor;
import br.com.fiap.stormsafe.model.TipoUsuario;
import br.com.fiap.stormsafe.model.Usuario;
import br.com.fiap.stormsafe.repository.SensorRepository;
import br.com.fiap.stormsafe.specification.SensorSpecification;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;

@RestController
@RequestMapping("/api/sensores")
@Slf4j
public class SensorController {
    
    @Autowired
    private SensorRepository repository;

    public record SensorFilters(String tipoSensor, String localizacao) {}

    @PostMapping
    @Operation(summary = "Cadastrar sensor", description = "Cadastra um novo sensor (admin apenas)")
    @CacheEvict(value = "sensores", allEntries = true)
    public ResponseEntity<?> createSensor(@RequestBody @Valid SensorRequestDTO dto,
                                          @AuthenticationPrincipal Usuario usuarioAuth) {
        if (usuarioAuth == null || !usuarioAuth.getTipoUsuario().equals(TipoUsuario.ADMIN)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Acesso negado: Apenas administradores podem criar sensores.");
        }

        Sensor sensor = new Sensor();
        sensor.setTipoSensor(dto.getTipoSensor());
        sensor.setLocalizacao(dto.getLocalizacao());
        sensor.setStatus(dto.getStatus());
        // TODO: set Rio com base no dto (buscar Rio pelo id)

        Sensor savedSensor = repository.save(sensor);
        return ResponseEntity.status(HttpStatus.CREATED).body(new SensorResponseDTO(savedSensor));
    }

    @GetMapping
    @Operation(summary = "Listar sensores", description = "Lista sensores com filtros e paginação")
    @Cacheable("sensores")
    public ResponseEntity<Page<SensorResponseDTO>> listarSensores(
            @RequestParam(required = false) String tipoSensor,
            @RequestParam(required = false) String localizacao,
            @ParameterObject @PageableDefault(size = 10, sort = "id", direction = Sort.Direction.DESC) Pageable pageable) {

        var filters = new SensorFilters(tipoSensor, localizacao);
        var specification = SensorSpecification.withFilters(filters);

        Page<Sensor> sensores = repository.findAll(specification, pageable);
        Page<SensorResponseDTO> sensoresDTO = sensores.map(SensorResponseDTO::new);

        return ResponseEntity.ok(sensoresDTO);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar sensor por Id", description = "Retorna um sensor pelo id")
    public ResponseEntity<?> getSensorById(@PathVariable Long id) {
        Optional<Sensor> optionalSensor = repository.findById(id);
        return optionalSensor
                .map(sensor -> ResponseEntity.ok(new SensorResponseDTO(sensor)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar sensor", description = "Atualiza dados de um sensor (admin apenas)")
    @CacheEvict(value = "sensores", allEntries = true)
    public ResponseEntity<?> updateSensor(@PathVariable Long id, @RequestBody @Valid SensorRequestDTO dto,
                                          @AuthenticationPrincipal Usuario usuarioAuth) {
        if (usuarioAuth == null || !usuarioAuth.getTipoUsuario().equals(TipoUsuario.ADMIN)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Acesso negado: Apenas administradores podem atualizar sensores.");
        }

        Optional<Sensor> optionalSensor = repository.findById(id);

        if (optionalSensor.isPresent()) {
            Sensor sensor = optionalSensor.get();
            sensor.setTipoSensor(dto.getTipoSensor());
            sensor.setLocalizacao(dto.getLocalizacao());
            sensor.setStatus(dto.getStatus());
            // TODO: atualizar Rio se necessário

            Sensor updatedSensor = repository.save(sensor);
            return ResponseEntity.ok(new SensorResponseDTO(updatedSensor));
        }

        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deletar sensor", description = "Remove um sensor do sistema (admin apenas)")
    @CacheEvict(value = "sensores", allEntries = true)
    public ResponseEntity<?> deleteSensor(@PathVariable Long id, @AuthenticationPrincipal Usuario usuarioAuth) {
        if (usuarioAuth == null || !usuarioAuth.getTipoUsuario().equals(TipoUsuario.ADMIN)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Acesso negado: Apenas administradores podem deletar sensores.");
        }

        if (repository.existsById(id)) {
            repository.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}

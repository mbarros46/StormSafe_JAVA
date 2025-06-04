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

import br.com.fiap.stormsafe.dto.LeituraSensorRequestDTO;
import br.com.fiap.stormsafe.dto.LeituraSensorResponseDTO;
import br.com.fiap.stormsafe.model.LeituraSensor;
import br.com.fiap.stormsafe.model.Sensor;
import br.com.fiap.stormsafe.model.TipoUsuario;
import br.com.fiap.stormsafe.model.Usuario;
import br.com.fiap.stormsafe.repository.LeituraSensorRepository;
import br.com.fiap.stormsafe.repository.SensorRepository;
import br.com.fiap.stormsafe.specification.LeituraSensorSpecification;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.data.domain.Sort;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/leituras-sensor")
public class LeituraSensorController {
    
    @Autowired
    private LeituraSensorRepository repository;

    @Autowired
    private SensorRepository sensorRepository;

    public record LeituraSensorFilters(Long sensorId) {}

    @PostMapping
    @Operation(summary = "Cadastrar leitura do sensor", description = "Registra uma nova leitura (admin ou sistema IoT)")
    @CacheEvict(value = "leituras", allEntries = true)
    public ResponseEntity<?> createLeitura(@RequestBody @Valid LeituraSensorRequestDTO dto,
                                       @AuthenticationPrincipal Usuario usuarioAuth) {
        if (usuarioAuth == null || !(usuarioAuth.getTipoUsuario().equals(TipoUsuario.ADMIN) || usuarioAuth.getTipoUsuario().equals(TipoUsuario.ADMIN))) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Acesso negado: Apenas admins ou Defesa Civil podem criar leituras.");
        }

        // Recupera o sensor pelo ID
        Sensor sensor = sensorRepository.findById(dto.getSensorId())
            .orElseThrow(() -> new RuntimeException("Sensor não encontrado"));

        // Criação da leitura
        LeituraSensor leitura = new LeituraSensor();
        leitura.setSensor(sensor); // Agora associamos o objeto Sensor, não o ID
        leitura.setDataHora(dto.getDataHora());
        leitura.setValor(dto.getValor());

        LeituraSensor savedLeitura = repository.save(leitura);
        return ResponseEntity.status(HttpStatus.CREATED).body(new LeituraSensorResponseDTO(savedLeitura));
    }

    @GetMapping
    @Operation(summary = "Listar leituras dos sensores", description = "Lista leituras com filtros e paginação")
    @Cacheable("leituras")
    public ResponseEntity<Page<LeituraSensorResponseDTO>> listarLeituras(
            @RequestParam(required = false) Long sensorId,
            @ParameterObject @PageableDefault(size = 10, sort = "dataHora", direction = Sort.Direction.DESC) Pageable pageable) {

        var filters = new LeituraSensorFilters(sensorId);
        var specification = LeituraSensorSpecification.withFilters(filters);

        Page<LeituraSensor> leituras = repository.findAll(specification, pageable);
        Page<LeituraSensorResponseDTO> leiturasDTO = leituras.map(LeituraSensorResponseDTO::new);

        return ResponseEntity.ok(leiturasDTO);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar leitura por Id", description = "Retorna uma leitura pelo id")
    public ResponseEntity<?> getLeituraById(@PathVariable Long id) {
        Optional<LeituraSensor> optionalLeitura = repository.findById(id);
        return optionalLeitura
                .map(leitura -> ResponseEntity.ok(new LeituraSensorResponseDTO(leitura)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar leitura", description = "Atualiza dados de uma leitura (admin apenas)")
    @CacheEvict(value = "leituras", allEntries = true)
    public ResponseEntity<?> updateLeitura(@PathVariable Long id, @RequestBody @Valid LeituraSensorRequestDTO dto,
                                       @AuthenticationPrincipal Usuario usuarioAuth) {
        if (usuarioAuth == null || !usuarioAuth.getTipoUsuario().equals(TipoUsuario.ADMIN)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Acesso negado: Apenas administradores podem atualizar leituras.");
        }

        Optional<LeituraSensor> optionalLeitura = repository.findById(id);

        if (optionalLeitura.isPresent()) {
            LeituraSensor leitura = optionalLeitura.get();

            // Recupera o sensor pelo ID (aqui é onde ocorre a correção)
            Sensor sensor = sensorRepository.findById(dto.getSensorId())
                .orElseThrow(() -> new RuntimeException("Sensor não encontrado"));

            // Atualiza os dados da leitura
            leitura.setSensor(sensor);  // Associa o objeto Sensor
            leitura.setDataHora(dto.getDataHora());
            leitura.setValor(dto.getValor());

            // Salva a leitura atualizada
            LeituraSensor updatedLeitura = repository.save(leitura);
            return ResponseEntity.ok(new LeituraSensorResponseDTO(updatedLeitura));
        }

        return ResponseEntity.notFound().build();  // Caso o alerta não seja encontrado
    }


    @DeleteMapping("/{id}")
    @Operation(summary = "Deletar leitura", description = "Remove uma leitura do sistema (admin apenas)")
    @CacheEvict(value = "leituras", allEntries = true)
    public ResponseEntity<?> deleteLeitura(@PathVariable Long id, @AuthenticationPrincipal Usuario usuarioAuth) {
        if (usuarioAuth == null || !usuarioAuth.getTipoUsuario().equals(TipoUsuario.ADMIN)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Acesso negado: Apenas administradores podem deletar leituras.");
        }

        if (repository.existsById(id)) {
            repository.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}

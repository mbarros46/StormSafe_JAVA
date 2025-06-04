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

import br.com.fiap.stormsafe.dto.AlertaRequestDTO;
import br.com.fiap.stormsafe.dto.AlertaResponseDTO;
import br.com.fiap.stormsafe.model.Alerta;
import br.com.fiap.stormsafe.model.Regiao;
import br.com.fiap.stormsafe.model.TipoUsuario;
import br.com.fiap.stormsafe.model.Usuario;
import br.com.fiap.stormsafe.repository.AlertaRepository;
import br.com.fiap.stormsafe.repository.RegiaoRepository;
import br.com.fiap.stormsafe.specification.AlertaSpecification;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.data.domain.Sort;

@RestController
@RequestMapping("/api/alertas")
public class AlertaController {
    
    @Autowired
    private AlertaRepository repository;

    @Autowired
    private RegiaoRepository regiaoRepository;

    public record AlertaFilters(Long regiaoId, String nivelCriticidade) {}

    @PostMapping
    @Operation(summary = "Cadastrar alerta", description = "Cadastra um novo alerta (admin)")
    @CacheEvict(value = "alertas", allEntries = true)
    public ResponseEntity<?> createAlerta(@RequestBody @Valid AlertaRequestDTO dto, @AuthenticationPrincipal Usuario usuarioAuth) {
        if (usuarioAuth == null || !(usuarioAuth.getTipoUsuario().equals(TipoUsuario.ADMIN) || usuarioAuth.getTipoUsuario().equals(TipoUsuario.ADMIN))) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Acesso negado: Apenas admins podem criar alertas.");
        }

        // A região é obtida pela busca do ID da região
        Regiao regiao = regiaoRepository.findById(dto.getRegiaoId())
            .orElseThrow(() -> new RuntimeException("Região não encontrada"));

        Alerta alerta = new Alerta();
        alerta.setRegiao(regiao); // Atribuindo a região diretamente ao objeto
        alerta.setMensagem(dto.getMensagem());
        alerta.setNivelCriticidade(dto.getNivelCriticidade());
        alerta.setDataHora(dto.getDataHora());
        alerta.setStatus(dto.getStatus());

        Alerta savedAlerta = repository.save(alerta);
        return ResponseEntity.status(HttpStatus.CREATED).body(new AlertaResponseDTO(savedAlerta));
    }

    @GetMapping
    @Operation(summary = "Listar alertas", description = "Lista alertas com filtros e paginação")
    @Cacheable("alertas")
    public ResponseEntity<Page<AlertaResponseDTO>> listarAlertas(
            @RequestParam(required = false) Long regiaoId,
            @RequestParam(required = false) String nivelCriticidade,
            @ParameterObject @PageableDefault(size = 10, sort = "dataHora", direction = Sort.Direction.DESC) Pageable pageable) {

        var filters = new AlertaFilters(regiaoId, nivelCriticidade);
        var specification = AlertaSpecification.withFilters(filters);

        Page<Alerta> alertas = repository.findAll(specification, pageable);
        Page<AlertaResponseDTO> alertasDTO = alertas.map(AlertaResponseDTO::new);

        return ResponseEntity.ok(alertasDTO);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar alerta por Id", description = "Retorna um alerta pelo id")
    public ResponseEntity<?> getAlertaById(@PathVariable Long id) {
        Optional<Alerta> optionalAlerta = repository.findById(id);
        return optionalAlerta
                .map(alerta -> ResponseEntity.ok(new AlertaResponseDTO(alerta)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar alerta", description = "Atualiza dados de um alerta (admin)")
    @CacheEvict(value = "alertas", allEntries = true)
    public ResponseEntity<?> updateAlerta(@PathVariable Long id, @RequestBody @Valid AlertaRequestDTO dto, @AuthenticationPrincipal Usuario usuarioAuth) {
        // Verifica se o usuário tem permissão para atualizar
        if (usuarioAuth == null || 
        !(usuarioAuth.getTipoUsuario().equals(TipoUsuario.ADMIN) || usuarioAuth.getTipoUsuario().equals(TipoUsuario.ADMIN))) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Acesso negado: Apenas admins podem atualizar alertas.");
        }

        // Procura o alerta pelo ID
        Optional<Alerta> optionalAlerta = repository.findById(id);

        if (optionalAlerta.isPresent()) {
            Alerta alerta = optionalAlerta.get();

            // Recupera a região associada ao alerta pelo ID
            Regiao regiao = regiaoRepository.findById(dto.getRegiaoId())
                .orElseThrow(() -> new RuntimeException("Região não encontrada"));

            // Atualiza os dados do alerta
            alerta.setRegiao(regiao); // Associa a região ao alerta
            alerta.setMensagem(dto.getMensagem());
            alerta.setNivelCriticidade(dto.getNivelCriticidade());
            alerta.setDataHora(dto.getDataHora());
            alerta.setStatus(dto.getStatus());

            // Salva o alerta atualizado
            Alerta updatedAlerta = repository.save(alerta);
            return ResponseEntity.ok(new AlertaResponseDTO(updatedAlerta));
        }

        return ResponseEntity.notFound().build(); // Retorna 404 caso o alerta não seja encontrado
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deletar alerta", description = "Remove um alerta do sistema (admin )")
    @CacheEvict(value = "alertas", allEntries = true)
    public ResponseEntity<?> deleteAlerta(@PathVariable Long id, @AuthenticationPrincipal Usuario usuarioAuth) {
        if (usuarioAuth == null || 
            !(usuarioAuth.getTipoUsuario().equals(TipoUsuario.ADMIN) || usuarioAuth.getTipoUsuario().equals(TipoUsuario.ADMIN))) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Acesso negado: Apenas admins podem deletar alertas.");
        }

        if (repository.existsById(id)) {
            repository.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}



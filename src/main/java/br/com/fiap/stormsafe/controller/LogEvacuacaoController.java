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

import br.com.fiap.stormsafe.dto.LogEvacuacaoRequestDTO;
import br.com.fiap.stormsafe.dto.LogEvacuacaoResponseDTO;
import br.com.fiap.stormsafe.model.LogEvacuacao;
import br.com.fiap.stormsafe.model.Regiao;
import br.com.fiap.stormsafe.model.TipoUsuario;
import br.com.fiap.stormsafe.model.Usuario;
import br.com.fiap.stormsafe.repository.LogEvacuacaoRepository;
import br.com.fiap.stormsafe.repository.RegiaoRepository;
import br.com.fiap.stormsafe.repository.UsuarioRepository;
import br.com.fiap.stormsafe.specification.LogEvacuacaoSpecification;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;

@RestController
@RequestMapping("/api/logs-evacuacao")
@Slf4j
public class LogEvacuacaoController {
    
    @Autowired
    private LogEvacuacaoRepository repository;
    
    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private RegiaoRepository regiaoRepository;



    public record LogEvacuacaoFilters(Long usuarioId, Long regiaoId) {}

    @PostMapping
    @Operation(summary = "Cadastrar log de evacuação", description = "Registra um novo log (admin)")
    @CacheEvict(value = "logsEvacuacao", allEntries = true)
    public ResponseEntity<?> createLog(@RequestBody @Valid LogEvacuacaoRequestDTO dto,
                                   @AuthenticationPrincipal Usuario usuarioAuth) {
        if (usuarioAuth == null || !(usuarioAuth.getTipoUsuario().equals(TipoUsuario.ADMIN) || usuarioAuth.getTipoUsuario().equals(TipoUsuario.ADMIN))) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Acesso negado: Apenas admins podem criar logs.");
        }

        // Recupera o usuário (pode ser usuário autenticado ou outro)
        Usuario usuario = usuarioRepository.findById(dto.getUsuarioId())
            .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        // Recupera a região
        Regiao regiao = regiaoRepository.findById(dto.getRegiaoId())
            .orElseThrow(() -> new RuntimeException("Região não encontrada"));

        // Criação do log de evacuação
        LogEvacuacao log = new LogEvacuacao();
        log.setUsuario(usuario);  // Atribui o usuário ao log
        log.setRegiao(regiao);  // Atribui a região ao log
        log.setDataHora(dto.getDataHora());
        log.setDescricao(dto.getDescricao());

        LogEvacuacao savedLog = repository.save(log);
        return ResponseEntity.status(HttpStatus.CREATED).body(new LogEvacuacaoResponseDTO(savedLog));
    }


    @GetMapping
    @Operation(summary = "Listar logs de evacuação", description = "Lista logs com filtros e paginação")
    @Cacheable("logsEvacuacao")
    public ResponseEntity<Page<LogEvacuacaoResponseDTO>> listarLogs(
            @RequestParam(required = false) Long usuarioId,
            @RequestParam(required = false) Long regiaoId,
            @ParameterObject @PageableDefault(size = 10, sort = "dataHora", direction = Sort.Direction.DESC) Pageable pageable) {

        var filters = new LogEvacuacaoFilters(usuarioId, regiaoId);
        var specification = LogEvacuacaoSpecification.withFilters(filters);

        Page<LogEvacuacao> logs = repository.findAll(specification, pageable);
        Page<LogEvacuacaoResponseDTO> logsDTO = logs.map(LogEvacuacaoResponseDTO::new);

        return ResponseEntity.ok(logsDTO);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar log por Id", description = "Retorna um log pelo id")
    public ResponseEntity<?> getLogById(@PathVariable Long id) {
        Optional<LogEvacuacao> optionalLog = repository.findById(id);
        return optionalLog
                .map(log -> ResponseEntity.ok(new LogEvacuacaoResponseDTO(log)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar log", description = "Atualiza dados de um log (admin)")
    @CacheEvict(value = "logsEvacuacao", allEntries = true)
    public ResponseEntity<?> updateLog(@PathVariable Long id, @RequestBody @Valid LogEvacuacaoRequestDTO dto,
                                   @AuthenticationPrincipal Usuario usuarioAuth) {
        // Verifica se o usuário tem permissão para atualizar
        if (usuarioAuth == null || !(usuarioAuth.getTipoUsuario().equals(TipoUsuario.ADMIN) || usuarioAuth.getTipoUsuario().equals(TipoUsuario.ADMIN))) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Acesso negado: Apenas admins  podem atualizar logs.");
        }

        // Procura o log pelo ID
        Optional<LogEvacuacao> optionalLog = repository.findById(id);

        if (optionalLog.isPresent()) {
            LogEvacuacao log = optionalLog.get();

            // Recupera o usuário e a região pelo ID
            Usuario usuario = usuarioRepository.findById(dto.getUsuarioId())
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

            Regiao regiao = regiaoRepository.findById(dto.getRegiaoId())
                .orElseThrow(() -> new RuntimeException("Região não encontrada"));

            // Atualiza os dados do log
            log.setUsuario(usuario);  // Associa o usuário ao log
            log.setRegiao(regiao);  // Associa a região ao log
            log.setDataHora(dto.getDataHora());
            log.setDescricao(dto.getDescricao());

            // Salva o log atualizado
            LogEvacuacao updatedLog = repository.save(log);
            return ResponseEntity.ok(new LogEvacuacaoResponseDTO(updatedLog));
        }

        return ResponseEntity.notFound().build();  // Caso o log não seja encontrado
    }


    @DeleteMapping("/{id}")
    @Operation(summary = "Deletar log", description = "Remove um log do sistema (admin)")
    @CacheEvict(value = "logsEvacuacao", allEntries = true)
    public ResponseEntity<?> deleteLog(@PathVariable Long id, @AuthenticationPrincipal Usuario usuarioAuth) {
        if (usuarioAuth == null || 
            !(usuarioAuth.getTipoUsuario().equals(TipoUsuario.ADMIN) || usuarioAuth.getTipoUsuario().equals(TipoUsuario.ADMIN))) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Acesso negado: Apenas admins  podem deletar logs.");
        }

        if (repository.existsById(id)) {
            repository.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

}

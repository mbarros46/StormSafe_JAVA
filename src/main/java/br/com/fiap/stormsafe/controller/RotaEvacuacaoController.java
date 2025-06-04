package br.com.fiap.stormsafe.controller;

import java.util.Optional;

import org.springdoc.core.annotations.ParameterObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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

import br.com.fiap.stormsafe.dto.RotaEvacuacaoRequestDTO;
import br.com.fiap.stormsafe.dto.RotaEvacuacaoResponseDTO;
import br.com.fiap.stormsafe.model.RotaEvacuacao;
import br.com.fiap.stormsafe.model.TipoUsuario;
import br.com.fiap.stormsafe.model.Usuario;
import br.com.fiap.stormsafe.repository.RotaEvacuacaoRepository;
import br.com.fiap.stormsafe.specification.RotaEvacuacaoSpecification;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;


@RestController
@RequestMapping("/api/rotas-evacuacao")
@Slf4j
public class RotaEvacuacaoController {
    

    @Autowired
    private RotaEvacuacaoRepository repository;

    public record RotaEvacuacaoFilters(String descricao) {}

    @PostMapping
    @Operation(summary = "Cadastrar rota de evacuação", description = "Cadastra uma nova rota (admin apenas)")
    @CacheEvict(value = "rotasEvacuacao", allEntries = true)
    public ResponseEntity<?> createRota(@RequestBody @Valid RotaEvacuacaoRequestDTO dto, @AuthenticationPrincipal Usuario usuarioAuth) {
        if (usuarioAuth == null || !usuarioAuth.getTipoUsuario().equals(TipoUsuario.ADMIN)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Acesso negado: Apenas administradores podem criar rotas.");
        }

        RotaEvacuacao rota = new RotaEvacuacao();
        rota.setDescricao(dto.getDescricao());
        rota.setCoordenadas(dto.getCoordenadas());
        rota.setStatus(dto.getStatus());
        // TODO: associar região pelo dto.getRegiaoId()

        RotaEvacuacao savedRota = repository.save(rota);
        return ResponseEntity.status(HttpStatus.CREATED).body(new RotaEvacuacaoResponseDTO(savedRota));
    }

    @GetMapping
    @Operation(summary = "Listar rotas de evacuação", description = "Lista rotas com filtros e paginação")
    @Cacheable("rotasEvacuacao")
    public ResponseEntity<Page<RotaEvacuacaoResponseDTO>> listarRotas(
            @RequestParam(required = false) String descricao,
            @ParameterObject @PageableDefault(size = 10, sort = "id", direction = Sort.Direction.DESC) Pageable pageable) {

        var filters = new RotaEvacuacaoFilters(descricao);
        var specification = RotaEvacuacaoSpecification.withFilters(filters);

        Page<RotaEvacuacao> rotas = repository.findAll(specification, pageable);
        Page<RotaEvacuacaoResponseDTO> rotasDTO = rotas.map(RotaEvacuacaoResponseDTO::new);

        return ResponseEntity.ok(rotasDTO);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar rota por Id", description = "Retorna uma rota pelo id")
    public ResponseEntity<?> getRotaById(@PathVariable Long id) {
        Optional<RotaEvacuacao> optionalRota = repository.findById(id);
        return optionalRota
                .map(rota -> ResponseEntity.ok(new RotaEvacuacaoResponseDTO(rota)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar rota", description = "Atualiza dados de uma rota (admin apenas)")
    @CacheEvict(value = "rotasEvacuacao", allEntries = true)
    public ResponseEntity<?> updateRota(@PathVariable Long id, @RequestBody @Valid RotaEvacuacaoRequestDTO dto, @AuthenticationPrincipal Usuario usuarioAuth) {
        if (usuarioAuth == null || !usuarioAuth.getTipoUsuario().equals(TipoUsuario.ADMIN)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Acesso negado: Apenas administradores podem atualizar rotas.");
        }

        Optional<RotaEvacuacao> optionalRota = repository.findById(id);

        if (optionalRota.isPresent()) {
            RotaEvacuacao rota = optionalRota.get();
            rota.setDescricao(dto.getDescricao());
            rota.setCoordenadas(dto.getCoordenadas());
            rota.setStatus(dto.getStatus());
            // TODO: atualizar região pelo dto.getRegiaoId()

            RotaEvacuacao updatedRota = repository.save(rota);
            return ResponseEntity.ok(new RotaEvacuacaoResponseDTO(updatedRota));
        }

        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deletar rota", description = "Remove uma rota do sistema (admin apenas)")
    @CacheEvict(value = "rotasEvacuacao", allEntries = true)
    public ResponseEntity<?> deleteRota(@PathVariable Long id,
                                        @AuthenticationPrincipal Usuario usuarioAuth) {
        if (usuarioAuth == null || !usuarioAuth.getTipoUsuario().equals(TipoUsuario.ADMIN)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Acesso negado: Apenas administradores podem deletar rotas.");
        }

        if (repository.existsById(id)) {
            repository.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
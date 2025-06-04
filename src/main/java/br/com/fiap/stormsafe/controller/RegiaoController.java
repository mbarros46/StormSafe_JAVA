package br.com.fiap.stormsafe.controller;

import java.util.Optional;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

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
import org.springframework.web.bind.annotation.*;

import br.com.fiap.stormsafe.dto.RegiaoRequestDTO;
import br.com.fiap.stormsafe.dto.RegiaoResponseDTO;
import br.com.fiap.stormsafe.model.Regiao;
import br.com.fiap.stormsafe.model.TipoUsuario;
import br.com.fiap.stormsafe.model.Usuario;
import br.com.fiap.stormsafe.repository.RegiaoRepository;
import br.com.fiap.stormsafe.specification.RegiaoFilters;
import br.com.fiap.stormsafe.specification.RegiaoSpecification;

@RestController
@RequestMapping("/api/regioes")
@Slf4j
public class RegiaoController {
    

    @Autowired
    private RegiaoRepository repository;

    @PostMapping
    @Operation(summary = "Cadastrar região", description = "Cadastra uma nova região (admin apenas)")
    @CacheEvict(value = "regioes", allEntries = true)
    public ResponseEntity<?> createRegiao(@RequestBody @Valid RegiaoRequestDTO dto, @AuthenticationPrincipal Usuario usuarioAuth) {
        if (usuarioAuth == null || !usuarioAuth.getTipoUsuario().equals(TipoUsuario.ADMIN)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Acesso negado: Apenas administradores podem criar regiões.");
        }

        Regiao regiao = new Regiao();
        regiao.setNome(dto.getNome());
        regiao.setLatitude(dto.getLatitude());
        regiao.setLongitude(dto.getLongitude());

        Regiao savedRegiao = repository.save(regiao);
        return ResponseEntity.status(HttpStatus.CREATED).body(new RegiaoResponseDTO(savedRegiao));
    }

    @GetMapping
    @Operation(summary = "Listar regiões", description = "Lista regiões com filtros e paginação")
    @Cacheable("regioes")
    public ResponseEntity<Page<RegiaoResponseDTO>> listarRegioes(
        @RequestParam(required = false) String nome,
        @ParameterObject @PageableDefault(size = 10, sort = "id", direction = Sort.Direction.DESC) Pageable pageable) {

        // Criação do filtro, passando o nome
        var filters = new RegiaoFilters(nome); // Passando o nome para o filtro
    
         // Usando a specification para filtrar baseado nos parâmetros
        var specification = RegiaoSpecification.withFilters(filters);  // Agora a specification é do tipo Specification<Regiao>
    
         // Executando a consulta com a specification e pageable
        Page<Regiao> regioes = repository.findAll(specification, pageable);  // Passando Specification<Regiao> corretamente

        // Convertendo as regiões para DTOs
        Page<RegiaoResponseDTO> regioesDTO = regioes.map(RegiaoResponseDTO::new);

        return ResponseEntity.ok(regioesDTO);
    }


    @GetMapping("/{id}")
    @Operation(summary = "Buscar região por Id", description = "Retorna uma região pelo id")
    public ResponseEntity<?> getRegiaoById(@PathVariable Long id) {
        Optional<Regiao> optionalRegiao = repository.findById(id);
        return optionalRegiao
                .map(regiao -> ResponseEntity.ok(new RegiaoResponseDTO(regiao)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar região", description = "Atualiza dados de uma região (admin apenas)")
    @CacheEvict(value = "regioes", allEntries = true)
    public ResponseEntity<?> updateRegiao(@PathVariable Long id, @RequestBody @Valid RegiaoRequestDTO dto,
                                          @AuthenticationPrincipal Usuario usuarioAuth) {
        if (usuarioAuth == null || !usuarioAuth.getTipoUsuario().equals(TipoUsuario.ADMIN)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Acesso negado: Apenas administradores podem atualizar regiões.");
        }

        Optional<Regiao> optionalRegiao = repository.findById(id);

        if (optionalRegiao.isPresent()) {
            Regiao regiao = optionalRegiao.get();
            regiao.setNome(dto.getNome());
            regiao.setLatitude(dto.getLatitude());
            regiao.setLongitude(dto.getLongitude());

            Regiao updatedRegiao = repository.save(regiao);
            return ResponseEntity.ok(new RegiaoResponseDTO(updatedRegiao));
        }

        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deletar região", description = "Remove uma região do sistema (admin apenas)")
    @CacheEvict(value = "regioes", allEntries = true)
    public ResponseEntity<?> deleteRegiao(@PathVariable Long id, @AuthenticationPrincipal Usuario usuarioAuth) {
        if (usuarioAuth == null || !usuarioAuth.getTipoUsuario().equals(TipoUsuario.ADMIN)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Acesso negado: Apenas administradores podem deletar regiões.");
        }

        if (repository.existsById(id)) {
            repository.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}

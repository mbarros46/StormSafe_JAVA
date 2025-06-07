package br.com.fiap.stormsafe.controller;

import br.com.fiap.stormsafe.dto.UsuarioRequestDTO;
import br.com.fiap.stormsafe.dto.UsuarioResponseDTO;
import br.com.fiap.stormsafe.model.TipoUsuario;
import br.com.fiap.stormsafe.model.Usuario;
import br.com.fiap.stormsafe.repository.UsuarioRepository;

import br.com.fiap.stormsafe.specification.UsuarioSpecification;
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
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/usuarios")
@Slf4j
public class UsuarioController {

    @Autowired
    private UsuarioRepository repository;

    @Autowired
    private PasswordEncoder encoder;

    public record UsuarioFilters(String nome, String email) {}

    @PostMapping
    @Operation(summary = "Cadastrar usuário", description = "Cadastra um novo usuário com role POPULACAO, ADMIN ou DEFESACIVIL")
    @CacheEvict(value = "usuarios", allEntries = true)
    public ResponseEntity<UsuarioResponseDTO> createUsuario(@RequestBody @Valid UsuarioRequestDTO usuarioDTO) {
        Usuario usuario = new Usuario();
        usuario.setNome(usuarioDTO.getNome());
        usuario.setEmail(usuarioDTO.getEmail());
        usuario.setSenha(encoder.encode(usuarioDTO.getSenha()));

        try {
            usuario.setTipoUsuario(usuarioDTO.getTipoUsuario());
        } catch (IllegalArgumentException e) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(null);
        }

        Usuario savedUsuario = repository.save(usuario);
        return ResponseEntity.status(HttpStatus.CREATED).body(new UsuarioResponseDTO(savedUsuario));
    }

    @GetMapping
    @Operation(summary = "Listar usuários cadastrados", description = "Retorna todos os usuários cadastrados")
    @Cacheable("usuarios")
    public ResponseEntity<?> listarUsuarios(
            @RequestParam(required = false) String nome,
            @RequestParam(required = false) String email,
            @ParameterObject @PageableDefault(size = 10, sort = "id", direction = Sort.Direction.DESC) Pageable pageable,
            @AuthenticationPrincipal Usuario usuarioAuth
    ) {
        var filters = new UsuarioFilters(nome, email);
        var specification = UsuarioSpecification.withFilters(filters);

        Page<Usuario> usuarios = repository.findAll(specification, pageable);
        Page<UsuarioResponseDTO> usuariosDTO = usuarios.map(UsuarioResponseDTO::new);

        return ResponseEntity.ok(usuariosDTO);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar usuário por Id", description = "Retorna um usuário pelo id (ADMIN ou dono do perfil)")
    public ResponseEntity<?> getUsuarioById(@PathVariable Long id, @AuthenticationPrincipal Usuario usuarioAuth) {
        if (usuarioAuth == null || (!usuarioAuth.getId().equals(id) && !usuarioAuth.getTipoUsuario().equals(TipoUsuario.ADMIN))) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Acesso negado.");
        }

        Optional<Usuario> optionalUsuario = repository.findById(id);
        return optionalUsuario
                .map(usuario -> ResponseEntity.ok(new UsuarioResponseDTO(usuario)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar usuário", description = """
            Atualiza os dados de um usuário existente. 
            Apenas usuários com role ADMIN podem alterar o campo 'tipoUsuario'.
            Usuários comuns (POPULACAO) podem atualizar apenas nome, email e senha.
            """
    )
    @CacheEvict(value = "usuarios", allEntries = true)
    public ResponseEntity<?> updateUsuario(
            @PathVariable Long id,
            @RequestBody @Valid UsuarioRequestDTO usuarioDTO,
            @AuthenticationPrincipal Usuario usuarioAuth
    ) {
        if (usuarioAuth == null || (!usuarioAuth.getId().equals(id) && !usuarioAuth.getTipoUsuario().equals(TipoUsuario.ADMIN))) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Acesso negado.");
        }

        Optional<Usuario> optionalUsuario = repository.findById(id);

        if (optionalUsuario.isPresent()) {
            Usuario usuario = optionalUsuario.get();
            usuario.setNome(usuarioDTO.getNome());
            usuario.setEmail(usuarioDTO.getEmail());
            usuario.setSenha(encoder.encode(usuarioDTO.getSenha()));

            if (usuarioAuth.getTipoUsuario().equals(TipoUsuario.ADMIN)) {
                try {
                    usuario.setTipoUsuario(usuarioDTO.getTipoUsuario());
                } catch (IllegalArgumentException e) {
                    return ResponseEntity
                            .status(HttpStatus.BAD_REQUEST)
                            .body("Tipo de usuário inválido.");
                }
            }

            Usuario updatedUsuario = repository.save(usuario);
            return ResponseEntity.ok(new UsuarioResponseDTO(updatedUsuario));
        }

        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deletar usuário", description = "Remove um usuário do sistema (ADMIN ou dono do perfil)")
    @CacheEvict(value = "usuarios", allEntries = true)
    public ResponseEntity<?> deleteUsuario(@PathVariable Long id, @AuthenticationPrincipal Usuario usuarioAuth) {
        if (usuarioAuth == null || (!usuarioAuth.getId().equals(id) && !usuarioAuth.getTipoUsuario().equals(TipoUsuario.ADMIN))) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Acesso negado.");
        }

        if (repository.existsById(id)) {
            repository.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
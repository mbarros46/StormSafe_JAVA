
package br.com.fiap.stormsafe.controller;

import br.com.fiap.stormsafe.model.LogEvacuacao;
import br.com.fiap.stormsafe.service.LogEvacuacaoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/evacuacoes")
public class LogEvacuacaoController {

    @Autowired
    private LogEvacuacaoService service;

    @GetMapping
    public Page<LogEvacuacao> listar(Pageable pageable) {
        return service.listar(pageable);
    }

    @PostMapping
    public ResponseEntity<LogEvacuacao> cadastrar(@RequestBody @Valid LogEvacuacao entidade) {
        return ResponseEntity.ok(service.salvar(entidade));
    }

    @GetMapping("/{id}")
    public ResponseEntity<LogEvacuacao> buscar(@PathVariable Long id) {
        var obj = service.buscarPorId(id);
        return (obj != null) ? ResponseEntity.ok(obj) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> remover(@PathVariable Long id) {
        service.deletar(id);
        return ResponseEntity.noContent().build();
    }
}

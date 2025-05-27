
package br.com.fiap.stormsafe.controller;

import br.com.fiap.stormsafe.model.Regiao;
import br.com.fiap.stormsafe.service.RegiaoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/regioes")
public class RegiaoController {

    @Autowired
    private RegiaoService service;

    @GetMapping
    public Page<Regiao> listar(Pageable pageable) {
        return service.listar(pageable);
    }

    @PostMapping
    public ResponseEntity<Regiao> cadastrar(@RequestBody @Valid Regiao entidade) {
        return ResponseEntity.ok(service.salvar(entidade));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Regiao> buscar(@PathVariable Long id) {
        var obj = service.buscarPorId(id);
        return (obj != null) ? ResponseEntity.ok(obj) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> remover(@PathVariable Long id) {
        service.deletar(id);
        return ResponseEntity.noContent().build();
    }
}

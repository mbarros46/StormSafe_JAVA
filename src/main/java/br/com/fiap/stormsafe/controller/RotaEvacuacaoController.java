
package br.com.fiap.stormsafe.controller;

import br.com.fiap.stormsafe.model.RotaEvacuacao;
import br.com.fiap.stormsafe.service.RotaEvacuacaoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/rotas")
public class RotaEvacuacaoController {

    @Autowired
    private RotaEvacuacaoService service;

    @GetMapping
    public Page<RotaEvacuacao> listar(Pageable pageable) {
        return service.listar(pageable);
    }

    @PostMapping
    public ResponseEntity<RotaEvacuacao> cadastrar(@RequestBody @Valid RotaEvacuacao entidade) {
        return ResponseEntity.ok(service.salvar(entidade));
    }

    @GetMapping("/{id}")
    public ResponseEntity<RotaEvacuacao> buscar(@PathVariable Long id) {
        var obj = service.buscarPorId(id);
        return (obj != null) ? ResponseEntity.ok(obj) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> remover(@PathVariable Long id) {
        service.deletar(id);
        return ResponseEntity.noContent().build();
    }
}


package br.com.fiap.stormsafe.controller;

import br.com.fiap.stormsafe.model.LeituraSensor;
import br.com.fiap.stormsafe.service.LeituraSensorService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/leituras")
public class LeituraSensorController {

    @Autowired
    private LeituraSensorService service;

    @GetMapping
    public Page<LeituraSensor> listar(Pageable pageable) {
        return service.listar(pageable);
    }

    @PostMapping
    public ResponseEntity<LeituraSensor> cadastrar(@RequestBody @Valid LeituraSensor entidade) {
        return ResponseEntity.ok(service.salvar(entidade));
    }

    @GetMapping("/{id}")
    public ResponseEntity<LeituraSensor> buscar(@PathVariable Long id) {
        var obj = service.buscarPorId(id);
        return (obj != null) ? ResponseEntity.ok(obj) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> remover(@PathVariable Long id) {
        service.deletar(id);
        return ResponseEntity.noContent().build();
    }
}

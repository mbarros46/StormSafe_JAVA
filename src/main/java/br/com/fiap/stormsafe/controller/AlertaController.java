
package br.com.fiap.stormsafe.controller;

import br.com.fiap.stormsafe.model.Alerta;
import br.com.fiap.stormsafe.service.AlertaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/alertas")
public class AlertaController {

    @Autowired
    private AlertaService service;

    @GetMapping
    public Page<Alerta> listar(Pageable pageable) {
        return service.listar(pageable);
    }

    @PostMapping
    public ResponseEntity<Alerta> cadastrar(@RequestBody @Valid Alerta entidade) {
        return ResponseEntity.ok(service.salvar(entidade));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Alerta> buscar(@PathVariable Long id) {
        var obj = service.buscarPorId(id);
        return (obj != null) ? ResponseEntity.ok(obj) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> remover(@PathVariable Long id) {
        service.deletar(id);
        return ResponseEntity.noContent().build();
    }
}

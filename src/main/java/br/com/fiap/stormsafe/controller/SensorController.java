
package br.com.fiap.stormsafe.controller;

import br.com.fiap.stormsafe.model.Sensor;
import br.com.fiap.stormsafe.service.SensorService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/sensores")
public class SensorController {

    @Autowired
    private SensorService service;

    @GetMapping
    public Page<Sensor> listar(Pageable pageable) {
        return service.listar(pageable);
    }

    @PostMapping
    public ResponseEntity<Sensor> cadastrar(@RequestBody @Valid Sensor entidade) {
        return ResponseEntity.ok(service.salvar(entidade));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Sensor> buscar(@PathVariable Long id) {
        var obj = service.buscarPorId(id);
        return (obj != null) ? ResponseEntity.ok(obj) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> remover(@PathVariable Long id) {
        service.deletar(id);
        return ResponseEntity.noContent().build();
    }
}

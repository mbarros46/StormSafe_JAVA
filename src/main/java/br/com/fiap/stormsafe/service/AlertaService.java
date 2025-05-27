
package br.com.fiap.stormsafe.service;

import br.com.fiap.stormsafe.model.Alerta;
import br.com.fiap.stormsafe.repository.AlertaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AlertaService {

    @Autowired
    private AlertaRepository repository;

    public Page<Alerta> listar(Pageable pageable) {
        return repository.findAll(pageable);
    }

    public Alerta salvar(Alerta entidade) {
        return repository.save(entidade);
    }

    public Alerta buscarPorId(Long id) {
        return repository.findById(id).orElse(null);
    }

    public void deletar(Long id) {
        repository.deleteById(id);
    }
}

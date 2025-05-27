
package br.com.fiap.stormsafe.service;

import br.com.fiap.stormsafe.model.LeituraSensor;
import br.com.fiap.stormsafe.repository.LeituraSensorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LeituraSensorService {

    @Autowired
    private LeituraSensorRepository repository;

    public Page<LeituraSensor> listar(Pageable pageable) {
        return repository.findAll(pageable);
    }

    public LeituraSensor salvar(LeituraSensor entidade) {
        return repository.save(entidade);
    }

    public LeituraSensor buscarPorId(Long id) {
        return repository.findById(id).orElse(null);
    }

    public void deletar(Long id) {
        repository.deleteById(id);
    }
}

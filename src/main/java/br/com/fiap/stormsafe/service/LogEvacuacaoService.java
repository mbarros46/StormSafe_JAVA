
package br.com.fiap.stormsafe.service;

import br.com.fiap.stormsafe.model.LogEvacuacao;
import br.com.fiap.stormsafe.repository.LogEvacuacaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LogEvacuacaoService {

    @Autowired
    private LogEvacuacaoRepository repository;

    public Page<LogEvacuacao> listar(Pageable pageable) {
        return repository.findAll(pageable);
    }

    public LogEvacuacao salvar(LogEvacuacao entidade) {
        return repository.save(entidade);
    }

    public LogEvacuacao buscarPorId(Long id) {
        return repository.findById(id).orElse(null);
    }

    public void deletar(Long id) {
        repository.deleteById(id);
    }
}

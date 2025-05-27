
package br.com.fiap.stormsafe.service;

import br.com.fiap.stormsafe.model.RotaEvacuacao;
import br.com.fiap.stormsafe.repository.RotaEvacuacaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RotaEvacuacaoService {

    @Autowired
    private RotaEvacuacaoRepository repository;

    public Page<RotaEvacuacao> listar(Pageable pageable) {
        return repository.findAll(pageable);
    }

    public RotaEvacuacao salvar(RotaEvacuacao entidade) {
        return repository.save(entidade);
    }

    public RotaEvacuacao buscarPorId(Long id) {
        return repository.findById(id).orElse(null);
    }

    public void deletar(Long id) {
        repository.deleteById(id);
    }
}

package br.com.fiap.stormsafe.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import br.com.fiap.stormsafe.model.LogEvacuacao;

public interface LogEvacuacaoRepository extends JpaRepository<LogEvacuacao, Long>, JpaSpecificationExecutor<LogEvacuacao> {
}

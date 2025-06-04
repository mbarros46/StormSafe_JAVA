package br.com.fiap.stormsafe.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import br.com.fiap.stormsafe.model.RotaEvacuacao;

@Repository
public interface RotaEvacuacaoRepository extends JpaRepository<RotaEvacuacao, Long>, JpaSpecificationExecutor<RotaEvacuacao> {
}

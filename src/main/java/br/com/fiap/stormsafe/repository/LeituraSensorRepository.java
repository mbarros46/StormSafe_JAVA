package br.com.fiap.stormsafe.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import br.com.fiap.stormsafe.model.LeituraSensor;

@Repository
public interface LeituraSensorRepository extends JpaRepository<LeituraSensor, Long>, JpaSpecificationExecutor<LeituraSensor> {
}

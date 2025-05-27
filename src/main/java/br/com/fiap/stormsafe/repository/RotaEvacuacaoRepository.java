
package br.com.fiap.stormsafe.repository;

import br.com.fiap.stormsafe.model.RotaEvacuacao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RotaEvacuacaoRepository extends JpaRepository<RotaEvacuacao, Long> {

}

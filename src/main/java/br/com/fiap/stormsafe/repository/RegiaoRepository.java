
package br.com.fiap.stormsafe.repository;

import br.com.fiap.stormsafe.model.Regiao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RegiaoRepository extends JpaRepository<Regiao, Long> {

}

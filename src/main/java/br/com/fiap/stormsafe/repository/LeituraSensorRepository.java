
package br.com.fiap.stormsafe.repository;

import br.com.fiap.stormsafe.model.LeituraSensor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LeituraSensorRepository extends JpaRepository<LeituraSensor, Long> {

}

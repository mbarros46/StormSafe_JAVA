
package br.com.fiap.stormsafe.repository;

import br.com.fiap.stormsafe.model.Alerta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AlertaRepository extends JpaRepository<Alerta, Long> {

}

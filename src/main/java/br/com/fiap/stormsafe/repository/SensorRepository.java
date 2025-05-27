
package br.com.fiap.stormsafe.repository;

import br.com.fiap.stormsafe.model.Sensor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SensorRepository extends JpaRepository<Sensor, Long> {

}

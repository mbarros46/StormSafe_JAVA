package br.com.fiap.stormsafe.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import br.com.fiap.stormsafe.model.Sensor;

@Repository
public interface SensorRepository extends JpaRepository<Sensor, Long>, JpaSpecificationExecutor<Sensor> {

    // Define any custom query methods if needed
    // For example, to find sensors by a specific attribute:
    // List<Sensor> findByAttribute(String attribute);
    
}

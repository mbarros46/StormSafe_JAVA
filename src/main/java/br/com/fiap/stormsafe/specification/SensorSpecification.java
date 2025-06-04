package br.com.fiap.stormsafe.specification;

import org.springframework.data.jpa.domain.Specification;

import br.com.fiap.stormsafe.controller.SensorController;
import br.com.fiap.stormsafe.model.Sensor;
import jakarta.persistence.criteria.Predicate;

public class SensorSpecification {
    
    public static Specification<Sensor> withFilters(SensorController.SensorFilters filters) {
        return (root, query, builder) -> {
            Predicate predicates = builder.conjunction();

            if (filters.tipoSensor() != null && !filters.tipoSensor().isBlank()) {
                predicates = builder.and(predicates,
                        builder.equal(root.get("tipoSensor"), filters.tipoSensor()));
            }

            if (filters.localizacao() != null && !filters.localizacao().isBlank()) {
                predicates = builder.and(predicates,
                        builder.like(builder.lower(root.get("localizacao")), "%" + filters.localizacao().toLowerCase() + "%"));
            }

            return predicates;
        };
    }
}
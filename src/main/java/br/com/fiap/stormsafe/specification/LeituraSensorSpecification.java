package br.com.fiap.stormsafe.specification;

import org.springframework.data.jpa.domain.Specification;

import br.com.fiap.stormsafe.controller.LeituraSensorController;
import br.com.fiap.stormsafe.model.LeituraSensor;
import jakarta.persistence.criteria.Predicate;

public class LeituraSensorSpecification {
    
public static Specification<LeituraSensor> withFilters(LeituraSensorController.LeituraSensorFilters filters) {
        return (root, query, builder) -> {
            Predicate predicates = builder.conjunction();

            if (filters.sensorId() != null) {
                predicates = builder.and(predicates,
                        builder.equal(root.get("sensor").get("id"), filters.sensorId()));
            }

            return predicates;
        };
    }
}
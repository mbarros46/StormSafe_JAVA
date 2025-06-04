package br.com.fiap.stormsafe.specification;

import org.springframework.data.jpa.domain.Specification;

import br.com.fiap.stormsafe.controller.AlertaController;
import br.com.fiap.stormsafe.model.Alerta;
import jakarta.persistence.criteria.Predicate;

public class AlertaSpecification {
    
public static Specification<Alerta> withFilters(AlertaController.AlertaFilters filters) {
        return (root, query, builder) -> {
            Predicate predicates = builder.conjunction();

            if (filters.regiaoId() != null) {
                predicates = builder.and(predicates,
                        builder.equal(root.get("regiao").get("id"), filters.regiaoId()));
            }

            if (filters.nivelCriticidade() != null && !filters.nivelCriticidade().isBlank()) {
                predicates = builder.and(predicates,
                        builder.equal(root.get("nivelCriticidade"), filters.nivelCriticidade()));
            }

            return predicates;
        };
    }
}
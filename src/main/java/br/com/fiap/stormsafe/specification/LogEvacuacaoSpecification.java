package br.com.fiap.stormsafe.specification;

import org.springframework.data.jpa.domain.Specification;

import br.com.fiap.stormsafe.controller.LogEvacuacaoController;
import br.com.fiap.stormsafe.model.LogEvacuacao;
import jakarta.persistence.criteria.Predicate;

public class LogEvacuacaoSpecification {
    
public static Specification<LogEvacuacao> withFilters(LogEvacuacaoController.LogEvacuacaoFilters filters) {
        return (root, query, builder) -> {
            Predicate predicates = builder.conjunction();

            if (filters.usuarioId() != null) {
                predicates = builder.and(predicates,
                        builder.equal(root.get("usuario").get("id"), filters.usuarioId()));
            }

            if (filters.regiaoId() != null) {
                predicates = builder.and(predicates,
                        builder.equal(root.get("regiao").get("id"), filters.regiaoId()));
            }

            return predicates;
        };
    }
}
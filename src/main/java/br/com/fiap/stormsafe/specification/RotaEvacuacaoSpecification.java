package br.com.fiap.stormsafe.specification;

import org.springframework.data.jpa.domain.Specification;

import br.com.fiap.stormsafe.controller.RotaEvacuacaoController;
import br.com.fiap.stormsafe.model.RotaEvacuacao;
import jakarta.persistence.criteria.Predicate;

public class RotaEvacuacaoSpecification {
    
public static Specification<RotaEvacuacao> withFilters(RotaEvacuacaoController.RotaEvacuacaoFilters filters) {
        return (root, query, builder) -> {
            Predicate predicates = builder.conjunction();

            if (filters.descricao() != null && !filters.descricao().isBlank()) {
                predicates = builder.and(predicates,
                        builder.like(builder.lower(root.get("descricao")), "%" + filters.descricao().toLowerCase() + "%"));
            }

            return predicates;
        };
    }
}

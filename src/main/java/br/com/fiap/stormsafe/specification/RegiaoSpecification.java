package br.com.fiap.stormsafe.specification;


import br.com.fiap.stormsafe.model.Regiao;
import org.springframework.data.jpa.domain.Specification;

public class RegiaoSpecification {

    public static Specification<Regiao> withFilters(RegiaoFilters filters) {
        return (root, query, builder) -> {
            var predicates = builder.conjunction(); // Cria um predicado para os filtros
            
            // Se o nome não for nulo ou vazio, adiciona um filtro de like no nome
            if (filters.nome() != null && !filters.nome().isEmpty()) {
                predicates.getExpressions().add(
                        builder.like(builder.lower(root.get("nome")), "%" + filters.nome().toLowerCase() + "%")
                );
            }

            return predicates;
        };
    }
}
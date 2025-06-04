package br.com.fiap.stormsafe.specification;

import org.springframework.data.jpa.domain.Specification;

import br.com.fiap.stormsafe.controller.UsuarioController;
import br.com.fiap.stormsafe.model.Usuario;
import jakarta.persistence.criteria.Predicate;

public class UsuarioSpecification {
    
    public static Specification<Usuario> withFilters(UsuarioController.UsuarioFilters filters) {
        return (root, query, builder) -> {
            Predicate predicates = builder.conjunction();

            if (filters.nome() != null && !filters.nome().isBlank()) {
                predicates = builder.and(predicates,
                        builder.like(builder.lower(root.get("nome")), "%" + filters.nome().toLowerCase() + "%"));
            }

            if (filters.email() != null && !filters.email().isBlank()) {
                predicates = builder.and(predicates,
                        builder.like(builder.lower(root.get("email")), "%" + filters.email().toLowerCase() + "%"));
            }

            return predicates;
        };
    }
}

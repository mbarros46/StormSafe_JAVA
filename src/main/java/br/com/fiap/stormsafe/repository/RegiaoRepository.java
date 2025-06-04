package br.com.fiap.stormsafe.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import br.com.fiap.stormsafe.model.Regiao;

@Repository
public interface RegiaoRepository extends JpaRepository<Regiao, Long>, JpaSpecificationExecutor<Regiao> 
{
    
}

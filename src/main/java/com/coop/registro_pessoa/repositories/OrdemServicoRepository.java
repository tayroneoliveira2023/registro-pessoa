package com.coop.registro_pessoa.repositories;

import com.coop.registro_pessoa.entities.OrdemServico;
import com.coop.registro_pessoa.entities.RegistroPessoa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrdemServicoRepository extends JpaRepository<OrdemServico, Long> {

    boolean existsByInstrutorAndEmAbertoIsTrue(RegistroPessoa instrutor);
}
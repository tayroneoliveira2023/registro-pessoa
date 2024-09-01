package com.coop.registro_pessoa.repositories;

import com.coop.registro_pessoa.entities.RegistroPessoa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface RegistroPessoaRepository extends JpaRepository<RegistroPessoa, Long> {

    @Query("SELECT rp FROM RegistroPessoa rp " +
            "JOIN FETCH rp.papeis " +
            "WHERE rp.id = :id AND rp.deletado = false")
    RegistroPessoa findByIdAndAndDeletadoIsFalse(Long id);

}
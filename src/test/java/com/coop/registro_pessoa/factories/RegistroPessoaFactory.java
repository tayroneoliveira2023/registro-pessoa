package com.coop.registro_pessoa.factories;

import com.coop.registro_pessoa.entities.RegistroPessoa;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
@Profile("test")
@RequiredArgsConstructor
public class RegistroPessoaFactory extends DataTestFactory<RegistroPessoa, Long> {

    private final PapelFactory papelFactory;

    public RegistroPessoa novoInstrutor() {
        RegistroPessoa instrutor = nova();
        instrutor.setPapeis(Set.of(papelFactory.getInstrutor()));
        return salvarOuAtualizar(instrutor);
    }

    public RegistroPessoa novoDeletante() {
        RegistroPessoa deletante = nova();
        deletante.setPapeis(Set.of(papelFactory.getEstadual(), papelFactory.getInstrutor(), papelFactory.getMunicipal()));
        return salvarOuAtualizar(deletante);
    }

    public void delete() {
        repository.deleteAll();
        papelFactory.delete();
    }
}
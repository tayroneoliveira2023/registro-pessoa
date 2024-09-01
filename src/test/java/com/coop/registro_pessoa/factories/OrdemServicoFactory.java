package com.coop.registro_pessoa.factories;

import com.coop.registro_pessoa.entities.OrdemServico;
import com.coop.registro_pessoa.entities.RegistroPessoa;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile("test")
public class OrdemServicoFactory extends DataTestFactory<OrdemServico, Long> {

    public OrdemServico novaEmAberto(RegistroPessoa instrutor) {
        OrdemServico ordemServico = nova();
        ordemServico.setInstrutor(instrutor);
        ordemServico.setEmAberto(true);
        return salvarOuAtualizar(ordemServico);
    }

    public OrdemServico novaFechada(RegistroPessoa instrutor) {
        OrdemServico ordemServico = nova();
        ordemServico.setInstrutor(instrutor);
        ordemServico.setEmAberto(false);
        return salvarOuAtualizar(ordemServico);
    }
}

package com.coop.registro_pessoa.services;

import com.coop.registro_pessoa.entities.RegistroPessoa;
import com.coop.registro_pessoa.factories.OrdemServicoFactory;
import com.coop.registro_pessoa.factories.RegistroPessoaFactory;
import net.thucydides.core.annotations.Step;

import static org.junit.jupiter.api.Assertions.*;

public class DeletarInstrutorSteps {

    private RegistroPessoa deletante;
    private RegistroPessoa instrutor;
    private Exception exception;


    @Step("Dado que sou um gestor com papel {0}")
    public void dado_que_sou_um_gestor_com_papel(String papel, RegistroPessoaFactory registroPessoaFactory) {
        switch (papel) {
            case "ESTADUAL":
                deletante = registroPessoaFactory.novoDeletante();
                break;
            case "INSTRUTOR":
                deletante = registroPessoaFactory.novoInstrutor();
                break;
            default:
                throw new IllegalArgumentException("Papel não suportado: " + papel);
        }
    }

    @Step("Quando existe um instrutor sem ordens de serviço em aberto")
    public void quando_existe_um_instrutor_sem_ordens_de_servico_em_aberto(RegistroPessoaFactory registroPessoaFactory, OrdemServicoFactory ordemServicoFactory) {
        instrutor = registroPessoaFactory.novoInstrutor();
        ordemServicoFactory.novaFechada(instrutor);
    }

    @Step("Quando existe um instrutor com ordens de serviço em aberto")
    public void quando_existe_um_instrutor_com_ordens_de_servico_em_aberto(RegistroPessoaFactory registroPessoaFactory, OrdemServicoFactory ordemServicoFactory) {
        instrutor = registroPessoaFactory.novoInstrutor();
        ordemServicoFactory.novaEmAberto(instrutor);
    }

    @Step("E eu tento deletar o instrutor")
    public void e_eu_tento_deletar_o_instrutor(RegistroPessoaService registroPessoaService) {
        try {
            registroPessoaService.deletarInstrutor(deletante.getId(), instrutor.getId());
        } catch (Exception e) {
            exception = e;
        }
    }

    @Step("Então o instrutor deve ser deletado com sucesso")
    public void entao_o_instrutor_deve_ser_deletado_com_sucesso(RegistroPessoaService registroPessoaService) {
        instrutor = registroPessoaService.buscarPorId(instrutor.getId());
        assertNull(instrutor);
    }

    @Step("Então deve ocorrer um erro indicando que o instrutor possui ordens de serviço em aberto")
    public void entao_deve_ocorrer_um_erro_indicando_que_o_instrutor_possui_ordens_de_servico_em_aberto() {
        assertNotNull(exception);
        assertTrue(exception instanceof IllegalStateException);
        assertEquals("Instrutor possui ordens de serviço em aberto", exception.getMessage());
    }

    @Step("Então deve ocorrer um erro indicando que o deletante não tem permissão para deletar o instrutor")
    public void entao_deve_ocorrer_um_erro_indicando_que_o_deletante_nao_tem_permissao_para_deletar_o_instrutor() {
        assertNotNull(exception);
        assertTrue(exception instanceof IllegalArgumentException);
        assertEquals("O deletante não tem permissão para deletar o instrutor", exception.getMessage());
    }
}
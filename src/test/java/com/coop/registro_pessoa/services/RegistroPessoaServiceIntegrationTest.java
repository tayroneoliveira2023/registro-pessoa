package com.coop.registro_pessoa.services;

import com.coop.registro_pessoa.factories.OrdemServicoFactory;
import com.coop.registro_pessoa.factories.RegistroPessoaFactory;
import net.serenitybdd.junit5.SerenityJUnit5Extension;
import net.thucydides.core.annotations.Steps;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ExtendWith({SerenityJUnit5Extension.class, MockitoExtension.class})
@ContextConfiguration
@ActiveProfiles(value = "test")
class RegistroPessoaServiceIntegrationTest {

    @Autowired
    private RegistroPessoaService registroPessoaService;
    @Autowired
    private RegistroPessoaFactory registroPessoaFactory;
    @Autowired
    private OrdemServicoFactory ordemServicoFactory;

    @Steps
    private DeletarInstrutorSteps deletarInstrutorSteps;

    @BeforeEach
    void setUp() {
        limparBaseTestes();
    }

    @AfterEach
    void tearDown() {
        limparBaseTestes();
    }

    @Test
    void deve_deletar_com_sucesso_com_deletante_correto() {
        deletarInstrutorSteps.dado_que_sou_um_gestor_com_papel("ESTADUAL", registroPessoaFactory);
        deletarInstrutorSteps.quando_existe_um_instrutor_sem_ordens_de_servico_em_aberto(registroPessoaFactory, ordemServicoFactory);
        deletarInstrutorSteps.e_eu_tento_deletar_o_instrutor(registroPessoaService);
        deletarInstrutorSteps.entao_o_instrutor_deve_ser_deletado_com_sucesso(registroPessoaService);
    }

    @Test
    void deve_nao_deletar_pois_instrutor_tem_os_aberta() {
        deletarInstrutorSteps.dado_que_sou_um_gestor_com_papel("ESTADUAL", registroPessoaFactory);
        deletarInstrutorSteps.quando_existe_um_instrutor_com_ordens_de_servico_em_aberto(registroPessoaFactory, ordemServicoFactory);
        deletarInstrutorSteps.e_eu_tento_deletar_o_instrutor(registroPessoaService);
        deletarInstrutorSteps.entao_deve_ocorrer_um_erro_indicando_que_o_instrutor_possui_ordens_de_servico_em_aberto();
    }

    @Test
    void deve_nao_deletar_pois_deletante_nao_tem_permissao() {
        deletarInstrutorSteps.dado_que_sou_um_gestor_com_papel("INSTRUTOR", registroPessoaFactory);
        deletarInstrutorSteps.quando_existe_um_instrutor_sem_ordens_de_servico_em_aberto(registroPessoaFactory, ordemServicoFactory);
        deletarInstrutorSteps.e_eu_tento_deletar_o_instrutor(registroPessoaService);
        deletarInstrutorSteps.entao_deve_ocorrer_um_erro_indicando_que_o_deletante_nao_tem_permissao_para_deletar_o_instrutor();
    }

    private void limparBaseTestes() {
        this.ordemServicoFactory.delete();
        this.registroPessoaFactory.delete();
    }
}
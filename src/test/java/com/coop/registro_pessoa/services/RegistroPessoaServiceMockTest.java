package com.coop.registro_pessoa.services;

import com.coop.registro_pessoa.entities.RegistroPessoa;
import com.coop.registro_pessoa.repositories.RegistroPessoaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

/**
 * Para provar a ineficácia de testes mockados:
 * 1. Rode essa classe de teste e perceba que dará sucesso;
 * 2. Rode a classe {@link RegistroPessoaServiceIntegrationTest} e perceba que dará sucesso;
 * 3. Após isso, vá até a classe {@link RegistroPessoaRepository} e comente o @Query do métod {@link RegistroPessoaRepository#findByIdAndAndDeletadoIsFalse(Long)};
 * 4. Perceba que o teste mockado continuará passando, mas o teste de integração falhará.
 *
 * @author: Tayrone Oliveira
 * @since: 2024-09-01
 */
@SpringBootTest
class RegistroPessoaServiceMockTest {

    @Mock
    private RegistroPessoaRepository registroPessoaRepository;

    @Mock
    private OrdemServicoService ordemServicoService;

    @InjectMocks
    private RegistroPessoaService registroPessoaService;

    private RegistroPessoa deletante;
    private RegistroPessoa instrutor;

    @BeforeEach
    void setUp() {
        deletante = mock(RegistroPessoa.class);
        instrutor = mock(RegistroPessoa.class);
    }

    @Test
    void deve_deletar_com_sucesso_com_deletante_correto() {
        when(registroPessoaRepository.findByIdAndAndDeletadoIsFalse(anyLong())).thenReturn(deletante, instrutor);
        when(deletante.podeDeletarInstrutor()).thenReturn(true);
        when(ordemServicoService.isInstrutorComOrdemServicoEmAberto(instrutor)).thenReturn(false);

        registroPessoaService.deletarInstrutor(deletante.getId(), instrutor.getId());

        verify(instrutor).setDeletado(true);
        verify(registroPessoaRepository).save(instrutor);
    }

    @Test
    void deve_nao_deletar_pois_instrutor_tem_os_aberta() {
        when(registroPessoaRepository.findByIdAndAndDeletadoIsFalse(anyLong())).thenReturn(deletante, instrutor);
        when(deletante.podeDeletarInstrutor()).thenReturn(true);
        when(ordemServicoService.isInstrutorComOrdemServicoEmAberto(instrutor)).thenReturn(true);

        Exception exception = assertThrows(IllegalStateException.class, () -> registroPessoaService.deletarInstrutor(deletante.getId(), instrutor.getId()));

        assertEquals("Instrutor possui ordens de serviço em aberto", exception.getMessage());
        verify(instrutor, never()).setDeletado(true);
        verify(registroPessoaRepository, never()).save(instrutor);
    }

    @Test
    void deve_nao_deletar_pois_deletante_nao_tem_permissao() {
        when(registroPessoaRepository.findByIdAndAndDeletadoIsFalse(anyLong())).thenReturn(deletante, instrutor);
        when(deletante.podeDeletarInstrutor()).thenReturn(false);

        Exception exception = assertThrows(IllegalArgumentException.class, () -> registroPessoaService.deletarInstrutor(deletante.getId(), instrutor.getId()));

        assertEquals("O deletante não tem permissão para deletar o instrutor", exception.getMessage());
        verify(instrutor, never()).setDeletado(true);
        verify(registroPessoaRepository, never()).save(instrutor);
    }
}
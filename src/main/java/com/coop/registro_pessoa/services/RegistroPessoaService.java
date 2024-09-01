package com.coop.registro_pessoa.services;

import com.coop.registro_pessoa.entities.RegistroPessoa;
import com.coop.registro_pessoa.repositories.RegistroPessoaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RegistroPessoaService {

    private final RegistroPessoaRepository registroPessoaRepository;

    private final OrdemServicoService ordemServicoService;

    public RegistroPessoa buscarPorId(Long id) {
        return registroPessoaRepository.findByIdAndAndDeletadoIsFalse(id);
    }

    public void deletarInstrutor(Long deletanteId, Long instrutorId) {
        RegistroPessoa deletante = registroPessoaRepository.findByIdAndAndDeletadoIsFalse(deletanteId);
        RegistroPessoa instrutor = registroPessoaRepository.findByIdAndAndDeletadoIsFalse(instrutorId);

        if (!deletante.podeDeletarInstrutor()) {
            throw new IllegalArgumentException("O deletante não tem permissão para deletar o instrutor");
        }

        if (ordemServicoService.isInstrutorComOrdemServicoEmAberto(instrutor)) {
            throw new IllegalStateException("Instrutor possui ordens de serviço em aberto");
        }

        instrutor.setDeletado(true);
        registroPessoaRepository.save(instrutor);
    }
}
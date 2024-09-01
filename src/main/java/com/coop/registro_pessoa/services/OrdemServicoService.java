package com.coop.registro_pessoa.services;

import com.coop.registro_pessoa.entities.RegistroPessoa;
import com.coop.registro_pessoa.repositories.OrdemServicoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrdemServicoService {

    private final OrdemServicoRepository ordemServicoRepository;

    public boolean isInstrutorComOrdemServicoEmAberto(RegistroPessoa instrutor) {
        return ordemServicoRepository.existsByInstrutorAndEmAbertoIsTrue(instrutor);
    }
}

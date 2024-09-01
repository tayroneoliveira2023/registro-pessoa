package com.coop.registro_pessoa.entities;

import com.coop.registro_pessoa.entities.enums.PapelTipo;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class RegistroPessoa {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;
    private Boolean deletado = false;

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(
            name = "registro_pessoa_papel",
            joinColumns = @JoinColumn(name = "registro_pessoa_id"),
            inverseJoinColumns = @JoinColumn(name = "papel_id")
    )
    private Set<Papel> papeis;

    public boolean isInstrutor() {
        return papeis.stream().anyMatch(papel -> papel.getTipo().equals(PapelTipo.INSTRUTOR));
    }

    public boolean isEstadual() {
        return papeis.stream().anyMatch(papel -> papel.getTipo().equals(PapelTipo.ESTADUAL));
    }

    public boolean isNacional() {
        return papeis.stream().anyMatch(papel -> papel.getTipo().equals(PapelTipo.NACIONAL));
    }

    public boolean isMunicipal() {
        return papeis.stream().anyMatch(papel -> papel.getTipo().equals(PapelTipo.MUNICIPAL));
    }

    public boolean podeDeletarInstrutor() {
        return isEstadual() || isNacional() || isMunicipal();
    }
}
package com.coop.registro_pessoa.entities;

import com.coop.registro_pessoa.entities.enums.PapelTipo;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Papel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private PapelTipo tipo;

    @ManyToOne
    @JoinColumn(name = "registro_pessoa_id")
    private RegistroPessoa registroPessoa;
}

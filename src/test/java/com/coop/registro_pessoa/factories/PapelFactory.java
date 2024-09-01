package com.coop.registro_pessoa.factories;

import com.coop.registro_pessoa.entities.Papel;
import com.coop.registro_pessoa.entities.enums.PapelTipo;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;

@Component
@Profile("test")
public class PapelFactory extends DataTestFactory<Papel, Long> {

    private List<Papel> papeis;
    private Papel instrutor;
    private Papel estadual;
    private Papel nacional;
    private Papel municipal;

    public Papel getInstrutor() {
        if (Objects.isNull(instrutor)) {
            instrutor = getPapeis().stream().filter(p -> p.getTipo().equals(PapelTipo.INSTRUTOR)).findFirst().orElse(null);
        }
        return instrutor;
    }

    public Papel getEstadual() {
        if (Objects.isNull(estadual)) {
            estadual = getPapeis().stream().filter(p -> p.getTipo().equals(PapelTipo.ESTADUAL)).findFirst().orElse(null);
        }
        return estadual;
    }

    public Papel getNacional() {
        if (Objects.isNull(nacional)) {
            nacional = getPapeis().stream().filter(p -> p.getTipo().equals(PapelTipo.NACIONAL)).findFirst().orElse(null);
        }
        return nacional;
    }

    public Papel getMunicipal() {
        if (Objects.isNull(municipal)) {
            municipal = getPapeis().stream().filter(p -> p.getTipo().equals(PapelTipo.MUNICIPAL)).findFirst().orElse(null);
        }
        return municipal;
    }

    public List<Papel> getPapeis() {
        if (Objects.isNull(papeis)) {
            papeis = gerarPapeis();
        }
        return papeis;
    }

    private List<Papel> gerarPapeis() {
        return List.of(salvarOuAtualizar(Papel.builder().id(1L).tipo(PapelTipo.INSTRUTOR).build()),
                salvarOuAtualizar(Papel.builder().id(2L).tipo(PapelTipo.ESTADUAL).build()),
                salvarOuAtualizar(Papel.builder().id(3L).tipo(PapelTipo.NACIONAL).build()),
                salvarOuAtualizar(Papel.builder().id(4L).tipo(PapelTipo.MUNICIPAL).build()));
    }
}

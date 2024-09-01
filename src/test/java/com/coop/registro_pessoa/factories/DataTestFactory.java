package com.coop.registro_pessoa.factories;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.ParameterizedType;

/**
 * Classe abstrata para criação de instâncias de testes
 *
 * @param <T1> Entidade
 * @param <T2> Id
 */
@Component
@Profile("test")
public abstract class DataTestFactory<T1, T2> {

    @Autowired
    protected JpaRepository<T1, T2> repository;

    @SuppressWarnings("unchecked")
    protected T1 createNewInstance() {
        try {
            return (T1) ((Class<?>) ((ParameterizedType) this.getClass()
                    .getGenericSuperclass()).getActualTypeArguments()[0]).getDeclaredConstructor().newInstance();
        } catch (InstantiationException | IllegalAccessException | NoSuchMethodException |
                 InvocationTargetException e) {
            throw new RuntimeException("Failed to create new instance", e);
        }
    }

    public void delete() {
        this.repository.deleteAll();
    }

    public T1 salvarOuAtualizar(T1 entity) {
        return this.repository.save(entity);
    }

    public T1 nova() {
        T1 newInstance = createNewInstance();
        customNewInstance(newInstance);
        return this.repository.save(newInstance);
    }

    protected void customNewInstance(T1 newInstance) {

    }
}

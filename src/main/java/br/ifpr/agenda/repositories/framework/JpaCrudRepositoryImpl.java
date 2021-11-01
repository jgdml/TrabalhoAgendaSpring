package br.ifpr.agenda.repositories.framework;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import javax.persistence.EntityManager;
import java.util.List;

@RequiredArgsConstructor
public class JpaCrudRepositoryImpl<T, Y> implements JpaCrudRepository<T, Y> {

    protected final EntityManager entityManager;

    @Override
    public Page<T> pageByFilter(Pageable pageable, Y filter) {
        return null;
    }

    @Override
    public List<T> findAllByFilter(Y filter) {
        return null;
    }

}
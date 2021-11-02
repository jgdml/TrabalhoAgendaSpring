package br.ifpr.agenda.repositories;

import br.ifpr.agenda.dominio.Contato;
import br.ifpr.agenda.dominio.FilterContato;
import br.ifpr.agenda.dominio.QContato;
import br.ifpr.agenda.dominio.QUsuario;
import br.ifpr.agenda.repositories.framework.JpaCrudRepositoryImpl;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.QueryResults;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.jpa.JPAQueryBase;
import com.querydsl.jpa.impl.JPAQuery;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
public class ContatoData extends JpaCrudRepositoryImpl<Contato, FilterContato> {

    public ContatoData(EntityManager entityManager) {
        super(entityManager);
    }

    @Override
    public Page<Contato> pageByFilter(Pageable pageable, FilterContato filter) {
        JPAQuery jpaQuery = new JPAQuery(entityManager);

        QContato entidade = QContato.contato;
        QUsuario usuario = QUsuario.usuario;
//        JPAQueryBase jpaQueryBase = jpaQuery.from(entidade);
//        jpaQueryBase.limit(pageable.getPageSize()).offset(pageable.getOffset());
        PathBuilder<Contato> pathBuilder = new PathBuilder<>(Contato.class, "contato");
        BooleanBuilder where = new BooleanBuilder();

//        where.and(entidade.active.isTrue());

        if (filter.getId() != null) {
            where.and(entidade.id.like("%" + filter.getId() + "%"));
        }
        if (filter.getId() != null && filter.getId() > 0) {
            where.and(entidade.id.eq(filter.getId()));
        }
        if (filter.getName() != null && !filter.getName().isBlank()) {
            where.and(entidade.nome.like("%" + filter.getName() + "%"));
        }

//        if (filter.getActive() != null) {
//            where.and(entidade.active.eq(filter.getActive()));
//        }

        JPAQueryBase jpaQueryBase = jpaQuery.from(entidade);
        jpaQueryBase.limit(pageable.getPageSize()).offset(pageable.getOffset());
        jpaQueryBase.innerJoin(entidade.usuario, usuario);
        jpaQueryBase.where(where);
        for (Sort.Order order : pageable.getSort()) {
            if (order.getDirection().equals(Sort.Direction.ASC)) {
                jpaQueryBase.orderBy(pathBuilder.getString(order.getProperty()).asc());
            } else {
                jpaQueryBase.orderBy(pathBuilder.getString(order.getProperty()).desc());
            }
        }

        QueryResults jpaQueryResults = jpaQueryBase.fetchResults();
        return new PageImpl<Contato>(jpaQueryResults.getResults(), pageable, jpaQueryResults.getTotal());
    }

    @Override
    public List<Contato> findAllByFilter(FilterContato filter) {
        JPAQuery jpaQuery = new JPAQuery(entityManager);

        QContato entidade = QContato.contato;
        JPAQueryBase jpaQueryBase = jpaQuery.from(entidade);

        PathBuilder<Contato> pathBuilder = new PathBuilder<>(Contato.class, "contato");
        BooleanBuilder where = new BooleanBuilder();

//        where.and(entidade.active.isTrue());

        if (filter.getId() != null) {
            where.and(entidade.id.like("%" + filter.getId() + "%"));
        }
        if (filter.getId() != null && filter.getId() > 0) {
            where.and(entidade.id.eq(filter.getId()));
        }
        if (filter.getName() != null && !filter.getName().isBlank()) {
            where.and(entidade.nome.like("%" + filter.getName() + "%"));
        }

//        if (filter.getActive() != null) {
//            where.and(entidade.active.eq(filter.getActive()));
//        }

        jpaQueryBase.where(where);

        QueryResults jpaQueryResults = jpaQueryBase.fetchResults();
        return jpaQueryResults.getResults();
    }

}

package ru.otus.spring.hw3.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.spring.hw3.models.Genre;

@Repository
@Transactional
public class GenreRepositoryImpl implements GenreRepository {
    @PersistenceContext
    private EntityManager em;

    @Override
    public int insert(Genre genre) {
        em.persist(genre);
        return genre.getId();
    }

    @Override
    public boolean update(Genre genre) {
        em.merge(genre);
        return true;
    }

    @Override
    public boolean deleteById(int id) {
        return em.createQuery("delete from Genre g where g.id = :id")
                .setParameter("id", id)
                .executeUpdate() > 0;
    }

    @Override
    public int count() {
        return em.createQuery("select count(*) from Genre g", Long.class).getSingleResult().intValue();
    }

    @Override
    public Genre getById(int id) {
        return em.find(Genre.class, id);
    }

    @Override
    public List<Genre> getAll() {
        return em.createQuery("select g from Genre g", Genre.class).getResultList();
    }
}

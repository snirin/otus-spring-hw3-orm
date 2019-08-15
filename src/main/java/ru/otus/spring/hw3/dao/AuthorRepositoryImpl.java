package ru.otus.spring.hw3.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.spring.hw3.models.Author;

@Repository
@Transactional
public class AuthorRepositoryImpl implements AuthorRepository {
    @PersistenceContext
    private EntityManager em;

    @Override
    public int insert(Author author) {
        em.persist(author);
        return author.getId();
    }

    @Override
    public boolean update(Author author) {
        return em.createQuery("update Author g set g.name = :name where g.id = :id")
                .setParameter("id", author.getId())
                .setParameter("name", author.getName())
                .executeUpdate() > 0;
    }

    @Override
    public boolean deleteById(int id) {
        return em.createQuery("delete from Author g where g.id = :id")
                .setParameter("id", id)
                .executeUpdate() > 0;
    }

    @Override
    public int count() {
        return em.createQuery("select count(*) from Author g", Long.class).getSingleResult().intValue();
    }

    @Override
    public Author getById(int id) {
        return em.find(Author.class, id);
    }

    @Override
    public List<Author> getAll() {
        return em.createQuery("select g from Author g", Author.class).getResultList();
    }
}

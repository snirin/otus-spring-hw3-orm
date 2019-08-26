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
        em.merge(author);
        return true;
    }

    @Override
    public boolean deleteById(int id) {
        return em.createQuery("delete from Author a where a.id = :id")
                .setParameter("id", id)
                .executeUpdate() > 0;
    }

    @Override
    public int count() {
        return em.createQuery("select count(*) from Author a", Long.class).getSingleResult().intValue();
    }

    @Override
    public Author getById(int id) {
        return em.find(Author.class, id);
    }

    @Override
    public List<Author> getAll() {
        return em.createQuery("select a from Author a", Author.class).getResultList();
    }
}

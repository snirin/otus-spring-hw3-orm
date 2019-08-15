package ru.otus.spring.hw3.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.spring.hw3.models.Author;
import ru.otus.spring.hw3.models.Book;
import ru.otus.spring.hw3.models.Genre;

@Repository
@Transactional
public class BookRepositoryImpl implements BookRepository {
    @PersistenceContext
    private EntityManager em;

    @Override
    public int insert(Book book) {
        em.persist(book);
        return book.getId();
    }

    @Override
    public boolean update(Book book) {
        return em.createQuery("update Book b set b.name = :name where b.id = :id")
                .setParameter("id", book.getId())
                .setParameter("name", book.getName())
                .executeUpdate() > 0;
    }

    @Override
    public boolean updateName(int id, String name) {
        return em.createQuery("update Book b set b.name = :name where b.id = :id")
                .setParameter("id", id)
                .setParameter("name", name)
                .executeUpdate() > 0;
    }

    @Override
    public boolean updateAuthor(int id, int authorId) {
        return em.createQuery("update Book b set b.author = :author where b.id = :id")
                .setParameter("id", id)
                .setParameter("author", new Author(authorId))
                .executeUpdate() > 0;
    }

    @Override
    public boolean updateGenre(int id, int genreId) {
        return em.createQuery("update Book b set b.genre = :genre where b.id = :id")
                .setParameter("id", id)
                .setParameter("genre", new Genre(genreId))
                .executeUpdate() > 0;
    }

    @Override
    public boolean deleteById(int id) {
        return em.createQuery("delete from Book b where b.id = :id")
                .setParameter("id", id)
                .executeUpdate() > 0;
    }

    @Override
    public int count() {
        return em.createQuery("select count(*) from Book b", Long.class).getSingleResult().intValue();
    }

    @Override
    public Book getById(int id) {
        return em.find(Book.class, id);
    }

    @Override
    public List<Book> getAll() {
        return em.createQuery("select b from Book b", Book.class).getResultList();
    }
}

package ru.otus.spring.hw3.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.spring.hw3.models.Book;
import ru.otus.spring.hw3.models.Comment;

@Repository
@Transactional
public class CommentRepositoryImpl implements CommentRepository {
    @PersistenceContext
    private EntityManager em;

    @Override
    public int insert(Comment comment) {
        em.persist(comment);
        return comment.getId();
    }

    @Override
    public boolean update(int id, String text) {
        return em.createQuery("update Comment c set c.text = :text where c.id = :id")
                .setParameter("id", id)
                .setParameter("text", text)
                .executeUpdate() > 0;
    }

    @Override
    public boolean deleteById(int id) {
        return em.createQuery("delete from Comment c where c.id = :id")
                .setParameter("id", id)
                .executeUpdate() > 0;
    }

    @Override
    public int count() {
        return em.createQuery("select count(*) from Comment c", Long.class).getSingleResult().intValue();
    }

    @Override
    public Comment getById(int id) {
        return em.find(Comment.class, id);
    }

    @Override
    public List<Comment> getByBookId(int bookId) {
        return em.createQuery("select c from Comment c where c.book = :book", Comment.class)
                .setParameter("book", new Book().withId(bookId))
                .getResultList();
    }

    @Override
    public List<Comment> getAll() {
        return em.createQuery("select c from Comment c", Comment.class).getResultList();
    }
}

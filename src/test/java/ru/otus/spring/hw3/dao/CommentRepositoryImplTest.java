package ru.otus.spring.hw3.dao;

import java.util.HashSet;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit4.SpringRunner;
import ru.otus.spring.hw3.models.Author;
import ru.otus.spring.hw3.models.Book;
import ru.otus.spring.hw3.models.Comment;
import ru.otus.spring.hw3.models.Genre;

import static java.util.Collections.singletonList;
import static junit.framework.TestCase.assertNull;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@Import(CommentRepositoryImpl.class)
@DataJpaTest
public class CommentRepositoryImplTest {

    private static final String AUTHOR_1 = "Author1";
    private static final String AUTHOR_2 = "Author2";

    private static final String GENRE_1 = "Genre1";
    private static final String GENRE_2 = "Genre2";

    private static final String BOOK_1 = "Book1";
    private static final String BOOK_2 = "Book2";

    private static final String COMMENT_1 = "Comment1";
    private static final String COMMENT_2 = "Comment2";

    @Autowired
    private TestEntityManager em;

    @Autowired
    private CommentRepository commentRepository;

    private Book book1;

    @Before
    public void setUp() {
        Author author1 = em.persistAndFlush(new Author(0, AUTHOR_1));
        Genre genre1 = em.persistAndFlush(new Genre(0, GENRE_1));
        book1 = em.persistAndFlush(new Book(0, BOOK_1, author1, genre1));
    }

    @Test
    public void insert() {
        int commentId = commentRepository.insert(new Comment(0, book1, COMMENT_1));
        Comment expected = new Comment(commentId, book1, COMMENT_1);
        Comment result = em.find(Comment.class, commentId);
        assertEquals(expected, result);
    }

    @Test
    public void update() {
        int commentId = em.persistAndFlush(new Comment(0, book1, COMMENT_1)).getId();
        em.clear();
        commentRepository.update(commentId, COMMENT_2);
        Comment result = em.find(Comment.class, commentId);
        Comment expected = new Comment(commentId, book1, COMMENT_2);
        assertEquals(expected, result);
    }

    @Test
    public void deleteById() {
        Comment comment = em.persistAndFlush(new Comment(0, book1, COMMENT_1));
        int commentId = comment.getId();
        em.detach(comment);

        comment = em.find(Comment.class, commentId);
        assertNotNull(comment);
        em.detach(comment);

        commentRepository.deleteById(commentId);
        assertNull(em.find(Comment.class, commentId));
    }

    @Test
    public void count() {
        int count1 = commentRepository.count();
        em.persistAndFlush(new Comment(0, book1, COMMENT_1));
        int count2 = commentRepository.count();
        assertEquals(count1 + 1, count2);
    }

    @Test
    public void getById() {
        Comment comment = em.persistAndFlush(new Comment(0, book1, COMMENT_1));
        em.clear();
        Comment result = commentRepository.getById(comment.getId());
        assertEquals(comment, result);
    }

    @Test
    public void getByBookId() {
        Comment comment = em.persistAndFlush(new Comment(0, book1, COMMENT_1));
        em.clear();
        List<Comment> comments = commentRepository.getByBookId(book1.getId());
        assertEquals(singletonList(comment), comments);
    }

    @Test
    public void getAll() {
        Comment comment = em.persistAndFlush(new Comment(0, book1, COMMENT_1));
        em.clear();
        List<Comment> result = commentRepository.getAll();
        assertTrue(new HashSet<>(result).contains(comment));
    }
}
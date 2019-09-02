package ru.otus.spring.hw3.dao;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.spring.hw3.models.Author;
import ru.otus.spring.hw3.models.Book;
import ru.otus.spring.hw3.models.Comment;
import ru.otus.spring.hw3.models.Genre;

import static junit.framework.TestCase.assertFalse;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@Import({AuthorRepositoryImpl.class, GenreRepositoryImpl.class, BookRepositoryImpl.class, CommentRepositoryImpl.class})
@DataJpaTest
@Transactional(propagation = Propagation.NOT_SUPPORTED)
public class CommentRepositoryImplTest {
    private static final String BOOK_1 = "Book1";
    private static final String AUTHOR_1 = "Author1";
    private static final String GENRE_1 = "Genre1";
    private static final String COMMENT_1 = "Comment1";
    private static final String COMMENT_2 = "Comment2";

    @Autowired
    private AuthorRepository authorRepository;

    @Autowired
    private GenreRepository genreRepository;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private CommentRepository commentRepository;

    private Author author1;
    private Genre genre1;
    private Book book;
    private int bookId1;
    private Comment comment;
    private int commentId;

    @Before
    public void setUp() {
        author1 = new Author(0, AUTHOR_1);
        authorRepository.insert(author1);

        genre1 = new Genre(0, GENRE_1);
        genreRepository.insert(genre1);

        book = new Book(0, BOOK_1, author1, genre1);
        bookId1 = bookRepository.insert(book);

        comment = new Comment(0, book, COMMENT_1);
        commentId = commentRepository.insert(comment);
    }

    @Test
    public void insert() {
        Comment expected = new Comment(commentId, book, COMMENT_1);
        Comment result = commentRepository.getById(commentId);
        assertEquals(expected, result);
    }

    @Test
    public void update() {
        commentRepository.update(commentId, COMMENT_2);
        Comment expected = new Comment(commentId, book, COMMENT_2);
        Comment result = commentRepository.getById(commentId);
        assertEquals(expected, result);
    }

    @Test
    public void deleteById() {
        assertNotNull(commentRepository.getById(commentId));
        commentRepository.deleteById(commentId);
        assertNull(commentRepository.getById(commentId));
    }

    @Test
    public void deleteByCascade() {
        assertNotNull(commentRepository.getById(commentId));
        bookRepository.deleteById(bookId1);
        assertNull(commentRepository.getById(commentId));
    }

    @Test
    public void count() {
        assertTrue(commentRepository.count() > 0);
    }

    @Test
    public void getAll() {
        assertFalse(commentRepository.getAll().isEmpty());
    }
}
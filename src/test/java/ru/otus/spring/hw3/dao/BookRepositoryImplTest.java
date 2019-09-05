package ru.otus.spring.hw3.dao;

import java.util.HashSet;
import java.util.List;

import junit.framework.TestCase;
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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@Import(BookRepositoryImpl.class)
@DataJpaTest
public class BookRepositoryImplTest {

    private static final String AUTHOR_1 = "Author1";
    private static final String AUTHOR_2 = "Author2";

    private static final String GENRE_1 = "Genre1";
    private static final String GENRE_2 = "Genre2";

    private static final String BOOK_1 = "Book1";
    private static final String BOOK_2 = "Book2";

    private static final String COMMENT_1 = "Comment1";

    @Autowired
    private TestEntityManager em;

    @Autowired
    private BookRepository bookRepository;

    private Author author1;
    private Genre genre1;

    @Before
    public void setUp() {
        author1 = em.persistAndFlush(new Author(0, AUTHOR_1));
        genre1 = em.persistAndFlush(new Genre(0, GENRE_1));
    }


    @Test
    public void insert() {
        int bookId = bookRepository.insert(new Book(0, BOOK_1, author1, genre1));
        Book expected = new Book(bookId, BOOK_1, author1, genre1);
        Book result = em.find(Book.class, bookId);
        assertEquals(expected, result);
    }

    @Test
    public void update() {
        int bookId = em.persistAndFlush(new Book(0, BOOK_1, author1, genre1)).getId();

        Author author1 = em.persistAndFlush(new Author(0, AUTHOR_1));
        Genre genre1 = em.persistAndFlush(new Genre(0, GENRE_1));
        Book book = new Book(bookId, BOOK_2, author1, genre1);

        bookRepository.update(book);
        Book result = em.find(Book.class, bookId);
        assertEquals(book, result);
    }

    @Test
    public void updateAuthor() {
        int bookId = em.persistAndFlush(new Book(0, BOOK_1, author1, genre1)).getId();
        em.clear();

        Author author2 = em.persistAndFlush(new Author(0, AUTHOR_2));
        bookRepository.updateAuthor(bookId, author2.getId());

        Book expected = new Book(bookId, BOOK_1, author2, genre1);
        Book result = em.find(Book.class, bookId);
        assertEquals(expected, result);
    }

        @Test
        public void updateGenre() {
            int bookId = em.persistAndFlush(new Book(0, BOOK_1, author1, genre1)).getId();
            em.clear();

            Genre genre2 = em.persistAndFlush(new Genre(0, GENRE_2));
            bookRepository.updateGenre(bookId, genre2.getId());

            Book expected = new Book(bookId, BOOK_1, author1, genre2);
            Book result = em.find(Book.class, bookId);
            assertEquals(expected, result);
        }

    @Test
    public void deleteById() {
        Book book = em.persistAndFlush(new Book(0, BOOK_1, author1, genre1));
        int bookId = book.getId();
        em.detach(book);

        book = em.find(Book.class, bookId);
        assertNotNull(book);
        em.detach(book);

        bookRepository.deleteById(bookId);
        assertNull(em.find(Book.class, bookId));
    }

    @Test
    public void deleteCommentsByCascade() {
        Book book = em.persistAndFlush(new Book(0, BOOK_1, author1, genre1));
        int bookId = book.getId();

        Comment comment = em.persistAndFlush(new Comment(0, book, COMMENT_1));
        int commentId = comment.getId();
        em.detach(comment);

        comment = em.find(Comment.class, commentId);
        assertNotNull(comment);
        em.detach(comment);

        bookRepository.deleteById(bookId);
        TestCase.assertNull(em.find(Comment.class, commentId));
    }

    @Test
    public void count() {
        int count1 = bookRepository.count();
        em.persistAndFlush(new Book(0, BOOK_1, author1, genre1));
        int count2 = bookRepository.count();
        assertEquals(count1 + 1, count2);
    }

    @Test
    public void getById() {
        Book book = em.persistAndFlush(new Book(0, BOOK_1, author1, genre1));
        em.clear();
        Book result = bookRepository.getById(book.getId());
        assertEquals(book, result);
    }

    @Test
    public void getAll() {
        Book book = em.persistAndFlush(new Book(0, BOOK_1, author1, genre1));
        em.clear();
        List<Book> result = bookRepository.getAll();
        assertTrue(new HashSet<>(result).contains(book));
    }
}

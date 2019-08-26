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
import ru.otus.spring.hw3.models.Genre;

import static java.util.Collections.emptyList;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@Import({AuthorRepositoryImpl.class, GenreRepositoryImpl.class, BookRepositoryImpl.class})
@DataJpaTest
@Transactional(propagation = Propagation.NOT_SUPPORTED)
public class BookRepositoryImplTest {
    private static final String BOOK_1 = "Book1";
    private static final String BOOK_2 = "Book2";
    private static final String AUTHOR_1 = "Author1";
    private static final String AUTHOR_2 = "Author2";
    private static final String GENRE_1 = "Genre1";
    private static final String GENRE_2 = "Genre2";

    @Autowired
    private AuthorRepository authorRepository;

    @Autowired
    private GenreRepository genreRepository;

    @Autowired
    private BookRepository bookRepository;

    private Author author1;
    private Author author2;
    private Genre genre1;
    private Genre genre2;
    private Book book;
    private int bookId1;

    @Before
    public void setUp() {
        author1 = new Author(0, AUTHOR_1);
        authorRepository.insert(author1);
        author2 = new Author(0, AUTHOR_2);
        authorRepository.insert(author2);

        genre1 = new Genre(0, GENRE_1);
        genreRepository.insert(genre1);
        genre2 = new Genre(0, GENRE_2);
        genreRepository.insert(genre2);

        book = new Book(0, BOOK_1, author1, genre1, emptyList());
        bookId1 = bookRepository.insert(book);
    }

    @Test
    public void insert() {
        Book expected = new Book(bookId1, BOOK_1, author1, genre1, emptyList());
        Book result = bookRepository.getById(bookId1);
        assertEquals(expected, result);
    }

    @Test
    public void update() {
        Book book = new Book(bookId1, BOOK_2, author2, genre2, emptyList());
        bookRepository.update(book);
        Book result = bookRepository.getById(bookId1);
        assertEquals(book, result);
    }

    @Test
    public void updateAuthor() {
        bookRepository.updateAuthor(book.getId(), author2.getId());
        Book expected = new Book(bookId1, BOOK_1, author2, genre1, emptyList());
        Book result = bookRepository.getById(bookId1);
        assertEquals(expected, result);
    }

    @Test
    public void updateGenre() {
        bookRepository.updateGenre(book.getId(), genre2.getId());
        Book expected = new Book(bookId1, BOOK_1, author1, genre2, emptyList());
        Book result = bookRepository.getById(bookId1);
        assertEquals(expected, result);
    }

    @Test
    public void deleteById() {
        assertNotNull(bookRepository.getById(bookId1));
        bookRepository.deleteById(bookId1);
        assertNull(bookRepository.getById(bookId1));
    }

    @Test
    public void count() {
        assertTrue(bookRepository.count() > 0);
    }

    @Test
    public void getAll() {
        assertFalse(bookRepository.getAll().isEmpty());
    }
}

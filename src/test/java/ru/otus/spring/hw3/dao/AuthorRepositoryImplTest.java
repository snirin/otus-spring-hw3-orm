package ru.otus.spring.hw3.dao;

import java.util.HashSet;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import ru.otus.spring.hw3.LibraryApplication;
import ru.otus.spring.hw3.models.Author;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

@SpringBootTest
@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {LibraryApplication.class})
@DataJpaTest
public class AuthorRepositoryImplTest {
    private static final String AUTHOR_1 = "Author1";
    private static final String AUTHOR_2 = "Author2";

    @Autowired
    private AuthorRepository authorRepository;

    private int authorId1;

    @Before
    public void setUp() {
        authorId1 = authorRepository.insert(new Author(0, AUTHOR_1));
    }

    @Test
    public void insert() {
        Author expected = new Author(authorId1, AUTHOR_1);
        Author result = authorRepository.getById(authorId1);
        assertEquals(expected, result);
    }

    @Test
    public void update() {
        Author author = new Author(authorId1, AUTHOR_2);
        authorRepository.update(author);
        Author result = authorRepository.getById(authorId1);
        assertEquals(author, result);
    }

    @Test
    public void deleteById() {
        Author author = new Author(authorId1, AUTHOR_1);
        HashSet<Author> authors = new HashSet<>(authorRepository.getAll());
        assertTrue(authors.contains(author));
        authorRepository.deleteById(authorId1);
        authors = new HashSet<>(authorRepository.getAll());
        assertFalse(authors.contains(author));
    }

    @Test
    public void count() {
        assertTrue(authorRepository.count() > 0);
    }

    @Test
    public void getAll() {
        assertFalse(authorRepository.getAll().isEmpty());
    }
//
//    @Test
//    public void insert() {
//    }
//
//    @Test
//    public void update() {
//    }
//
//    @Test
//    public void deleteById() {
//    }
//
//    @Test
//    public void count() {
//    }
//
//    @Test
//    public void getById() {
//    }
//
//    @Test
//    public void getAll() {
//    }
}


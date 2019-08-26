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

import static junit.framework.TestCase.assertFalse;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@Import(AuthorRepositoryImpl.class)
@DataJpaTest
@Transactional(propagation = Propagation.NOT_SUPPORTED)
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
        assertNotNull(authorRepository.getById(authorId1));
        authorRepository.deleteById(authorId1);
        assertNull(authorRepository.getById(authorId1));
    }

    @Test
    public void count() {
        assertTrue(authorRepository.count() > 0);
    }

    @Test
    public void getAll() {
        assertFalse(authorRepository.getAll().isEmpty());
    }
}


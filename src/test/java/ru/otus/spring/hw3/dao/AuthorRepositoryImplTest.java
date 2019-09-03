package ru.otus.spring.hw3.dao;

import java.util.HashSet;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit4.SpringRunner;
import ru.otus.spring.hw3.models.Author;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@Import(AuthorRepositoryImpl.class)
@DataJpaTest
public class AuthorRepositoryImplTest {

    private static final String AUTHOR_1 = "Author1";
    private static final String AUTHOR_2 = "Author2";

    @Autowired
    private TestEntityManager em;

    @Autowired
    private AuthorRepository authorRepository;

    @Test
    public void insert() {
        int authorId = authorRepository.insert(new Author(0, AUTHOR_1));
        Author expected = new Author(authorId, AUTHOR_1);
        Author result = em.find(Author.class, authorId);
        assertEquals(expected, result);
    }

    @Test
    public void update() {
        int authorId = em.persistAndFlush(new Author(0, AUTHOR_1)).getId();
        Author author = new Author(authorId, AUTHOR_2);
        authorRepository.update(author);
        Author result = em.find(Author.class, authorId);
        assertEquals(author, result);
    }

    @Test
    public void deleteById() {
        int authorId = em.persistAndFlush(new Author(0, AUTHOR_1)).getId();
        Author author = em.find(Author.class, authorId);
        assertNotNull(author);
        em.detach(author);
        authorRepository.deleteById(authorId);
        assertNull(em.find(Author.class, authorId));
    }

    @Test
    public void count() {
        int count1 = authorRepository.count();
        em.persistAndFlush(new Author(0, AUTHOR_1));
        int count2 = authorRepository.count();
        assertEquals(count1 + 1, count2);
    }

    @Test
    public void getById() {
        Author author = em.persistAndFlush(new Author(0, AUTHOR_1));
        Author result = authorRepository.getById(author.getId());
        assertEquals(author, result);
    }

    @Test
    public void getAll() {
        Author author = em.persistAndFlush(new Author(0, AUTHOR_1));
        List<Author> result = authorRepository.getAll();
        assertTrue(new HashSet<>(result).contains(author));
    }
}


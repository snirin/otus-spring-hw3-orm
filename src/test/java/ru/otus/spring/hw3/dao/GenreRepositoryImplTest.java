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
import ru.otus.spring.hw3.models.Genre;

import static junit.framework.TestCase.assertFalse;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@Import(GenreRepositoryImpl.class)
@DataJpaTest
@Transactional(propagation = Propagation.NOT_SUPPORTED)
public class GenreRepositoryImplTest {
    private static final String GENRE_1 = "Genre1";
    private static final String GENRE_2 = "Genre2";

    @Autowired
    private GenreRepository genreRepository;

    private int genreId1;

    @Before
    public void setUp() {
        genreId1 = genreRepository.insert(new Genre(0, GENRE_1));
    }

    @Test
    public void insert() {
        Genre expected = new Genre(genreId1, GENRE_1);
        Genre result = genreRepository.getById(genreId1);
        assertEquals(expected, result);
    }

    @Test
    public void update() {
        Genre genre = new Genre(genreId1, GENRE_2);
        genreRepository.update(genre);
        Genre result = genreRepository.getById(genreId1);
        assertEquals(genre, result);
    }

    @Test
    public void deleteById() {
        assertNotNull(genreRepository.getById(genreId1));
        genreRepository.deleteById(genreId1);
        assertNull(genreRepository.getById(genreId1));
    }

    @Test
    public void count() {
        assertTrue(genreRepository.count() > 0);
    }

    @Test
    public void getAll() {
        assertFalse(genreRepository.getAll().isEmpty());
    }
}


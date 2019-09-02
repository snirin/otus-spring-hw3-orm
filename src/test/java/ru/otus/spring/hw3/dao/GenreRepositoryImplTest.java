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
import ru.otus.spring.hw3.models.Genre;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@Import(GenreRepositoryImpl.class)
@DataJpaTest
//@Transactional(propagation = Propagation.NOT_SUPPORTED)
public class GenreRepositoryImplTest {

    private static final String GENRE_2 = "Genre2";
    private static final String GENRE_1 = "Genre1";

    @Autowired
    private TestEntityManager em;

    @Autowired
    private GenreRepository genreRepository;

    @Test
    public void insert() {
        int genreId = genreRepository.insert(new Genre(0, GENRE_1));
        Genre expected = new Genre(genreId, GENRE_1);
        Genre result = em.find(Genre.class, genreId);
        assertEquals(expected, result);
    }

    @Test
    public void update() {
        int genreId = em.persistAndFlush(new Genre(0, GENRE_1)).getId();
        Genre genre = new Genre(genreId, GENRE_2);
        genreRepository.update(genre);
        Genre result = em.find(Genre.class, genreId);
        assertEquals(genre, result);
    }

    @Test
    public void deleteById() {
        int genreId = em.persistAndFlush(new Genre(0, GENRE_1)).getId();
        assertNotNull(em.find(Genre.class, genreId));
        genreRepository.deleteById(genreId);
//        em.flush();
        assertNull(em.find(Genre.class, genreId));
    }

    @Test
    public void count() {
        int count1 = genreRepository.count();
        em.persistAndFlush(new Genre(0, GENRE_1));
        int count2 = genreRepository.count();
        assertEquals(count1 + 1, count2);
    }

    @Test
    public void getById() {
        Genre genre = em.persistAndFlush(new Genre(0, GENRE_1));
        Genre result = genreRepository.getById(genre.getId());
        assertEquals(genre, result);
    }

    @Test
    public void getAll() {
        Genre genre = em.persistAndFlush(new Genre(0, GENRE_1));
        List<Genre> result = genreRepository.getAll();
        assertTrue(new HashSet<>(result).contains(genre));
    }
}


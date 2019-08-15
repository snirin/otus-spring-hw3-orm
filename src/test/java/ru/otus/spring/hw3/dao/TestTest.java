package ru.otus.spring.hw3.dao;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import ru.otus.spring.hw3.LibraryApplication;
import ru.otus.spring.hw3.models.Author;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {LibraryApplication.class})
@DataJpaTest
public class TestTest {

    @Autowired
    private TestEntityManager em;

    @Test
    public void saveAndGet() {
        Author author = new Author(0, "Ivan");
        Author fromDb = em.persistAndFlush(author);
//        assertThat(fromDb.getId()).isNotZero();
//        assertThat(fromDb.getName()).isEqualTo(author.getName());
    }
}

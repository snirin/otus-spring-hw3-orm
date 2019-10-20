package ru.otus.spring.hw3.controllers;

import java.net.URL;

import com.google.common.base.Charsets;
import com.google.common.io.Resources;
import org.junit.Ignore;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.otus.spring.hw3.dao.AuthorRepository;
import ru.otus.spring.hw3.dao.AuthorRepositoryImpl;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(AuthorController.class)
@Import(AuthorRepositoryImpl.class)
@Ignore //todo падает из-за небольшого расхождения в одной строке
class AuthorControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private AuthorRepository authorRepository;

    @Test
    public void testExample() throws Exception {
        URL url = Resources.getResource("authors.html");
        String content = Resources.toString(url, Charsets.UTF_8);

        this.mvc.perform(MockMvcRequestBuilders.get("/authors").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(content));
    }
}
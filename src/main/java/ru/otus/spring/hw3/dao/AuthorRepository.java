package ru.otus.spring.hw3.dao;

import java.util.List;

import ru.otus.spring.hw3.models.Author;

public interface AuthorRepository {

    int insert(Author author);

    boolean update(Author author);

    boolean deleteById(int id);

    int count();

    Author getById(int id);

    List<Author> getAll();
}

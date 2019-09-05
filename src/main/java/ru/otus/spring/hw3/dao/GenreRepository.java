package ru.otus.spring.hw3.dao;

import java.util.List;

import ru.otus.spring.hw3.models.Genre;

public interface GenreRepository {

    int insert(Genre genre);

    boolean update(Genre genre);

    boolean deleteById(int id);

    int count();

    Genre getById(int id);

    List<Genre> getAll();
}

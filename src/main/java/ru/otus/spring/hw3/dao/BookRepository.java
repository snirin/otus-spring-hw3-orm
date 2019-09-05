package ru.otus.spring.hw3.dao;

import java.util.List;

import ru.otus.spring.hw3.models.Book;

public interface BookRepository {

    int insert(Book book);

    boolean update(Book book);

    boolean updateName(int id, String name);

    boolean updateAuthor(int id, int authorId);

    boolean updateGenre(int id, int genreId);

    boolean deleteById(int id);

    int count();

    Book getById(int id);

    List<Book> getAll();
}

package ru.otus.spring.hw3.dao;

import java.util.List;

import ru.otus.spring.hw3.models.Comment;

public interface CommentRepository {

    int insert(Comment comment);

    boolean update(int id, String text);

    boolean deleteById(int id);

    int count();

    Comment getById(int id);

    List<Comment> getAll();
}

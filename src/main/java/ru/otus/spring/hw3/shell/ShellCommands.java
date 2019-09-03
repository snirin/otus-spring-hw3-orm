package ru.otus.spring.hw3.shell;

import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import ru.otus.spring.hw3.dao.AuthorRepository;
import ru.otus.spring.hw3.dao.BookRepository;
import ru.otus.spring.hw3.dao.CommentRepository;
import ru.otus.spring.hw3.dao.GenreRepository;
import ru.otus.spring.hw3.models.Author;
import ru.otus.spring.hw3.models.Book;
import ru.otus.spring.hw3.models.Comment;
import ru.otus.spring.hw3.models.Genre;

@ShellComponent
public class ShellCommands {

    private final AuthorRepository authorDao;
    private final GenreRepository genreRepository;
    private final BookRepository bookRepository;
    private final CommentRepository commentRepository;

    public ShellCommands(AuthorRepository authorRepository, GenreRepository genreRepository, BookRepository bookRepository,
                         CommentRepository commentRepository) {
        this.authorDao = authorRepository;
        this.genreRepository = genreRepository;
        this.bookRepository = bookRepository;
        this.commentRepository = commentRepository;
    }

    /*
    AUTHORS
     */
    @ShellMethod(value = "Author:Insert", key = {"ai"})
    public int authorInsert(@ShellOption String name) {
        return authorDao.insert(new Author(0, name));
    }

    @ShellMethod(value = "Author:Update", key = {"au"})
    public boolean authorUpdate(@ShellOption int id, @ShellOption String name) {
        return authorDao.update(new Author(id, name));
    }

    @ShellMethod(value = "Author:Delete", key = {"ad"})
    public boolean authorDelete(@ShellOption int id) {
        return authorDao.deleteById(id);
    }

    @ShellMethod(value = "Author:Get", key = {"ag"})
    public String authorGet(@ShellOption int id) {
        return authorDao.getById(id).toString();
    }

    @ShellMethod(value = "Author:GetAll", key = {"aga"})
    public String authorGet() {
        return authorDao.getAll().toString();
    }

    @ShellMethod(value = "Author:Count", key = {"ac"})
    public int authorCount() {
        return authorDao.count();
    }
    /*
    AUTHORS - END
     */

    /*
    GENRES
     */
    @ShellMethod(value = "Genre:Insert", key = {"gi"})
    public int genreInsert(@ShellOption String name) {
        return genreRepository.insert(new Genre(0, name));
    }

    @ShellMethod(value = "Genre:Update", key = {"gu"})
    public boolean genreUpdate(@ShellOption int id, @ShellOption String name) {
        return genreRepository.update(new Genre(id, name));
    }

    @ShellMethod(value = "Genre:Delete", key = {"gd"})
    public boolean genreDelete(@ShellOption int id) {
        return genreRepository.deleteById(id);
    }

    @ShellMethod(value = "Genre:Get", key = {"gg"})
    public String genreGet(@ShellOption int id) {
        return genreRepository.getById(id).toString();
    }

    @ShellMethod(value = "Genre:GetAll", key = {"gga"})
    public String genreGet() {
        return genreRepository.getAll().toString();
    }

    @ShellMethod(value = "Genre:Count", key = {"gc"})
    public int genreCount() {
        return genreRepository.count();
    }
    /*
    GENRES - END
     */


    /*
    BOOKS
     */
    @ShellMethod(value = "Book:Insert", key = {"bi"})
    public int bookInsert(@ShellOption String name, @ShellOption int authorId, @ShellOption int genreId) {
        return bookRepository.insert(new Book(0, name, new Author(authorId, ""), new Genre(genreId, "")));
    }

    @ShellMethod(value = "Book:Update", key = {"bu"})
    public boolean bookUpdate(@ShellOption int id, @ShellOption String name, @ShellOption int authorId, @ShellOption int genreId) {
        return bookRepository.update(new Book(id, name, new Author(authorId, ""), new Genre(genreId, "")));
    }

    @ShellMethod(value = "Book:UpdateName", key = {"bun"})
    public boolean bookUpdateName(@ShellOption int id, @ShellOption String name) {
        return bookRepository.updateName(id, name);
    }

    @ShellMethod(value = "Book:UpdateAuthor", key = {"bua"})
    public boolean bookUpdateAuthor(@ShellOption int id, @ShellOption int authorId) {
        return bookRepository.updateAuthor(id, authorId);
    }

    @ShellMethod(value = "Book:UpdateGenre", key = {"bug"})
    public boolean bookUpdateGenre(@ShellOption int id, @ShellOption int genreId) {
        return bookRepository.updateGenre(id, genreId);
    }

    @ShellMethod(value = "Book:Delete", key = {"bd"})
    public boolean bookDelete(@ShellOption int id) {
        return bookRepository.deleteById(id);
    }

    @ShellMethod(value = "Book:Get", key = {"bg"})
    public String bookGet(@ShellOption int id) {
        return bookRepository.getById(id).toString();
    }

    @ShellMethod(value = "Book:GetAll", key = {"bga"})
    public String bookGet() {
        return bookRepository.getAll().toString();
    }

    @ShellMethod(value = "Book:Count", key = {"bc"})
    public int bookCount() {
        return bookRepository.count();
    }
    /*
    BOOKS - END
     */

    /*
    COMMENTS
     */
    @ShellMethod(value = "Comment:Insert", key = {"ci"})
    public int commentInsert(@ShellOption int bookId, @ShellOption String text) {
        return commentRepository.insert(new Comment(0, new Book().withId(bookId), text));
    }

    @ShellMethod(value = "Comment:Update", key = {"cu"})
    public boolean commentUpdate(@ShellOption int id, @ShellOption String text) {
        return commentRepository.update(id, text);
    }

    @ShellMethod(value = "Comment:Delete", key = {"cd"})
    public boolean commentDelete(@ShellOption int id) {
        return commentRepository.deleteById(id);
    }

    @ShellMethod(value = "Comment:Get", key = {"cg"})
    public String commentGet(@ShellOption int id) {
        return commentRepository.getById(id).toString();
    }

    @ShellMethod(value = "Comment:GetAll", key = {"cga"})
    public String commentGet() {
        return commentRepository.getAll().toString();
    }

    @ShellMethod(value = "Comment:GetByBook", key = {"cgb"})
    public String commentGetByBook(@ShellOption int bookId) {
        return commentRepository.getByBookId(bookId).toString();
    }

    @ShellMethod(value = "Comment:Count", key = {"cc"})
    public int commentCount() {
        return commentRepository.count();
    }
    /*
    COMMENTS - END
     */

}

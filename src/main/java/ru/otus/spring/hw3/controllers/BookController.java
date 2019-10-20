package ru.otus.spring.hw3.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.otus.spring.hw3.dao.AuthorRepository;
import ru.otus.spring.hw3.dao.BookRepository;
import ru.otus.spring.hw3.dao.GenreRepository;
import ru.otus.spring.hw3.models.Author;
import ru.otus.spring.hw3.models.Book;
import ru.otus.spring.hw3.models.Genre;

@Controller
public class BookController {

    private static final String BOOKS = "books";
    private static final String BOOK_EDIT = "book_edit";
    private final AuthorRepository authorRepository;
    private final GenreRepository genreRepository;
    private final BookRepository bookRepository;

    @Autowired
    public BookController(AuthorRepository authorRepository, GenreRepository genreRepository, BookRepository bookRepository) {
        this.authorRepository = authorRepository;
        this.genreRepository = genreRepository;
        this.bookRepository = bookRepository;
    }

    @GetMapping(BOOKS)
    public String listPage(Model model) {
        fillModel(model);
        return BOOKS;
    }

    @PostMapping(value = BOOKS, params={"removeId"})
    public String removeRow(Model model, @RequestParam("removeId") int id) {
        bookRepository.deleteById(id);

        fillModel(model);
        return BOOKS;
    }

    @GetMapping(BOOK_EDIT)
    public String editBook(@RequestParam("id") int id, Model model) {
        Book book = (id == 0) ? new Book(0, "", new Author(0), new Genre(0)) : bookRepository.getById(id);
        model.addAttribute("book", book);
        model.addAttribute("authors", authorRepository.getAll());
        model.addAttribute("genres", genreRepository.getAll());
        return BOOK_EDIT;
    }

    @PostMapping(BOOK_EDIT)
    public String editBook(@RequestParam(value = "id", defaultValue = "0") int id,
                           @RequestParam("name") String name,
                           @RequestParam("authorId") int authorId,
                           @RequestParam("genreId") int genreId,
                           Model model) {
        if (id == 0) {
            Book book = new Book(id, name, new Author(authorId), new Genre(genreId));
            bookRepository.insert(book);
        } else {
            Book book = bookRepository.getById(id);

            if (book == null) {
                throw new NotFoundException();
            }

            book.setName(name);
            book.setAuthor(new Author(authorId));
            book.setGenre(new Genre(genreId));
            bookRepository.update(book);
        }

        fillModel(model);

        return "redirect:/" + BOOKS;
    }

    private void fillModel(Model model) {
        List<Book> books = bookRepository.getAll();
        model.addAttribute("books", books);
    }
}

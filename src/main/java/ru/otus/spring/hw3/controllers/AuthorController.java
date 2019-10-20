package ru.otus.spring.hw3.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.otus.spring.hw3.dao.AuthorRepository;
import ru.otus.spring.hw3.models.Author;

@Controller
public class AuthorController {

    private static final String AUTHORS = "authors";
    private static final String AUTHOR_EDIT = "author_edit";
    private final AuthorRepository authorRepository;

    @Autowired
    public AuthorController(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    @GetMapping(AUTHORS)
    public String listPage(Model model) {
        fillModel(model);
        return AUTHORS;
    }

    @PostMapping(value = AUTHORS, params={"removeId"})
    public String removeRow(Model model, @RequestParam("removeId") int id) {
        authorRepository.deleteById(id);

        fillModel(model);
        return AUTHORS;
    }

    @GetMapping(AUTHOR_EDIT)
    public String editAuthor(@RequestParam("id") int id, Model model) {
        Author author = (id == 0) ? new Author(0, "") : authorRepository.getById(id);
        model.addAttribute("author", author);
        return AUTHOR_EDIT;
    }

    @PostMapping(AUTHOR_EDIT)
    public String editAuthor(@ModelAttribute("author") Author author, Model model) {
        if (author.getId() == 0) {
            authorRepository.insert(author);
        } else {
            authorRepository.update(author);
        }

        fillModel(model);
        return "redirect:/" + AUTHORS;
    }

    private void fillModel(Model model) {
        List<Author> authors = authorRepository.getAll();
        model.addAttribute("authors", authors);
    }
}

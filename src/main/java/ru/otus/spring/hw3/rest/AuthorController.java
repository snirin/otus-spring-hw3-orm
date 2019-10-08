package ru.otus.spring.hw3.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
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
    public String editPage(@RequestParam("id") int id, Model model) {
        Author author;
        if (id == 0) {
            author = new Author(0, "");
        } else {
            author = authorRepository.getById(id);
        }
        model.addAttribute("author", author);
        return AUTHOR_EDIT;
    }

    @PostMapping(AUTHOR_EDIT)
    public String editAuthor(@RequestParam(value = "id", defaultValue = "0") int id, @RequestParam("name") String name, Model model) {
        if (id == 0) {
            Author author = new Author(id, name);
            authorRepository.insert(author);
        } else {
            Author author = authorRepository.getById(id);

            if (author == null) {
                throw new NotFoundException();
            }

            author.setName(name);
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

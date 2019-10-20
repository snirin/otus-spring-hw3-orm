package ru.otus.spring.hw3.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.otus.spring.hw3.dao.GenreRepository;
import ru.otus.spring.hw3.models.Genre;

@Controller
public class GenreController {

    private static final String GENRES = "genres";
    private static final String GENRE_EDIT = "genre_edit";
    private final GenreRepository genreRepository;

    @Autowired
    public GenreController(GenreRepository genreRepository) {
        this.genreRepository = genreRepository;
    }

    @GetMapping(GENRES)
    public String listPage(Model model) {
        fillModel(model);
        return GENRES;
    }

    @PostMapping(value = GENRES, params={"removeId"})
    public String removeRow(Model model, @RequestParam("removeId") int id) {
        genreRepository.deleteById(id);

        fillModel(model);
        return GENRES;
    }

    @GetMapping(GENRE_EDIT)
    public String editGenre(@RequestParam("id") int id, Model model) {
        Genre genre = (id == 0) ? new Genre(0, "") : genreRepository.getById(id);
        model.addAttribute("genre", genre);
        return GENRE_EDIT;
    }

    @PostMapping(GENRE_EDIT)
    public String editGenre(@ModelAttribute("genre") Genre genre, Model model) {
        if (genre.getId() == 0) {
            genreRepository.insert(genre);
        } else {
            genreRepository.update(genre);
        }

        fillModel(model);
        return "redirect:/" + GENRES;
    }

    private void fillModel(Model model) {
        List<Genre> genres = genreRepository.getAll();
        model.addAttribute("genres", genres);
    }
}

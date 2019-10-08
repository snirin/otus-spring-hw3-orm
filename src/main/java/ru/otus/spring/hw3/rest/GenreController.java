package ru.otus.spring.hw3.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
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
    public String editPage(@RequestParam("id") int id, Model model) {
        Genre genre;
        if (id == 0) {
            genre = new Genre(0, "");
        } else {
            genre = genreRepository.getById(id);
        }
        model.addAttribute("genre", genre);
        return GENRE_EDIT;
    }

    @PostMapping(GENRE_EDIT)
    public String editGenre(@RequestParam(value = "id", defaultValue = "0") int id, @RequestParam("name") String name, Model model) {
        if (id == 0) {
            Genre genre = new Genre(id, name);
            genreRepository.insert(genre);
        } else {
            Genre genre = genreRepository.getById(id);

            if (genre == null) {
                throw new NotFoundException();
            }

            genre.setName(name);
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

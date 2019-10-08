package ru.otus.spring.hw3.rest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LibraryController {
    @GetMapping("/")
    public String indexPage() {
        return "index";
    }
}

package ru.otus.spring.hw3;

import java.sql.SQLException;

import org.h2.tools.Console;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class LibraryApplication {

    public static void main(String[] args) throws SQLException {

        SpringApplication.run(LibraryApplication.class);
        Console.main(args);
    }
}

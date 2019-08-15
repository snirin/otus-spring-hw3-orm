package ru.otus.spring.hw3.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "comments")
public class Comment {

    @Id
    @GeneratedValue
    private int id;
    @ManyToOne
    @JoinColumn(name = "b_id")
    private Book book;
    private String text;

    @Override
    public String toString() {
        return "Comment{" +
                "id=" + id +
                ", bookId=" + book.getId() +
                ", text='" + text + '\'' +
                '}';
    }
}

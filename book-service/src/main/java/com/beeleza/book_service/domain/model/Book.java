package com.beeleza.book_service.domain.model;

import jakarta.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "books")
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String title;
    private String author;

    @Column(unique = true)
    private String isbn;

    @Column(nullable = false)
    private Integer availableQuantity;

    public Book() {
    }

    public Book(String title, String author, String isbn, Integer availableQuantity) {
        this.title = title;
        this.author = author;
        this.isbn = isbn;
        this.availableQuantity = availableQuantity;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public Integer getAvailableQuantity() {
        return availableQuantity;
    }

    public void setAvailableQuantity(Integer availableQuantity) {
        this.availableQuantity = availableQuantity;
    }

}

package com.restws.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.hateoas.ResourceSupport;

/**
 * Model for Book entity in MongoDB books Collection
 * Created by Ninad on 1/4/2017.
 */
@Document(collection = "books")
public class Book extends ResourceSupport{ // ResourceSupport is used to add HATEOAS support for navigation
    @org.springframework.data.annotation.Id
    private String _Id;

    private String title;

    private String genre;

    private String author;

    private boolean read = false;

    public String get_Id() {
        return _Id;
    }

    public void set_Id(String id) {
        _Id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public boolean isRead() {
        return read;
    }

    public void setRead(boolean read) {
        this.read = read;
    }
}

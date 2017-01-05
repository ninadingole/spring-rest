package com.restws.resource;

import org.springframework.hateoas.ResourceSupport;

/**
 * View Model to present data in the view
 * Created by Ninad on 1/4/2017.
 */
public class BookResource extends ResourceSupport { // ResourceSupport is used to add HATEOAS support for navigation
    private String title;
    private String genre;

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
}

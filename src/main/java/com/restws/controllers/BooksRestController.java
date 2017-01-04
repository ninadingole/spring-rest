package com.restws.controllers;

import com.restws.model.Book;
import com.restws.service.BooksService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.*;

import java.util.List;

/**
 *  Book Rest controller to handle all HTTP opertaions related to books int the API.
 * Created by Ninad on 1/3/2017.
 */
@RestController
@RequestMapping("/api/books")
public class BooksRestController {

    @Autowired
    private BooksService booksService;

    public BooksService getBooksService() {
        return booksService;
    }

    public void setBooksService(BooksService booksService) {
        this.booksService = booksService;
    }

    /**
     * Returns all books that are found in the db as a json array
     * @param uriBuilder
     * @return
     */
    @RequestMapping(method = RequestMethod.GET,produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Book>> get(UriComponentsBuilder uriBuilder){
        List<Book> allBooks = booksService.getAllBooks();

        if(allBooks != null){
            allBooks.forEach(x-> x.add(linkTo(BooksRestController.class).slash(x.get_Id()).withSelfRel()));
            return new ResponseEntity<List<Book>>(allBooks, HttpStatus.OK);
        }

        return ResponseEntity.noContent().build();
    }

    @RequestMapping(method = RequestMethod.POST,consumes= MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Book> post(@RequestBody Book book){
        Book savedBook = booksService.saveBook(book);
        if(savedBook != null){
            savedBook.add(linkTo(BooksRestController.class).slash(savedBook.get_Id()).withSelfRel());
        }
        return new ResponseEntity<Book>(savedBook,HttpStatus.CREATED);
    }
}

package com.restws.controllers;

import com.restws.assembler.BookResourceAssembler;
import com.restws.model.Book;
import com.restws.resource.BookResource;
import com.restws.service.BooksService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
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
     * @return
     */
    @RequestMapping(method = RequestMethod.GET,produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<BookResource>> get(
            @RequestParam(name = "genre", required = false, defaultValue = "")String genre){
        List<Book> allBooks = booksService.getAllBooks();
        if(allBooks != null){
            return new ResponseEntity<List<BookResource>>(new BookResourceAssembler().toResources(allBooks),HttpStatus.OK);
        }
        return ResponseEntity.noContent().build();
    }

    @RequestMapping(method = RequestMethod.POST,consumes= MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<BookResource> post(@RequestBody Book book){
        Book savedBook = booksService.saveBook(book);

        return new ResponseEntity<BookResource>(new BookResourceAssembler().toResource(savedBook),HttpStatus.CREATED);
    }

    @RequestMapping(value = "/{id}",method = RequestMethod.GET,produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<BookResource> getBook(@PathVariable String id){
        Book book = booksService.getBook(id);
        BookResource bookResource = new BookResourceAssembler().toResource(book);
        return new ResponseEntity<BookResource>(bookResource,HttpStatus.OK);
    }
}

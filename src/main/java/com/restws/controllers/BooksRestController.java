package com.restws.controllers;

import com.restws.assembler.BookResourceAssembler;
import com.restws.model.Book;
import com.restws.resource.BookResource;
import com.restws.service.BooksService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.ExposesResourceFor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * Book Rest controller to handle all HTTP opertaions related to books int the API.
 * Created by Ninad on 1/3/2017.
 */
@RestController
@ExposesResourceFor(Book.class)
@RequestMapping("/api/books")
public class BooksRestController {

    @Autowired
    private BooksService booksService;
    /* @Autowired
     private BookRepository bookRepository;
 */
    private BookResourceAssembler assembler = new BookResourceAssembler();

    public BooksService getBooksService() {
        return booksService;
    }

    public void setBooksService(BooksService booksService) {
        this.booksService = booksService;
    }

    /**
     * Returns all books that are found in the db as a json array
     *
     * @return
     */
    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<BookResource>> get(
            @RequestParam(name = "genre", required = false, defaultValue = "") String genre) {
        List<Book> allBooks = booksService.getAllBooks();
        // Spring Mongo Repository with Java 8
        /*List<BookResource> allBooks = StreamSupport.stream(bookRepository.findAll().spliterator(), false)
                .map(x -> assembler.toResource(x))
                .collect(Collectors.toList());
        */
        if (allBooks != null) {
            return new ResponseEntity<List<BookResource>>(assembler.toResources(allBooks), HttpStatus.OK);
        }
        return ResponseEntity.noContent().build();
    }

    /**
     * Add a new Book to collection
     *
     * @param book - Book Json recieved from Request
     * @return ResponseEntity<BookResource> bookresource.
     */
    @RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<BookResource> post(@RequestBody Book book) {
        Book savedBook = booksService.saveBook(book);
//        Book save = bookRepository.save(book);
        return new ResponseEntity<BookResource>(assembler.toResource(savedBook), HttpStatus.CREATED);
    }

    /**
     * Get the book details for the given ID.
     *
     * @param id book id recievd in query.
     * @return
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<BookResource> getBook(@PathVariable String id) {
        Book book = booksService.getBook(id);
//        Book book = bookRepository.findOne(id);
        BookResource bookResource = assembler.toResource(book);
        return new ResponseEntity<BookResource>(bookResource, HttpStatus.OK);
    }

    /**
     * Remove the book from collection
     *
     * @param id Book Id to remove from collection
     * @return 200 in case of success or 204 in case of id is not found or book is already deleted.
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity delete(@PathVariable String id) {
        if (id != null) {
//             bookRepository.delete(id);

            if (booksService.remove(id).isPresent()) {
                return ResponseEntity.ok("Content Deleted");
            }
        }
        return ResponseEntity.noContent().build();
    }

    /**
     * PATCH only the information that are received for the given book id.
     *
     * @param id   Book Id recieved as a part of request
     * @param book the content received for update
     * @return the new updated book details.
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.PATCH, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<BookResource> patch(@PathVariable String id, @RequestBody Book book) {
        Optional<Book> update = this.booksService.update(id, book);
        if (update.isPresent()) {
            return new ResponseEntity<BookResource>(assembler.toResource(update.get()), HttpStatus.OK);
        }
        return ResponseEntity.notFound().build();

    }
}

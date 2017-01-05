package com.restws.assembler;

import com.restws.controllers.BooksRestController;
import com.restws.model.Book;
import com.restws.resource.BookResource;
import org.springframework.hateoas.mvc.IdentifiableResourceAssemblerSupport;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * {@link BookResourceAssembler } coverts the data domain object into view object reason being to keep seperation of
 * concern to follow single responsibilty pattern. Adding HATEOS support on domain object is not a good practice and
 * its better to let them only handle db related work. In this case the BookResource and Book object doesn't add much
 * of the logic but in a real work application this will be really helpful.
 *
 * Created by Ninad on 1/4/2017.
 */
public class BookResourceAssembler extends IdentifiableResourceAssemblerSupport<Book, BookResource> {

    public BookResourceAssembler() {
        super(BooksRestController.class, BookResource.class);
    }

    @Override
    public List<BookResource> toResources(Iterable<? extends Book> entities) {
        List<BookResource> res = new ArrayList<>();

        entities.forEach(book -> {
            BookResource bookResource = createResourceWithId(book.getId(), book);
            bookResource.setGenre(book.getGenre());
            bookResource.setTitle(book.getTitle());
            // You can also add other mappings here.
            res.add(bookResource);
        });
        return res;
    }

    @Override
    public BookResource toResource(Book book) {
        BookResource resource = createResource(book);
        resource.setTitle(book.getTitle());
        resource.setGenre(book.getGenre());
        Map<String,String> map  = new HashMap<>();
        map.put("genre",resource.getGenre());
        resource.add(linkTo(methodOn(BooksRestController.class).get(resource.getGenre())).withRel("filterByThisGenre"));
        return resource;
    }
}

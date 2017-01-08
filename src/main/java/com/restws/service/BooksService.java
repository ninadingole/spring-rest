package com.restws.service;

import com.restws.model.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;

import static org.springframework.data.mongodb.core.query.Criteria.*;

import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Service class implementation to perform different operations on mongodb books collection
 * Created by Ninad.
 */
@Service
public class BooksService {

    @Autowired
    private MongoOperations mongoOperations;

    public MongoOperations getMongoOperations() {
        return mongoOperations;
    }

    public void setMongoOperations(MongoOperations mongoOperations) {
        this.mongoOperations = mongoOperations;
    }


    /**
     * Get All the books that are present in the collection.
     * @return List<Book> collection of all books.
     */
    public List<Book> getAllBooks(){
        List<Book> books = mongoOperations.find(new Query(), Book.class);
        return books;
    }

    /**
     * Save Book to the collection.
     * @param book object to be stored
     * @return Book object with added _id field
     */
    public Book saveBook(Book book) {
        this.mongoOperations.save(book);
        return book;
    }

    /**
     * Get the book for given id from the collection.
     * @param id book id to query.
     * @return Book entity from the collection
     */
    public Book getBook(String id) {
        Query q = queryById(id);
        Book found = this.mongoOperations.findOne(q, Book.class);
        return found;
    }

    /**
     * Remove the book for the received book id.
     * @param id
     * @return
     */
    public Optional<Book> remove(String id) {
        Book removedBook = this.mongoOperations.findAndRemove(queryById(id),Book.class);
        return Optional.ofNullable(removedBook);
    }

    /**
     * Update the book to the collection. Frankly this is a poor implementation I know.
     * @param id
     * @param b
     * @return
     */
    public Optional<Book> update(String id,Book b){

        Book book = this.mongoOperations.findOne(queryById(id), Book.class);
        if(book == null){
            return Optional.of(null);
        }
        if(b.getTitle() != null){
            book.setTitle(b.getTitle());
        }
        if(b.getGenre() != null){
            book.setGenre(b.getGenre());
        }
        if(b.getAuthor() != null){
            book.setAuthor(b.getAuthor());
        }
        if(b.isRead() != book.isRead()){
            book.setRead(b.isRead());
        }
        this.mongoOperations.save(book);
        return Optional.ofNullable(book);
    }


    private Query queryById(String _id){
        Query q = new Query();
        q.addCriteria(where("_id").is(_id));
        return q;
    }

}

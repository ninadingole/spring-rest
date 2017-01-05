package com.restws.service;

import com.restws.model.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.BasicQuery;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.CriteriaDefinition;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.List;

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

    public Book getBook(String id) {
        Query q = new Query();
        q.addCriteria(Criteria.where("_id").is(id));
        Book found = this.mongoOperations.findOne(q, Book.class);
        return found;
    }
}

package com.restws.repository.mongo;

import com.restws.model.Book;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by Ninad on 1/7/2017.
 */
@Repository
public interface BookRepository extends CrudRepository<Book, String>{
}

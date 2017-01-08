package unittest;

import com.restws.config.SpringMongoConfig;
import com.restws.model.Book;
import com.restws.repository.mongo.BookRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created by Ninad on 1/7/2017.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {SpringMongoConfig.class})
@EnableAutoConfiguration
@ComponentScan(basePackages = "com.restws")
@PropertySource("classpath:application.properties")
public class MongoDBTest {

    @Autowired
    BookRepository repository;

    @Test
    public void test(){
        Iterable<Book> all =
                repository.findAll();
        all.forEach(System.out::println);
    }



}

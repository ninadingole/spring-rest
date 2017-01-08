package com.restws.config;

import com.mongodb.Mongo;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.authentication.UserCredentials;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.core.MongoClientFactoryBean;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoDbFactory;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

/**
 * Annotation based configurations for Spring Data MongoDB support.
 * Created by Ninad on 1/4/2017.
 */
@Configuration
@EnableMongoRepositories(basePackages = "com.restws.repository.mongo")
public class SpringMongoConfig {

    /**
     * Injected from properties file
     */
    @Value("${spring.data.mongodb.database}")
    private String databaseName;

    @Value("${spring.data.mongodb.host}")
    private String host;

    @Value("${spring.data.mongodb.port}")
    private String port;


    /**
     * Returns Beans Instance of {@link MongoTemplate}
     * @return MongoTemplate
     * @throws Exception
     */
    @Bean
    public MongoTemplate mongoTemplate() throws Exception{
        return new MongoTemplate(mongoDbFactory());
    }


    /**
     * Returns Bean instance of {@link Mongo}, A Connection object to mongodb
     * @return
     * @throws Exception
     */
    public Mongo mongo() throws Exception {
        MongoClientURI uri = new MongoClientURI("mongodb://localhost/");
        Mongo mongo = Mongo.Holder.singleton().connect(uri);
        return mongo;
    }

    /**
     * Returns bean instance of MongoClient required by {@link MongoTemplate}
     * @return
     */
    @Bean
    public MongoClient mongoClient() throws Exception{
        return new MongoClient(mongo().getAddress());
    }

    @Bean
    public MongoDbFactory mongoDbFactory()throws Exception{
        return new SimpleMongoDbFactory(mongoClient(),databaseName);
    }


}

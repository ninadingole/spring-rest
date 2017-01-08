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
 * Created by Ninad on 1/4/2017.
 */
@Configuration
@EnableMongoRepositories(basePackages = "com.restws.repository.mongo")
public class SpringMongoConfig {

    @Value(value = "${dbName}")
    private String databaseName;

    @Bean
    public MongoTemplate mongoTemplate() throws Exception{
        MongoTemplate template = new MongoTemplate(mongoClient(),"libraryApp");
        return template;
    }


    public Mongo mongo() throws Exception {
        MongoClientURI uri = new MongoClientURI("mongodb://localhost/"+databaseName);
        Mongo mongo = Mongo.Holder.singleton().connect(uri);
        return mongo;
    }

    @Bean
    public MongoClient mongoClient(){
        MongoClientURI uri = new MongoClientURI("mongodb://localhost/");
        return new MongoClient(uri);
    }

    @Bean
    public MongoDbFactory mongoDbFactory(){
        return new SimpleMongoDbFactory(mongoClient(),databaseName);
    }


}

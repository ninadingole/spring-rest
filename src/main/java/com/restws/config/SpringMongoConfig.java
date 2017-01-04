package com.restws.config;

import com.mongodb.MongoClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;

/**
 * Created by Ninad on 1/4/2017.
 */
@Configuration
public class SpringMongoConfig {

    @Bean
    public MongoTemplate mongoTemplate() throws Exception{
        MongoTemplate template = new MongoTemplate(new MongoClient("localhost"),"libraryApp");
        return template;
    }
}

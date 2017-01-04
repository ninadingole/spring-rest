package com.restws.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Home Controller as a sample page to check if the API is up and running.
 * Created by Ninad.
 */
@RestController
@RequestMapping("/")
public class HomeRestController {

    /**
     * Returns a hello message as a JSON string when hit from a browser
     * @return
     */
    @RequestMapping(value = "/",method = RequestMethod.GET,produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Message> index(){
        return new ResponseEntity(new Message(), HttpStatus.OK);
    }

    class Message{
        private String title = "Hello World";
        private String author = "Ninad Ingole";
        public String getTitle(){
            return title;
        }

        public String getAuthor(){
            return author;
        }
    }
}

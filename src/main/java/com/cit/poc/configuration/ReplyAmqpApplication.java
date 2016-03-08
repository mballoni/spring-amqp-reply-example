package com.cit.poc.configuration;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("com.cit.poc")
public class ReplyAmqpApplication {

    public static void main(String[] args) {
         SpringApplication.run(ReplyAmqpApplication.class, args);
    }
}

package com.cit.poc.configuration;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@ComponentScan("com.cit.poc")
@EnableAsync
public class ReplyAmqpApplication {

    public static void main(String[] args) {
         SpringApplication.run(ReplyAmqpApplication.class, args);
    }
}

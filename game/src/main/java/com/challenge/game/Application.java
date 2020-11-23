package com.challenge.game;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class Application {
    public static void main(final String[] args) {
        ApplicationContext applicationContext = SpringApplication.run(Application.class, args);
    }
}

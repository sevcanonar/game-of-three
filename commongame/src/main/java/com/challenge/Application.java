package com.challenge;

import com.challenge.integrator.EchoSocketServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication(scanBasePackages = {"com.challenge"})
public class Application {

    public static void main(final String[] args) {
        ApplicationContext applicationContext = SpringApplication.run(Application.class, args);
        EchoSocketServer server = applicationContext.getBean(EchoSocketServer.class);
        server.start();
    }
}

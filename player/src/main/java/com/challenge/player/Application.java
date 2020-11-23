package com.challenge.player;

import com.challenge.player.model.PlayerMessage;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.messaging.simp.stomp.StompSession;

import java.util.Scanner;

@SpringBootApplication
public class Application {

    public static void main(final String[] args) {
        ApplicationContext applicationContext = SpringApplication.run(Application.class, args);

        Scanner scanner = new Scanner(System.in);

        //  prompt for the user's name
        System.out.print("Enter your name: ");

        // get their input as a String
        String username = scanner.next();

        PlayerMessage playerMessage = new PlayerMessage();
        playerMessage.setFrom("user1");
        playerMessage.setText("5");
        applicationContext.getBean(StompSession.class).send("/app/chat", playerMessage);
    }
}

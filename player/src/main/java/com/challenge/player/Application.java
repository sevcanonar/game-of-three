package com.challenge.player;

import com.challenge.player.constants.EventType;
import com.challenge.player.model.PlayerEvent;
import com.challenge.player.service.PlayerLoginService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.messaging.simp.stomp.StompSessionHandler;

import java.util.Scanner;

@SpringBootApplication
public class Application {

    public static void main(final String[] args) {
        ApplicationContext applicationContext = SpringApplication.run(Application.class, args);


        PlayerLoginService playerLoginService = applicationContext.getBean(PlayerLoginService.class);
        playerLoginService.scanPlayerCredentials();

    }
}

package com.challenge.player.service;

import com.challenge.player.event.EventSender;
import com.challenge.player.event.OutgoingEvent.UserLoginEventBuilder;
import com.challenge.player.model.PlayerEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.messaging.simp.stomp.StompSessionHandler;
import org.springframework.stereotype.Service;

import java.util.Scanner;

@Service
public class PlayerLoginScannerService implements PlayerLoginService {

    @Autowired
    UserLoginEventBuilder userLoginEventBuilder;
    @Autowired
    EventSender eventSender;
    @Autowired
    ApplicationContext applicationContext;

    @Override
    public void scanPlayerCredentials() {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter your user name: ");

        String userName = scanner.next();
        StompSession playerSession = applicationContext.getBean(StompSession.class);
        StompSessionHandler playerSessionHandler = applicationContext.getBean(StompSessionHandler.class);
        playerSession.subscribe("/topic/" + userName, playerSessionHandler);

        PlayerEvent playerEvent = userLoginEventBuilder.buildEvent(userName);
        eventSender.sendEvent(playerEvent);
    }
}

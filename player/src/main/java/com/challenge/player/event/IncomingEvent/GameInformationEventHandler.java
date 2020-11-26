package com.challenge.player.event.IncomingEvent;

import com.challenge.player.constants.EventType;
import com.challenge.player.model.PlayerEvent;
import org.springframework.stereotype.Component;

import java.util.Scanner;

@Component
public class GameInformationEventHandler {
    public PlayerEvent handleIncomingEvent(PlayerEvent playerEvent) {

        PlayerEvent playerResponseEvent = new PlayerEvent();
        System.out.println("***********************************");
        System.out.println("ONLINE USERS");
        playerEvent.getOnlineUsers().stream().forEach(userName -> System.out.println("User name: " + userName));
        System.out.println("***********************************");
        System.out.println("GAME STATUS");
        if (playerEvent.isGameStatus()) {
            System.out.println("There is a game already started." + "The entered value is: " + playerEvent.getValue());
            playerResponseEvent.setEventType(EventType.MOVE_IS_PLAYED);
        } else {
            System.out.println("There is no game started yet. Do you want to start a new game?");
            playerResponseEvent.setEventType(EventType.START_GAME);
        }

        System.out.println("***********************************");
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter your input: ");
        String enteredValue = scanner.next();
        playerResponseEvent.setValue(Integer.valueOf(enteredValue));
        playerResponseEvent.setFrom(playerEvent.getTo());
        playerResponseEvent.setTo(playerEvent.getFrom());
        playerResponseEvent.setOnlineUsers(playerEvent.getOnlineUsers());
        playerResponseEvent.setGameStatus(true);
        return playerResponseEvent;

    }
}

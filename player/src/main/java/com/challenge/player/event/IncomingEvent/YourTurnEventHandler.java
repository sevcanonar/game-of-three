package com.challenge.player.event.IncomingEvent;

import com.challenge.player.constants.EventType;
import com.challenge.player.model.PlayerEvent;
import org.springframework.stereotype.Component;

import java.util.Scanner;

@Component
public class YourTurnEventHandler {
    public PlayerEvent handleIncomingEvent(PlayerEvent playerEvent) {
        PlayerEvent playerResponseEvent = new PlayerEvent();
        System.out.println("***********************************");
        System.out.println("YOUR TURN");
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter your input: ");
        String enteredValue = scanner.next();
        playerResponseEvent.setEventType(EventType.MOVE_IS_PLAYED);
        playerResponseEvent.setValue(Integer.valueOf(enteredValue));
        playerResponseEvent.setFrom(playerEvent.getTo());
        playerResponseEvent.setTo(playerEvent.getFrom());
        playerResponseEvent.setOnlineUsers(playerEvent.getOnlineUsers());
        playerResponseEvent.setGameStatus(playerEvent.isGameStatus());
        return playerResponseEvent;

    }
}

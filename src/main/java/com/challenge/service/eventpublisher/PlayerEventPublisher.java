package com.challenge.service.eventpublisher;

import com.challenge.config.PlayerEventQueue;
import com.challenge.event.PlayerEvent;
import org.springframework.stereotype.Component;

@Component
public class PlayerEventPublisher {

    PlayerEventQueue playerEventQueue;

    public PlayerEventPublisher(PlayerEventQueue playerEventQueue) {
        this.playerEventQueue = playerEventQueue;
    }

    public void publish(PlayerEvent playerEvent) {
        if (playerEvent != null) {
            try {
                playerEventQueue.getInstance().put(playerEvent);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }
}

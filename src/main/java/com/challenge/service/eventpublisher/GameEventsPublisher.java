package com.challenge.service.eventpublisher;

import com.challenge.event.GameEvent;
import com.challenge.event.PlayerEvent;
import com.challenge.service.GameEventsCreator;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class GameEventsPublisher {

    GameEventsCreator gameEventsCreator;
    PlayerEventPublisher playerEventPublisher;

    public GameEventsPublisher(GameEventsCreator gameEventsCreator, PlayerEventPublisher playerEventPublisher) {
        this.gameEventsCreator = gameEventsCreator;
        this.playerEventPublisher = playerEventPublisher;
    }

    @Async
    public void publish(List<GameEvent> gameEvents) {
        for (GameEvent gameEvent : gameEvents) {
            gameEventsCreator.createEvent(gameEvent);
        }
    }
}

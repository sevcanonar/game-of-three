package com.challenge.service.eventpublisher;

import com.challenge.event.GameEvent;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class GameEventsPublisher implements GamePublisher {

    GameEventsCreator gameEventsCreator;
    PlayerEventPublisher playerEventPublisher;

    public GameEventsPublisher(GameEventsCreator gameEventsCreator, PlayerEventPublisher playerEventPublisher) {
        this.gameEventsCreator = gameEventsCreator;
        this.playerEventPublisher = playerEventPublisher;
    }

    @Override
    @Async
    public void publish(List<GameEvent> gameEvents) {
        for (GameEvent gameEvent : gameEvents) {
            gameEventsCreator.createEvent(gameEvent);
        }
    }
}

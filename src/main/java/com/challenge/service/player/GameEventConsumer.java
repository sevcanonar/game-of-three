package com.challenge.service.player;

import com.challenge.config.GameListeners;
import com.challenge.event.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
public class GameEventConsumer {

    @Autowired
    GameListeners gameListeners;

    public void register(GameListener listener) {
        gameListeners.getInstance().add(listener);
    }

    @Async
    public void createEvent(GameEvent gameEvent) {
        System.out.println("GameEventConsumer current thread " + Thread.currentThread());
        for (GameListener listener : gameListeners.getInstance()) {
            if (gameEvent instanceof GameYourTurnEvent) {
                listener.onGameYourTurnEvent((GameYourTurnEvent) gameEvent);
            }
            if (gameEvent instanceof GameInformationEvent) {
                listener.onGameInformationEvent(gameEvent);
            }
            if (gameEvent instanceof GameOverEvent) {
                listener.onGameOverEvent(gameEvent);
            }
            if (gameEvent instanceof GameStartEvent) {
                listener.onGameStartEvent((GameStartEvent) gameEvent);
            }
            if (gameEvent instanceof GameAutoManualInformationEvent) {
                listener.onGameAutoManualInformationEvent(gameEvent);
            }
        }
    }

}

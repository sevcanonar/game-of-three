package com.challenge.player.listener;

import com.challenge.player.config.GameListeners;
import com.challenge.player.event.*;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
public class GameEventRegistererCaller {

    public void register(GameListener listener) {
        GameListeners.getInstance().add(listener);
    }

    @Async
    public void eventHappens(GameEvent gameEvent) {
        for (GameListener listener : GameListeners.getInstance()) {
            if (gameEvent instanceof GameYourTurnEvent) {
                listener.onGameYourTurnEvent(gameEvent);
            }
            if (gameEvent instanceof GameInformationEvent) {
                listener.onGameInformationEvent(gameEvent);
            }
            if (gameEvent instanceof GameOverEvent) {
                listener.onGameOverEvent(gameEvent);
            }
            if (gameEvent instanceof GameStartEvent) {
                listener.onGameStartEvent(gameEvent);
            }
            if (gameEvent instanceof GameAutoManualInformationEvent) {
                listener.onGameAutoManualInformationEvent(gameEvent);
            }
            if (gameEvent instanceof GameStartInformationEvent) {
                listener.onGameStartInformationEvent(gameEvent);
            }
        }
    }

}

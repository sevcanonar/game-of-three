package com.challenge.service.player;

import com.challenge.config.GameListenersPerPlayer;
import com.challenge.constants.GameListenerType;
import com.challenge.event.*;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class GameEventsCreator {

    GameEventsRegisterer gameEventsRegisterer;
    GameListenersPerPlayer gameListenersPerPlayer;
    GameListener gameListener;

    public GameEventsCreator(GameEventsRegisterer gameEventsRegisterer, GameListenersPerPlayer gameListenersPerPlayer) {
        this.gameEventsRegisterer = gameEventsRegisterer;
        this.gameListenersPerPlayer = gameListenersPerPlayer;
    }
    @Async
    public void createEvent(GameEvent gameEvent) {
        Map<GameListenerType, GameListener> gameListenerMapForUser = gameListenersPerPlayer.getInstance().get(gameEvent.getTo());
        if (gameEvent instanceof GameYourTurnEvent) {
            gameListener = gameListenerMapForUser.get(GameListenerType.YOURTURN);
        } else if (gameEvent instanceof GameInformationEvent) {
            gameListener = gameListenerMapForUser.get(GameListenerType.INFO);
        } else if (gameEvent instanceof GameOverEvent) {
            gameListener = gameListenerMapForUser.get(GameListenerType.GAMEOVER);
            gameEventsRegisterer.deRegisterAllListeners(gameEvent.getTo());
        } else if (gameEvent instanceof GameStartEvent) {
            gameListener = gameListenerMapForUser.get(GameListenerType.START);
        } else if (gameEvent instanceof GameAutoManualInformationEvent) {
            gameListener = gameListenerMapForUser.get(GameListenerType.AUTOMAN);
        }
        gameListener.onGameEvent(gameEvent);
    }

}

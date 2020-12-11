package com.challenge.service.player;

import com.challenge.config.GameListenersPerPlayer;
import com.challenge.constants.GameListenerType;
import com.challenge.event.*;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class GameEventsConsumer {

    GameListenersPerPlayer gameListenersPerPlayer;
    GameListener gameListener;

    public GameEventsConsumer(GameListenersPerPlayer gameListenersPerPlayer) {
        this.gameListenersPerPlayer = gameListenersPerPlayer;
    }

    public void register(String userName, GameListenerType gameListenerType, GameListener listener) {
        Map<GameListenerType, GameListener> gameListenerTypeGameListenerMapForUser = gameListenersPerPlayer.getInstance().get(userName);
        if (gameListenerTypeGameListenerMapForUser == null) {
            gameListenerTypeGameListenerMapForUser = new HashMap<>();
        }
        gameListenerTypeGameListenerMapForUser.put(gameListenerType, listener);

        gameListenersPerPlayer.getInstance().put(userName, gameListenerTypeGameListenerMapForUser);
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
        } else if (gameEvent instanceof GameStartEvent) {
            gameListener = gameListenerMapForUser.get(GameListenerType.START);
        } else if (gameEvent instanceof GameAutoManualInformationEvent) {
            gameListener = gameListenerMapForUser.get(GameListenerType.AUTOMAN);
        }
        gameListener.onGameEvent(gameEvent);
    }

}

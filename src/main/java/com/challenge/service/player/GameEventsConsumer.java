package com.challenge.service.player;

import com.challenge.config.GameListenersPerPlayer;
import com.challenge.constants.GameListenerType;
import com.challenge.event.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class GameEventsConsumer {

    GameListener gameListener;

    @Autowired
    GameListenersPerPlayer gameListenersPerPlayer;

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
        for (Map.Entry<String, Map<GameListenerType, GameListener>> gameListenerMap : gameListenersPerPlayer.getInstance().entrySet()) {
            if (gameListenerMap.getKey().equals(gameEvent.getTo())) {
                Map<GameListenerType, GameListener> userListenersMap = gameListenerMap.getValue();
                if (gameEvent instanceof GameYourTurnEvent) {
                    gameListener = userListenersMap.get(GameListenerType.YOURTURN);
                } else if (gameEvent instanceof GameInformationEvent) {
                    gameListener = userListenersMap.get(GameListenerType.INFO);
                } else if (gameEvent instanceof GameOverEvent) {
                    gameListener = userListenersMap.get(GameListenerType.GAMEOVER);
                } else if (gameEvent instanceof GameStartEvent) {
                    gameListener = userListenersMap.get(GameListenerType.START);
                } else if (gameEvent instanceof GameAutoManualInformationEvent) {
                    gameListener = userListenersMap.get(GameListenerType.AUTOMAN);
                }
                gameListener.onGameEvent(gameEvent);
            }

        }
    }

}

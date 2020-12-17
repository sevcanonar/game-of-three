package com.challenge.service.player;

import com.challenge.config.GameListenersPerPlayer;
import com.challenge.config.PlayerEventQueue;
import com.challenge.constants.GameListenerType;
import com.challenge.event.*;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

@Component
public class GameEventsConsumer {

    GameListenersPerPlayer gameListenersPerPlayer;
    GameListener gameListener;

    public GameEventsConsumer(GameListenersPerPlayer gameListenersPerPlayer) {
        this.gameListenersPerPlayer = gameListenersPerPlayer;
    }

    public void registerAllListeners(String userName, PrintWriter out, Scanner in, Socket clientSocket, PlayerEventQueue playerEventQueue) {
        register(userName, GameListenerType.AUTOMAN, new GameAutoManualInfoEventListener(out, in, clientSocket, playerEventQueue));
        register(userName, GameListenerType.INFO, new GameInformationEventListener(out, in, clientSocket, playerEventQueue));
        register(userName, GameListenerType.START, new GameStartEventListener(out, in, clientSocket, playerEventQueue));
        register(userName, GameListenerType.YOURTURN, new GameYourTurnEventListener(out, in, clientSocket, playerEventQueue));
        register(userName, GameListenerType.GAMEOVER, new GameOverEventListener(out, in, clientSocket, playerEventQueue));
    }


    private void register(String userName, GameListenerType gameListenerType, GameListener listener) {
        Map<GameListenerType, GameListener> gameListenerTypeGameListenerMapForUser = gameListenersPerPlayer.getInstance().get(userName);
        if (gameListenerTypeGameListenerMapForUser == null) {
            gameListenerTypeGameListenerMapForUser = new HashMap<>();
        }
        gameListenerTypeGameListenerMapForUser.put(gameListenerType, listener);

        gameListenersPerPlayer.getInstance().put(userName, gameListenerTypeGameListenerMapForUser);
    }

    public void deRegisterAllListeners(String userName) {
        gameListenersPerPlayer.getInstance().remove(userName);
    }

    public Map<GameListenerType, GameListener> getGameListeners(String userName) {
        return gameListenersPerPlayer.getInstance().get(userName);
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
            deRegisterAllListeners(gameEvent.getTo());
        } else if (gameEvent instanceof GameStartEvent) {
            gameListener = gameListenerMapForUser.get(GameListenerType.START);
        } else if (gameEvent instanceof GameAutoManualInformationEvent) {
            gameListener = gameListenerMapForUser.get(GameListenerType.AUTOMAN);
        }
        gameListener.onGameEvent(gameEvent);
    }

}

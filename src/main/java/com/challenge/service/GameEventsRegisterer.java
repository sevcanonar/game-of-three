package com.challenge.service;

import com.challenge.config.GameListenersPerPlayer;
import com.challenge.config.PlayerEventQueue;
import com.challenge.constants.GameListenerType;
import com.challenge.service.player.eventlistener.*;
import org.springframework.stereotype.Component;

import java.io.PrintWriter;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

@Component
public class GameEventsRegisterer {

    GameListenersPerPlayer gameListenersPerPlayer;

    public GameEventsRegisterer(GameListenersPerPlayer gameListenersPerPlayer) {
        this.gameListenersPerPlayer = gameListenersPerPlayer;
    }

    public void registerAllListeners(String userName, PrintWriter out, Scanner in, Socket clientSocket) {
        register(userName, GameListenerType.AUTOMAN, new GameAutoManualInfoEventListener(out, in, clientSocket));
        register(userName, GameListenerType.INFO, new GameInformationEventListener(out, in, clientSocket));
        register(userName, GameListenerType.START, new GameStartEventListener(out, in, clientSocket));
        register(userName, GameListenerType.YOURTURN, new GameYourTurnEventListener(out, in, clientSocket));
        register(userName, GameListenerType.GAMEOVER, new GameOverEventListener(out, in, clientSocket));
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

}

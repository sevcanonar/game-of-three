package com.challenge.service.mock;

import com.challenge.constants.GameListenerType;
import com.challenge.service.player.GameListener;
import com.challenge.service.player.GameYourTurnEventListener;

import java.util.HashMap;
import java.util.Map;

public class GameListenersPerPlayerMock {
    public Map<String, Map<GameListenerType, GameListener>> getGameListenersEmptyMock() {
        return new HashMap<>();
    }

    public Map<String, Map<GameListenerType, GameListener>> getGameListenersOneMock() {
        Map<String, Map<GameListenerType, GameListener>> gameListeners = new HashMap<>();
        Map<GameListenerType, GameListener> userGameListenerMap = new HashMap<>();
        GameYourTurnEventListener gameListener = new GameYourTurnEventListener(null, null, null, null);
        userGameListenerMap.put(GameListenerType.YOURTURN, gameListener);
        gameListeners.put("a", userGameListenerMap);
        return gameListeners;
    }
}

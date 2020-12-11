package com.challenge.config;

import com.challenge.constants.GameListenerType;
import com.challenge.service.player.GameListener;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class GameListenersPerPlayer {

    private static Map<String, Map<GameListenerType, GameListener>> gameListeners = new HashMap<>();

    protected GameListenersPerPlayer() {
    }

    public Map<String, Map<GameListenerType, GameListener>> getInstance() {
        return gameListeners;
    }
}


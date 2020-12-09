package com.challenge.config;

import com.challenge.service.player.GameListener;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.HashSet;

@Component
public class GameListeners {

    private static Collection<GameListener> gameListeners = new HashSet<>();


    protected GameListeners() {
    }

    public Collection<GameListener> getInstance() {
        return gameListeners;
    }
}


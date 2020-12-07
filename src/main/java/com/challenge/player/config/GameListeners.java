package com.challenge.player.config;

import com.challenge.player.listener.GameListener;

import java.util.Collection;
import java.util.HashSet;

public class GameListeners {

    private static Collection<GameListener> gameListeners =new HashSet<>();


    protected GameListeners() {
    }

    public static Collection<GameListener> getInstance() {
        return gameListeners;
    }
}


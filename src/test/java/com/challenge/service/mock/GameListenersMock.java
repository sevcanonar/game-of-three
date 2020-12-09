package com.challenge.service.mock;

import com.challenge.service.player.GameEventsListener;
import com.challenge.service.player.GameListener;

import java.util.Collection;
import java.util.HashSet;

public class GameListenersMock {
    public Collection<GameListener> getGameListenersEmptyMock() {
        return new HashSet<>();
    }

    public Collection<GameListener> getGameListenersOneMock() {
        Collection<GameListener> gameListeners = new HashSet<>();
        GameListener gameListener = new GameEventsListener("a", null,null,null,null);
        gameListeners.add(gameListener);
        return gameListeners;
    }
}

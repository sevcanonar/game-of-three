package com.challenge.service.player;

import com.challenge.event.GameEvent;

import java.util.EventListener;

public interface GameListener extends EventListener {
    void onGameEvent(GameEvent gameEvent);
}

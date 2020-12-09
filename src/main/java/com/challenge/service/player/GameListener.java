package com.challenge.service.player;

import com.challenge.event.GameEvent;
import com.challenge.event.GameStartEvent;
import com.challenge.event.GameYourTurnEvent;

import java.util.EventListener;

public interface GameListener extends EventListener {
    void onGameEvent(GameEvent gameEvent);
}

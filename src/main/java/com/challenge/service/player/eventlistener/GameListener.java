package com.challenge.service.player.eventlistener;

import com.challenge.event.GameEvent;
import com.challenge.event.PlayerEvent;

import java.util.EventListener;

public interface GameListener extends EventListener {
    PlayerEvent onGameEvent(GameEvent gameEvent);
}

package com.challenge.service.player;

import com.challenge.event.GameEvent;
import com.challenge.event.GameStartEvent;
import com.challenge.event.GameYourTurnEvent;

import java.util.EventListener;

public interface GameListener extends EventListener {
    void onGameYourTurnEvent(GameYourTurnEvent gameEvent);
    void onGameInformationEvent(GameEvent gameEvent);
    void onGameOverEvent(GameEvent gameEvent);
    void onGameStartEvent(GameStartEvent gameEvent);
    void onGameAutoManualInformationEvent(GameEvent gameEvent);
}

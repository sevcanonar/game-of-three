package com.challenge.player.listener;

import com.challenge.player.event.GameEvent;

import java.util.EventListener;

public interface GameListener extends EventListener {
    void onGameYourTurnEvent(GameEvent gameEvent);
    void onGameInformationEvent(GameEvent gameEvent);
    void onGameOverEvent(GameEvent gameEvent);
    void onGameStartEvent(GameEvent gameEvent);
    void onGameAutoManualInformationEvent(GameEvent gameEvent);
    void onGameStartInformationEvent(GameEvent gameEvent);
}

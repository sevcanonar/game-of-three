package com.challenge.player.event;


import com.challenge.player.constants.PlayerEventType;

public class GameYourTurnEvent extends GameEvent{
    public GameYourTurnEvent(String to, PlayerEventType playerEventType, String playerOutput, boolean isStarted) {
        super(to, playerEventType, playerOutput, isStarted);
    }
}

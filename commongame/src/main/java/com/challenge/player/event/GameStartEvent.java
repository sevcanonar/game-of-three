package com.challenge.player.event;


public class GameStartEvent extends GameEvent {
    public GameStartEvent(String to, String playerOutput) {
        super(to, playerOutput);
    }
}

package com.challenge.player.event;


public class GameOverEvent extends GameEvent {
    public GameOverEvent(String to, String playerOutput) {
        super(to, playerOutput);
    }
}

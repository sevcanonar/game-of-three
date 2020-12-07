package com.challenge.player.event;


public class GameStartInformationEvent extends GameEvent {
    public GameStartInformationEvent(String to, String playerOutput) {
        super(to, playerOutput);
    }
}


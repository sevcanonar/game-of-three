package com.challenge.event;


import com.challenge.constants.PlayerType;

public class GameStartEvent extends GameEvent {

    public GameStartEvent(String to, String playerOutput, PlayerType playerType) {
        super(to, playerOutput, playerType);
    }
}

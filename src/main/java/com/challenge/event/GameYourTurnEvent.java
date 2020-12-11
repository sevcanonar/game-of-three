package com.challenge.event;


import com.challenge.constants.PlayerType;

public class GameYourTurnEvent extends GameEvent {

    public GameYourTurnEvent(String to, String playerOutput, PlayerType playerType) {
        super(to, playerOutput, playerType);
    }

    public GameYourTurnEvent(String to, String playerOutput, PlayerType playerType, Integer playerInput) {
        super(to, playerOutput, playerType, playerInput);
    }
}

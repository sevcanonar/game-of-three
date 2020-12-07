package com.challenge.player.event;


import com.challenge.player.constants.PlayerEventType;

public class GameEvent {

    private String to;
    private PlayerEventType playerEventType;
    private String playerOutput;
    private boolean isFirstMove;

    public GameEvent(String to, PlayerEventType playerEventType, String playerOutput, boolean isFirstMove) {
        this.to = to;
        this.playerEventType = playerEventType;
        this.playerOutput = playerOutput;
        this.isFirstMove = isFirstMove;
    }

    public GameEvent(String to, String playerOutput) {
        this.to = to;
        this.playerOutput = playerOutput;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public PlayerEventType getEventType() {
        return playerEventType;
    }

    public void setEventType(PlayerEventType playerEventType) {
        this.playerEventType = playerEventType;
    }

    public String getPlayerOutput() {
        return playerOutput;
    }

    public void setPlayerOutput(String playerOutput) {
        this.playerOutput = playerOutput;
    }

    public boolean isFirstMove() {
        return isFirstMove;
    }

    public void setFirstMove(boolean firstMove) {
        isFirstMove = firstMove;
    }
}

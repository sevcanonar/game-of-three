package com.challenge.event;


import com.challenge.constants.PlayerType;

public class GameEvent {

    private String to;
    private String playerOutput;
    PlayerType playerType;
    Integer playerInput;


    public GameEvent(String to, String playerOutput) {
        this.to = to;
        this.playerOutput = playerOutput;
    }

    public GameEvent(String to, String playerOutput, PlayerType playerType) {
        this.to = to;
        this.playerOutput = playerOutput;
        this.playerType = playerType;
    }

    public GameEvent(String to, String playerOutput, PlayerType playerType, Integer playerInput) {
        this.to = to;
        this.playerOutput = playerOutput;
        this.playerType = playerType;
        this.playerInput = playerInput;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getPlayerOutput() {
        return playerOutput;
    }

    public void setPlayerOutput(String playerOutput) {
        this.playerOutput = playerOutput;
    }

    public PlayerType getPlayerType() {
        return playerType;
    }

    public void setPlayerType(PlayerType playerType) {
        this.playerType = playerType;
    }

    public Integer getPlayerInput() {
        return playerInput;
    }

    public void setPlayerInput(Integer playerInput) {
        this.playerInput = playerInput;
    }
}

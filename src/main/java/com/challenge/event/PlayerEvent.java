package com.challenge.event;


import com.challenge.constants.PlayerEventType;
import com.challenge.constants.PlayerType;

public class PlayerEvent {

    private String userName;
    private PlayerEventType playerEventType;
    private String playerInput;
    private PlayerType playerType;

    public PlayerEvent(String userName, PlayerEventType playerEventType, String playerInput) {
        this.userName = userName;
        this.playerEventType = playerEventType;
        this.playerInput = playerInput;
    }

    public String getUserName() {
        return userName;
    }

    public PlayerEventType getEventType() {
        return playerEventType;
    }

    public String getPlayerInput() {
        return playerInput;
    }

    public void setPlayerInput(String playerInput) {
        this.playerInput = playerInput;
    }

    public PlayerType getPlayerType() {
        return playerType;
    }

    public void setPlayerType(PlayerType playerType) {
        this.playerType = playerType;
    }
}

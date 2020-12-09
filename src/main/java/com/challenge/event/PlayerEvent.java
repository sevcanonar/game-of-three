package com.challenge.event;


import com.challenge.constants.PlayerEventType;
import com.challenge.constants.PlayerType;

public class PlayerEvent {

    private String userName;
    private PlayerEventType playerEventType;
    private String playerInput;
    private PlayerType playerType;

    public PlayerEvent(String userName, PlayerEventType playerEventType, String playerInput, PlayerType playerType) {
        this.userName = userName;
        this.playerEventType = playerEventType;
        this.playerInput = playerInput;
        this.playerType = playerType;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public PlayerEventType getEventType() {
        return playerEventType;
    }

    public void setEventType(PlayerEventType playerEventType) {
        this.playerEventType = playerEventType;
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

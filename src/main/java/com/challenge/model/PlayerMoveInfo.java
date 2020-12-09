package com.challenge.model;

import com.challenge.constants.PlayerType;

public class PlayerMoveInfo {

    Integer moveInput;
    Integer moveValue;
    boolean isStarted = false;
    PlayerType playerType = PlayerType.NONE;

    public PlayerMoveInfo(boolean isStarted) {
        this.isStarted = isStarted;
    }

    public PlayerMoveInfo(boolean isStarted, PlayerType playerType) {
        this.isStarted = isStarted;
        this.playerType = playerType;
    }
    public PlayerMoveInfo(Integer moveInput, Integer moveValue, boolean isStarted) {
        this.moveInput = moveInput;
        this.moveValue = moveValue;
        this.isStarted = isStarted;
    }

    public Integer getMoveInput() {
        return moveInput;
    }

    public void setMoveInput(Integer moveInput) {
        this.moveInput = moveInput;
    }

    public Integer getMoveValue() {
        return moveValue;
    }

    public void setMoveValue(Integer moveValue) {
        this.moveValue = moveValue;
    }

    public boolean isStarted() {
        return isStarted;
    }

    public void setStarted(boolean started) {
        isStarted = started;
    }

    public PlayerType getPlayerType() {
        return playerType;
    }

    public void setPlayerType(PlayerType playerType) {
        this.playerType = playerType;
    }

}

package com.challenge.game.model;

import com.challenge.game.constants.EventType;

import java.util.List;

public class PlayerEvent {

    private EventType eventType;
    private String from;
    private String to;
    private Integer value;
    private List<String> onlineUsers;
    private boolean gameResult;
    private boolean gameStatus;

    public PlayerEvent() {
    }

    public PlayerEvent(EventType eventType, String from, String to, Integer value, List<String> onlineUsers, boolean gameResult, boolean gameStatus) {
        this.eventType = eventType;
        this.from = from;
        this.to = to;
        this.value = value;
        this.onlineUsers = onlineUsers;
        this.gameResult = gameResult;
        this.gameStatus = gameStatus;
    }

    public EventType getEventType() {
        return eventType;
    }

    public void setEventType(EventType eventType) {
        this.eventType = eventType;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }

    public List<String> getOnlineUsers() {
        return onlineUsers;
    }

    public void setOnlineUsers(List<String> onlineUsers) {
        this.onlineUsers = onlineUsers;
    }

    public boolean isGameResult() {
        return gameResult;
    }

    public void setGameResult(boolean gameResult) {
        this.gameResult = gameResult;
    }

    public boolean isGameStatus() {
        return gameStatus;
    }

    public void setGameStatus(boolean gameStatus) {
        this.gameStatus = gameStatus;
    }
}

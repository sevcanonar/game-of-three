package com.challenge.game.model;

public class GameMessage {
    private String from;
    private String text;
    private String time;

    public GameMessage(String from, String text, String time) {
        this.from = from;
        this.text = text;
        this.time = time;
    }
}

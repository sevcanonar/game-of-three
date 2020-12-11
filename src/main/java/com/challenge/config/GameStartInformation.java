package com.challenge.config;

public class GameStartInformation {

    private static boolean isStarted = false;


    protected GameStartInformation() {
    }

    public static boolean getInstance() {
        return isStarted;
    }

    public static void setInstance(boolean started) {
        isStarted = started;
    }
}

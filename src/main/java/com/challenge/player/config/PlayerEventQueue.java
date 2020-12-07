package com.challenge.player.config;

import com.challenge.player.event.PlayerEvent;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;

public class PlayerEventQueue {

    private static BlockingQueue<PlayerEvent> queue = new LinkedBlockingDeque<>();

    protected PlayerEventQueue() {
    }

    public static BlockingQueue<PlayerEvent> getInstance() {
        return queue;
    }
}


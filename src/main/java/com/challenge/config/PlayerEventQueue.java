package com.challenge.config;

import com.challenge.event.PlayerEvent;
import org.springframework.stereotype.Component;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;

@Component
public class PlayerEventQueue {

    private static BlockingQueue<PlayerEvent> queue = new LinkedBlockingDeque<>();

    public PlayerEventQueue() {
    }

    public BlockingQueue<PlayerEvent> getInstance() {
        return queue;
    }
}


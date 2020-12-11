package com.challenge.integrator;

import org.springframework.stereotype.Component;

@Component
public class GameSocketServer {

    private final AsyncThreadService asyncThreadService;

    public GameSocketServer(AsyncThreadService asyncThreadService) {
        this.asyncThreadService = asyncThreadService;
    }

    public void start() {
        while (true)
            asyncThreadService.executeAsynchronously();
    }
}
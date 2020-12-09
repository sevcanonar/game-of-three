package com.challenge.integrator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class GameSocketServer {

    @Autowired
    AsyncThreadService asyncThreadService;

    public void start() {
        while (true)
            asyncThreadService.executeAsynchronously();
    }
}
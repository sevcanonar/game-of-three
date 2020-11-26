package com.challenge.integrator;

import com.challenge.service.AsyncThreadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class EchoSocketServer {

    @Autowired
    AsyncThreadService asyncThreadService;

    @PostConstruct
    public void start() {
        while (true)
            asyncThreadService.executeAsynchronously();
    }
}
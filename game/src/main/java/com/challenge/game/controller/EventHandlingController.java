package com.challenge.game.controller;

import com.challenge.game.model.PlayerEvent;
import com.challenge.game.service.eventhandling.PlayerEventHandlingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;

@Controller
public class EventHandlingController {

    @Autowired
    private ApplicationContext context;

    private PlayerEventHandlingService playerEventHandlingService;

    @MessageMapping("/chat")
    public void handlePlayerMessage(PlayerEvent playerEvent) throws Exception {
        playerEventHandlingService = context.getBean(PlayerEventHandlingService.class);
        playerEventHandlingService.handlePlayerEvent(playerEvent);

    }
}

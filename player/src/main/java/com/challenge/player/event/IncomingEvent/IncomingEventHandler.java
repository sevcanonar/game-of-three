package com.challenge.player.event.IncomingEvent;

import com.challenge.player.event.EventSender;
import com.challenge.player.model.PlayerEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class IncomingEventHandler {

    @Autowired
    GameInformationEventHandler gameInformationEventHandler;
    @Autowired
    YourTurnEventHandler yourTurnEventHandler;
    @Autowired
    GameOverEventHandler gameOverEventHandler;

    @Autowired
    EventSender eventSender;

    public void handleIncomingEvent(PlayerEvent playerEvent) {
        PlayerEvent playerResponseEvent = new PlayerEvent();
        switch (playerEvent.getEventType()) {
            case GAME_INFORMATION:
                playerResponseEvent = gameInformationEventHandler.handleIncomingEvent(playerEvent);
                break;
            case YOUR_TURN:
                playerResponseEvent = yourTurnEventHandler.handleIncomingEvent(playerEvent);
                break;
            case GAME_OVER:
                playerResponseEvent = gameOverEventHandler.handleIncomingEvent(playerEvent);
                break;
        }
        eventSender.sendEvent(playerResponseEvent);
    }
}

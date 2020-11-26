package com.challenge.player.event;

import com.challenge.player.model.PlayerEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.stereotype.Component;

@Component
public class EventSender {
    @Autowired
    ApplicationContext applicationContext;

    public void sendEvent(PlayerEvent playerEvent) {
        applicationContext.getBean(StompSession.class).send("/app/chat", playerEvent);
    }
}

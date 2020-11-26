package com.challenge.player.event.OutgoingEvent;

import com.challenge.player.constants.EventType;
import com.challenge.player.model.PlayerEvent;
import org.springframework.stereotype.Component;

@Component
public class UserLoginEventBuilder {
     public PlayerEvent buildEvent(String userName) {
         PlayerEvent playerEvent = new PlayerEvent();
         playerEvent.setFrom(userName);
         playerEvent.setEventType(EventType.USER_LOGIN);
         return playerEvent;
    }
}

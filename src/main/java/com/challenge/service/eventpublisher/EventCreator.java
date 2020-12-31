package com.challenge.service.eventpublisher;

import com.challenge.event.GameEvent;

public interface EventCreator {
    public void createEvent(GameEvent gameEvent);
}

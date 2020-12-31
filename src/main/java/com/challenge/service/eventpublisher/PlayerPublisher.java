package com.challenge.service.eventpublisher;

import com.challenge.event.PlayerEvent;

public interface PlayerPublisher {
    void publish(PlayerEvent playerEvent);
}

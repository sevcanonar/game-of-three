package com.challenge.service.eventpublisher;

import com.challenge.event.GameEvent;

import java.util.List;

public interface GamePublisher {

    void publish(List<GameEvent> gameEvents);

}

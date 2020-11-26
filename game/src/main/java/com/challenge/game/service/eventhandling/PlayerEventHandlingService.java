package com.challenge.game.service.eventhandling;

import com.challenge.game.model.PlayerEvent;

public interface PlayerEventHandlingService {
    void handlePlayerEvent(PlayerEvent playerEvent);
}

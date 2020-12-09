package com.challenge.service.game;

import com.challenge.event.PlayerEvent;
import com.challenge.model.PlayerMoveInfo;

import java.util.Map;

public interface PlayerEventHandlingService {
    void handle(PlayerEvent playerEvent, Map<String, PlayerMoveInfo> playerInformation);
}

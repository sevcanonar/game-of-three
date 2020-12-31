package com.challenge.service.game.eventhandler;

import com.challenge.event.GameEvent;
import com.challenge.event.PlayerEvent;
import com.challenge.model.PlayerMoveInfo;

import java.util.List;
import java.util.Map;

public interface PlayerEventHandlingService {
    List<GameEvent> handle(PlayerEvent playerEvent, Map<String, PlayerMoveInfo> playerInformation);
}

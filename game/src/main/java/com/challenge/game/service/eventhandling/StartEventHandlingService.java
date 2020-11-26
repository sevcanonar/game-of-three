package com.challenge.game.service.eventhandling;

import com.challenge.game.config.GameCache;
import com.challenge.game.constants.EventType;
import com.challenge.game.model.PlayerEvent;
import com.challenge.game.service.informer.PlayerInformerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class StartEventHandlingService {

    @Autowired
    PlayerInformerService playerInformerService;

    public void handlePlayerEvent(PlayerEvent playerEvent) {
        GameCache.playerEventHashMap.put(playerEvent.getFrom(), playerEvent);
        String opponentName = null;
        PlayerEvent opponentPlayerEvent = null;
        for (Map.Entry<String, PlayerEvent> entry : GameCache.playerEventHashMap.entrySet()) {
            if ((entry.getKey().equals(playerEvent.getFrom())) {
                opponentName = entry.getKey();
                opponentPlayerEvent = entry.getValue();
            }
        }

        PlayerEvent playerResponseEvent = new PlayerEvent();
        playerResponseEvent.setEventType(EventType.YOUR_TURN);
        playerResponseEvent.setValue(playerEvent.getValue());
        playerResponseEvent.setTo(opponentName);
        playerResponseEvent.setFrom(playerEvent.getFrom());
        List<String> onlineUsers = new ArrayList<>();
        GameCache.playerEventHashMap.forEach((k, v) -> onlineUsers.add(k));
        playerResponseEvent.setOnlineUsers(onlineUsers);
        GameCache.playerEventHashMap.put(playerResponseEvent.getTo(), playerEvent);
        playerInformerService.informPlayer(playerResponseEvent);
    }
}

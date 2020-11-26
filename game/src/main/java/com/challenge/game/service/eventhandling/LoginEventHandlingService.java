package com.challenge.game.service.eventhandling;

import com.challenge.game.config.GameCache;
import com.challenge.game.constants.EventType;
import com.challenge.game.model.PlayerEvent;
import com.challenge.game.service.informer.PlayerInformerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class LoginEventHandlingService {

    @Autowired
    ApplicationContext applicationContext;

    @Autowired
    private PlayerInformerService playerInformerService;

    public void handlePlayerEvent(PlayerEvent playerEvent) {
        String opponentName = null;
        PlayerEvent opponentPlayerEvent = null;
        for (Map.Entry<String, PlayerEvent> entry : GameCache.playerEventHashMap.entrySet()) {
           opponentName = entry.getKey();
           opponentPlayerEvent = entry.getValue();
        }
        PlayerEvent playerResponseEvent = new PlayerEvent();
        playerResponseEvent.setEventType(EventType.GAME_INFORMATION);
        playerResponseEvent.setTo(playerEvent.getFrom());

        if(opponentPlayerEvent!=null && opponentPlayerEvent.getEventType().equals(EventType.START_GAME)) {
            playerResponseEvent.setGameStatus(true);
            playerResponseEvent.setValue(opponentPlayerEvent.getValue());
            playerResponseEvent.setFrom(opponentName);
        } else {
            playerResponseEvent.setGameStatus(false);
        }

        GameCache.playerEventHashMap.put(playerEvent.getFrom(), playerEvent);

        List<String> onlineUsers = new ArrayList<>();
        GameCache.playerEventHashMap.forEach((k, v) -> onlineUsers.add(k));
        playerResponseEvent.setOnlineUsers(onlineUsers);
        playerInformerService.informPlayer(playerResponseEvent);
    }
}

package com.challenge.service.game;


import com.challenge.config.PlayerEventQueue;
import com.challenge.event.GameEvent;
import com.challenge.event.PlayerEvent;
import com.challenge.model.PlayerMoveInfo;
import com.challenge.service.player.GameEventsConsumer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.BlockingQueue;

@Service
public class PlayerEventConsumerService extends Thread {
    private static final Logger LOG = LoggerFactory.getLogger(PlayerEventConsumerService.class);
    private static Map<String, PlayerMoveInfo> playerInformation = new HashMap<>(2);

    UserLoginHandlingService userLoginHandlingService;
    AutoManualSelectionHandlingService autoManualSelectionHandlingService;
    MovePlayedHandlingService movePlayedHandlingService;
    PlayerEventQueue playerEventQueue;
    BlockingQueue<PlayerEvent> playerEvents;
    GameEventsConsumer gameEventsConsumer;

    public PlayerEventConsumerService(UserLoginHandlingService userLoginHandlingService, AutoManualSelectionHandlingService autoManualSelectionHandlingService, MovePlayedHandlingService movePlayedHandlingService, PlayerEventQueue playerEventQueue, GameEventsConsumer gameEventsConsumer) {
        this.userLoginHandlingService = userLoginHandlingService;
        this.autoManualSelectionHandlingService = autoManualSelectionHandlingService;
        this.movePlayedHandlingService = movePlayedHandlingService;
        this.playerEventQueue = playerEventQueue;
        this.playerEvents = this.playerEventQueue.getInstance();
        this.gameEventsConsumer = gameEventsConsumer;
    }

    @Override
    public void run() {
        PlayerEvent playerEvent;
        PlayerEventHandlingService playerEventHandlingService;
        try {
            while ((playerEvent = playerEvents.take()) != null) {
                switch (playerEvent.getEventType()) {
                    case USER_LOGIN:
                        playerEventHandlingService = userLoginHandlingService;
                        break;
                    case AUTO_MANUAL_SELECTION:
                        playerEventHandlingService = autoManualSelectionHandlingService;
                        break;
                    case MOVE_IS_PLAYED:
                        playerEventHandlingService = movePlayedHandlingService;
                        break;
                    default:
                        throw new IllegalStateException("Unexpected value: " + playerEvent.getEventType());
                }
                List<GameEvent> gameEvents = playerEventHandlingService.handle(playerEvent, playerInformation);
                sendGameEvents(gameEvents);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void sendGameEvents(List<GameEvent> gameEvents) {
        for (GameEvent gameEvent : gameEvents)
            gameEventsConsumer.createEvent(gameEvent);
    }
}
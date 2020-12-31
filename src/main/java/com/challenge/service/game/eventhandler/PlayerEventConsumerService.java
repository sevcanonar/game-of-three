package com.challenge.service.game.eventhandler;


import com.challenge.config.PlayerEventQueue;
import com.challenge.constants.ExceptionalMessages;
import com.challenge.event.GameEvent;
import com.challenge.event.PlayerEvent;
import com.challenge.model.PlayerMoveInfo;
import com.challenge.service.eventpublisher.GameEventsPublisher;
import com.challenge.service.eventpublisher.GamePublisher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

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
    GamePublisher gameEventsPublisher;
    BlockingQueue<PlayerEvent> playerEvents;

    public PlayerEventConsumerService(UserLoginHandlingService userLoginHandlingService,
                                      AutoManualSelectionHandlingService autoManualSelectionHandlingService,
                                      MovePlayedHandlingService movePlayedHandlingService,
                                      PlayerEventQueue playerEventQueue,
                                      GamePublisher gameEventsPublisher) {
        this.userLoginHandlingService = userLoginHandlingService;
        this.autoManualSelectionHandlingService = autoManualSelectionHandlingService;
        this.movePlayedHandlingService = movePlayedHandlingService;
        this.playerEventQueue = playerEventQueue;
        this.playerEvents = this.playerEventQueue.getInstance();
        this.gameEventsPublisher = gameEventsPublisher;
    }

    @Override
    public void run() {
        PlayerEvent incomingPlayerEvent;
        PlayerEventHandlingService playerEventHandlingService;
        try {
            while ((incomingPlayerEvent = playerEvents.take()) != null) {
                switch (incomingPlayerEvent.getEventType()) {
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
                        throw new IllegalStateException(ExceptionalMessages.UNEXPECTED_VALUE + incomingPlayerEvent.getEventType());
                }
                List<GameEvent> gameEvents = playerEventHandlingService.handle(incomingPlayerEvent, playerInformation);
                gameEventsPublisher.publish(gameEvents);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
package com.challenge.service.game;


import com.challenge.config.PlayerEventQueue;
import com.challenge.event.PlayerEvent;
import com.challenge.model.PlayerMoveInfo;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.BlockingQueue;

@Service
public class PlayerEventConsumerService extends Thread {
    private static Map<String, PlayerMoveInfo> playerInformation = new HashMap<>(2);

    UserLoginHandlingService userLoginHandlingService;
    AutoManualSelectionHandlingService autoManualSelectionHandlingService;
    MovePlayedHandlingService movePlayedHandlingService;
    PlayerEventQueue playerEventQueue;
    BlockingQueue<PlayerEvent> playerEvents;

    public PlayerEventConsumerService(UserLoginHandlingService userLoginHandlingService, AutoManualSelectionHandlingService autoManualSelectionHandlingService, MovePlayedHandlingService movePlayedHandlingService, PlayerEventQueue playerEventQueue) {
        this.userLoginHandlingService = userLoginHandlingService;
        this.autoManualSelectionHandlingService = autoManualSelectionHandlingService;
        this.movePlayedHandlingService = movePlayedHandlingService;
        this.playerEventQueue = playerEventQueue;
        this.playerEvents = this.playerEventQueue.getInstance();
    }

    @Override
    public void run() {
        while (true) {
            try {
                PlayerEvent playerEvent = playerEvents.take();
                if (playerEvent == null)
                    break;

                switch (playerEvent.getEventType()) {
                    case USER_LOGIN:
                        userLoginHandlingService.handle(playerEvent, playerInformation);
                        break;
                    case AUTO_MANUAL_SELECTION:
                        autoManualSelectionHandlingService.handle(playerEvent, playerInformation);
                        break;
                    case MOVE_IS_PLAYED:
                        movePlayedHandlingService.handle(playerEvent, playerInformation);
                        break;
                }
            } catch (InterruptedException interruptedException) {
                interruptedException.printStackTrace();
            }
        }
    }
}
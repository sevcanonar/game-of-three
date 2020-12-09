package com.challenge.service.game;


import com.challenge.config.PlayerEventQueue;
import com.challenge.event.PlayerEvent;
import com.challenge.model.PlayerMoveInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.BlockingQueue;

@Service
public class PlayerEventConsumerService extends Thread {
    private static Map<String, PlayerMoveInfo> playerInformation = new HashMap<>(2);

    @Autowired
    UserLoginHandlingService userLoginHandlingService;

    @Autowired
    AutoManualSelectionHandlingService autoManualSelectionHandlingService;

    @Autowired
    MovePlayedHandlingService movePlayedHandlingService;

    @Autowired
    PlayerEventQueue playerEventQueue;

    @Override
    public void run() {
        BlockingQueue<PlayerEvent> playerEvents = playerEventQueue.getInstance();

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
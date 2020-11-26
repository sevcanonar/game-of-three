package com.challenge.game.service.eventhandling;

import com.challenge.game.model.PlayerEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PlayerEventHandlingOperator implements PlayerEventHandlingService {

    @Autowired
    LoginEventHandlingService loginEventHandlingService;
    @Autowired
    StartEventHandlingService startEventHandlingService;
    @Autowired
    MoveEventHandlingService moveEventHandlingService;

    @Override
    public void handlePlayerEvent(PlayerEvent playerEvent) {
        switch (playerEvent.getEventType()) {
            case USER_LOGIN:
                loginEventHandlingService.handlePlayerEvent(playerEvent);
                break;
            case START_GAME:
                startEventHandlingService.handlePlayerEvent(playerEvent);
                break;
            case MOVE_IS_PLAYED:
                moveEventHandlingService.handlePlayerEvent(playerEvent);
                break;
        }
    }
}

package com.challenge.service.game;

import com.challenge.constants.PlayerType;
import com.challenge.event.*;
import com.challenge.service.player.GameEventsConsumer;
import com.challenge.model.PlayerMoveInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class UserLoginHandlingService implements PlayerEventHandlingService {

    @Autowired
    GameHandlingServiceHelper gameHandlingServiceHelper;

    @Autowired
    GameEventsConsumer gameEventsConsumer;

    @Override
    public void handle(PlayerEvent playerEvent, Map<String, PlayerMoveInfo> playerInformation) {
        String currentUserName = gameHandlingServiceHelper.getCurrentUserName(playerInformation);
        PlayerMoveInfo startedGameInfo = gameHandlingServiceHelper.startedGameInformation(playerInformation);
        if (currentUserName != null && currentUserName.equals(playerEvent.getUserName())) {
            gameEventsConsumer.createEvent(new GameOverEvent(playerEvent.getUserName(), "User name already exists. Please connect again."));
        } else {
            PlayerMoveInfo playerMoveInfo = new PlayerMoveInfo(false, PlayerType.NONE);
            playerInformation.put(playerEvent.getUserName(), playerMoveInfo);
            gameEventsConsumer.createEvent(new GameInformationEvent(playerEvent.getUserName(), "You logged in."));
            if (startedGameInfo.isStarted()) {
                gameEventsConsumer.createEvent(new GameInformationEvent(playerEvent.getUserName(), "There is a game already started."));
                PlayerMoveInfo loggedInUserInfo = playerInformation.get(playerEvent.getUserName());
                loggedInUserInfo.setMoveInput(startedGameInfo.getMoveValue());
                playerInformation.put(playerEvent.getUserName(), loggedInUserInfo);
                gameEventsConsumer.createEvent(new GameAutoManualInformationEvent(playerEvent.getUserName(), "Select your play type"));
            } else
                gameEventsConsumer.createEvent(new GameAutoManualInformationEvent(playerEvent.getUserName(), "Select your play type"));
        }
    }
}

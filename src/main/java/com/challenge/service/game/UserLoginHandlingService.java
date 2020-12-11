package com.challenge.service.game;

import com.challenge.config.GameStartInformation;
import com.challenge.constants.PlayerType;
import com.challenge.event.GameAutoManualInformationEvent;
import com.challenge.event.GameInformationEvent;
import com.challenge.event.GameOverEvent;
import com.challenge.event.PlayerEvent;
import com.challenge.model.PlayerMoveInfo;
import com.challenge.service.player.GameEventsConsumer;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class UserLoginHandlingService implements PlayerEventHandlingService {

    GameHandlingServiceHelper gameHandlingServiceHelper;

    GameEventsConsumer gameEventsConsumer;

    public UserLoginHandlingService(GameHandlingServiceHelper gameHandlingServiceHelper, GameEventsConsumer gameEventsConsumer) {
        this.gameHandlingServiceHelper = gameHandlingServiceHelper;
        this.gameEventsConsumer = gameEventsConsumer;
    }


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
            if (GameStartInformation.getInstance()) {
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

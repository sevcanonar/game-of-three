package com.challenge.service.game;

import com.challenge.config.GameStartInformation;
import com.challenge.constants.PlayerType;
import com.challenge.constants.PlayerMessages;
import com.challenge.event.GameAutoManualInformationEvent;
import com.challenge.event.GameInformationEvent;
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
        PlayerMoveInfo startedGameInfo = gameHandlingServiceHelper.startedGameInformation(playerInformation);
        PlayerMoveInfo playerMoveInfo = new PlayerMoveInfo(false, PlayerType.NONE);
        playerInformation.put(playerEvent.getUserName(), playerMoveInfo);
        gameEventsConsumer.createEvent(new GameInformationEvent(playerEvent.getUserName(), PlayerMessages.YOU_LOGGED_IN));
        if (GameStartInformation.getInstance()) {
            gameEventsConsumer.createEvent(new GameInformationEvent(playerEvent.getUserName(), PlayerMessages.THERE_IS_A_GAME_ALREADY_STARTED));
            PlayerMoveInfo loggedInUserInfo = playerInformation.get(playerEvent.getUserName());
            loggedInUserInfo.setMoveInput(startedGameInfo.getMoveValue());
            playerInformation.put(playerEvent.getUserName(), loggedInUserInfo);
            gameEventsConsumer.createEvent(new GameAutoManualInformationEvent(playerEvent.getUserName(), PlayerMessages.SELECT_YOUR_PLAY_TYPE));
        } else
            gameEventsConsumer.createEvent(new GameAutoManualInformationEvent(playerEvent.getUserName(), PlayerMessages.SELECT_YOUR_PLAY_TYPE));
    }
}

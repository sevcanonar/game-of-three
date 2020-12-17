package com.challenge.service.game;

import com.challenge.config.GameStartInformation;
import com.challenge.constants.PlayerType;
import com.challenge.constants.PlayerMessages;
import com.challenge.event.GameInformationEvent;
import com.challenge.event.GameStartEvent;
import com.challenge.event.GameYourTurnEvent;
import com.challenge.event.PlayerEvent;
import com.challenge.model.PlayerMoveInfo;
import com.challenge.service.player.GameEventsConsumer;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class AutoManualSelectionHandlingService implements PlayerEventHandlingService {

    GameHandlingServiceHelper gameHandlingServiceHelper;
    GameEventsConsumer gameEventsConsumer;

    public AutoManualSelectionHandlingService(GameHandlingServiceHelper gameHandlingServiceHelper, GameEventsConsumer gameEventsConsumer) {
        this.gameHandlingServiceHelper = gameHandlingServiceHelper;
        this.gameEventsConsumer = gameEventsConsumer;
    }

    @Override
    public void handle(PlayerEvent playerEvent, Map<String, PlayerMoveInfo> playerInformation) {
        PlayerMoveInfo startedGameInfo = gameHandlingServiceHelper.startedGameInformation(playerInformation);
        PlayerMoveInfo userInfo = playerInformation.get(playerEvent.getUserName());
        userInfo.setMoveInput(startedGameInfo.getMoveValue());
        if (playerEvent.getPlayerInput().equals(PlayerMessages.AUTO)) {
            userInfo.setPlayerType(PlayerType.AUTO);
        } else {
            userInfo.setPlayerType(PlayerType.MANUAL);
        }
        playerInformation.put(playerEvent.getUserName(), userInfo);

        if (GameStartInformation.getInstance()) {
            gameEventsConsumer.createEvent(new GameInformationEvent(playerEvent.getUserName(), PlayerMessages.THERE_IS_A_GAME_ALREADY_STARTED));
            gameEventsConsumer.createEvent(new GameYourTurnEvent(playerEvent.getUserName(), PlayerMessages.OPPONENT_START_VALUE_IS + startedGameInfo.getMoveValue(), userInfo.getPlayerType(), startedGameInfo.getMoveValue()));
        } else {
            gameEventsConsumer.createEvent(new GameStartEvent(playerEvent.getUserName(), PlayerMessages.ENTER_A_VALUE_TO_START_THE_GAME, userInfo.getPlayerType()));
        }
    }
}

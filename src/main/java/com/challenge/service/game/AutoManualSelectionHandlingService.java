package com.challenge.service.game;

import com.challenge.config.GameStartInformation;
import com.challenge.constants.PlayerType;
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
        if (playerEvent.getPlayerInput().equals("A")) {
            userInfo.setPlayerType(PlayerType.AUTO);
        } else {
            userInfo.setPlayerType(PlayerType.MANUAL);
        }
        playerInformation.put(playerEvent.getUserName(), userInfo);

        if (GameStartInformation.getInstance()) {
            gameEventsConsumer.createEvent(new GameInformationEvent(playerEvent.getUserName(), "There is a game already started."));
            gameEventsConsumer.createEvent(new GameYourTurnEvent(playerEvent.getUserName(), "Opponent start value is " + startedGameInfo.getMoveValue(), userInfo.getPlayerType(), startedGameInfo.getMoveValue()));
        } else {
            gameEventsConsumer.createEvent(new GameStartEvent(playerEvent.getUserName(), "Enter a value to start the game.", userInfo.getPlayerType()));
        }
    }
}

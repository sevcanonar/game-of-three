package com.challenge.service.game;

import com.challenge.config.GameStartInformation;
import com.challenge.constants.PlayerMessages;
import com.challenge.constants.PlayerType;
import com.challenge.event.*;
import com.challenge.model.PlayerMoveInfo;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class AutoManualSelectionHandlingService implements PlayerEventHandlingService {

    GameHandlingServiceHelper gameHandlingServiceHelper;

    public AutoManualSelectionHandlingService(GameHandlingServiceHelper gameHandlingServiceHelper) {
        this.gameHandlingServiceHelper = gameHandlingServiceHelper;
    }

    @Override
    public List<GameEvent> handle(PlayerEvent playerEvent, Map<String, PlayerMoveInfo> playerInformation) {
        List<GameEvent> gameEvents = new ArrayList<>();
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
            gameEvents.add(new GameInformationEvent(playerEvent.getUserName(), PlayerMessages.THERE_IS_A_GAME_ALREADY_STARTED));
            gameEvents.add(new GameYourTurnEvent(playerEvent.getUserName(), PlayerMessages.OPPONENT_START_VALUE_IS + startedGameInfo.getMoveValue(), userInfo.getPlayerType(), startedGameInfo.getMoveValue()));
        } else {
            gameEvents.add(new GameStartEvent(playerEvent.getUserName(), PlayerMessages.ENTER_A_VALUE_TO_START_THE_GAME, userInfo.getPlayerType()));
        }
        return gameEvents;
    }
}

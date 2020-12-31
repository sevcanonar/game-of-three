package com.challenge.service.game.eventhandler;

import com.challenge.config.GameStartInformation;
import com.challenge.constants.ExceptionalMessages;
import com.challenge.event.GameEvent;
import com.challenge.event.PlayerEvent;
import com.challenge.model.PlayerMoveInfo;
import com.challenge.service.game.helper.PlayerInformationProvider;
import com.challenge.service.game.eventbuilder.GameEventsBuilderMediator;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class MovePlayedHandlingService implements PlayerEventHandlingService {

    PlayerInformationProvider playerInformationProvider;
    GameEventsBuilderMediator gameEventsBuilderMediator;

    public MovePlayedHandlingService(PlayerInformationProvider playerInformationProvider, GameEventsBuilderMediator gameEventsBuilderMediator) {
        this.playerInformationProvider = playerInformationProvider;
        this.gameEventsBuilderMediator = gameEventsBuilderMediator;
    }

    @Override
    public List<GameEvent> handle(PlayerEvent playerEvent, Map<String, PlayerMoveInfo> playerInformation) {
        String opponentName = playerInformationProvider.getOpponentName(playerEvent.getUserName(), playerInformation);
        PlayerMoveInfo playedPlayerMoveInfo = playerInformation.get(playerEvent.getUserName());
        try {
            playedPlayerMoveInfo.setMoveValue(Integer.valueOf(playerEvent.getPlayerInput()));
        } catch (NumberFormatException n) {
            return gameEventsBuilderMediator.build(playerEvent, playerInformation, null, ExceptionalMessages.INPUT_IS_NOT_SUITABLE);
        }
        createStartInformation(playedPlayerMoveInfo);
        playerInformation.put(playerEvent.getUserName(), playedPlayerMoveInfo);
        Integer moveOutput = calculateMoveOutput(playerEvent, playedPlayerMoveInfo);
        if (moveOutput != 1 && moveOutput != -1 && opponentName != null) {
            PlayerMoveInfo opponentPlayerMoveInfo = playerInformation.get(opponentName);
            opponentPlayerMoveInfo.setMoveInput(moveOutput);
            playerInformation.put(opponentName, opponentPlayerMoveInfo);
        }

        List<GameEvent> gameEvents = gameEventsBuilderMediator.build(playerEvent, playerInformation, moveOutput, null);

        if (moveOutput == 1) {
            GameStartInformation.setInstance(false);
            playerInformation.clear();
        }
        return gameEvents;
    }

    private void createStartInformation(PlayerMoveInfo playedPlayerMoveInfo) {
        if (playedPlayerMoveInfo.getMoveInput() == null) {
            playedPlayerMoveInfo.setStarted(true);
            GameStartInformation.setInstance(true);
        } else {
            playedPlayerMoveInfo.setStarted(false);
        }
    }

    public Integer calculateMoveOutput(PlayerEvent playerEvent, PlayerMoveInfo playedPlayerMoveInfo) {
        Integer moveOutput = -1;
        if (playedPlayerMoveInfo.getMoveInput() != null) {
            Integer moveSum = (playedPlayerMoveInfo.getMoveInput() + playedPlayerMoveInfo.getMoveValue());
            if (Math.floorMod(moveSum, 3) == 0) {
                moveOutput = moveSum / 3;
            }
        } else {
            moveOutput = Integer.valueOf(playerEvent.getPlayerInput());
        }
        return moveOutput;
    }
}

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
public class MovePlayedHandlingService implements PlayerEventHandlingService {

    GameHandlingServiceHelper gameHandlingServiceHelper;

    public MovePlayedHandlingService(GameHandlingServiceHelper gameHandlingServiceHelper) {
        this.gameHandlingServiceHelper = gameHandlingServiceHelper;
    }

    @Override
    public List<GameEvent> handle(PlayerEvent playerEvent, Map<String, PlayerMoveInfo> playerInformation) {
        List<GameEvent> gameEvents = new ArrayList<>();
        String opponentName = gameHandlingServiceHelper.getOpponent(playerEvent.getUserName(), playerInformation);
        //update for current move
        PlayerMoveInfo playedPlayerMoveInfo = playerInformation.get(playerEvent.getUserName());
        try {
            playedPlayerMoveInfo.setMoveValue(Integer.valueOf(playerEvent.getPlayerInput()));
        } catch (NumberFormatException n) {
            gameEvents.add(new GameYourTurnEvent(playerEvent.getUserName(), PlayerMessages.INPUT_IS_NOT_SUITABLE, playedPlayerMoveInfo.getPlayerType()));
            return gameEvents;
        }
        if (playedPlayerMoveInfo.getMoveInput() == null) {
            playedPlayerMoveInfo.setStarted(true);
            GameStartInformation.setInstance(true);
        } else {
            playedPlayerMoveInfo.setStarted(false);
        }
        playerInformation.put(playerEvent.getUserName(), playedPlayerMoveInfo);
        Integer moveOutput = calculateMoveOutput(playerEvent, playedPlayerMoveInfo);
        gameEvents.add(new GameInformationEvent(playerEvent.getUserName(), "You played with :" + playedPlayerMoveInfo.getMoveValue() + ". And output is " + moveOutput));

        switch (moveOutput) {
            case -1:
                gameEvents.add(new GameYourTurnEvent(playerEvent.getUserName(), PlayerMessages.INPUT_DOES_NOT_RESULT_IN, playedPlayerMoveInfo.getPlayerType()));
                break;
            case 1:
                GameStartInformation.setInstance(false);
                gameEvents.add(new GameOverEvent(playerEvent.getUserName(), PlayerMessages.YOU_WON));
                gameEvents.add(new GameOverEvent(opponentName, PlayerMessages.OPPONENT_SUCCEEDED_TO_CALCULATE_1_YOU_LOST));
                playerInformation.clear();
                break;
            default:
                if (opponentName != null) {
                    PlayerMoveInfo opponentPlayerMoveInfo = playerInformation.get(opponentName);
                    opponentPlayerMoveInfo.setMoveInput(moveOutput);
                    playerInformation.put(opponentName, opponentPlayerMoveInfo);
                    if (!opponentPlayerMoveInfo.getPlayerType().equals(PlayerType.NONE)) {
                        gameEvents.add(new GameYourTurnEvent(opponentName, PlayerMessages.OPPONENT_PLAYED_YOUR_INPUT_IS + moveOutput, opponentPlayerMoveInfo.getPlayerType(), moveOutput));
                    }
                    break;
                }
        }
        return gameEvents;
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

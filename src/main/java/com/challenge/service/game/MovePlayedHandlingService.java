package com.challenge.service.game;

import com.challenge.config.GameStartInformation;
import com.challenge.constants.PlayerType;
import com.challenge.constants.PlayerMessages;
import com.challenge.event.GameInformationEvent;
import com.challenge.event.GameOverEvent;
import com.challenge.event.GameYourTurnEvent;
import com.challenge.event.PlayerEvent;
import com.challenge.model.PlayerMoveInfo;
import com.challenge.service.player.GameEventsConsumer;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class MovePlayedHandlingService implements PlayerEventHandlingService {

    GameHandlingServiceHelper gameHandlingServiceHelper;
    GameEventsConsumer gameEventsConsumer;

    public MovePlayedHandlingService(GameHandlingServiceHelper gameHandlingServiceHelper, GameEventsConsumer gameEventsConsumer) {
        this.gameHandlingServiceHelper = gameHandlingServiceHelper;
        this.gameEventsConsumer = gameEventsConsumer;
    }

    @Override
    public void handle(PlayerEvent playerEvent, Map<String, PlayerMoveInfo> playerInformation) {
        String opponentName = gameHandlingServiceHelper.getOpponent(playerEvent.getUserName(), playerInformation);
        //update for current move
        PlayerMoveInfo playedPlayerMoveInfo = playerInformation.get(playerEvent.getUserName());
        playedPlayerMoveInfo.setMoveValue(Integer.valueOf(playerEvent.getPlayerInput()));
        if (playedPlayerMoveInfo.getMoveInput() == null) {
            playedPlayerMoveInfo.setStarted(true);
            GameStartInformation.setInstance(true);
        } else {
            playedPlayerMoveInfo.setStarted(false);
        }
        playerInformation.put(playerEvent.getUserName(), playedPlayerMoveInfo);
        Integer moveOutput = calculateMoveOutput(playerEvent, playedPlayerMoveInfo);
        gameEventsConsumer.createEvent(new GameInformationEvent(playerEvent.getUserName(), "You played with :" + playedPlayerMoveInfo.getMoveValue() + ". And output is " + moveOutput));

        switch (moveOutput) {
            case -1:
                gameEventsConsumer.createEvent(new GameYourTurnEvent(playerEvent.getUserName(), PlayerMessages.INPUT_DOES_NOT_RESULT_IN, playerEvent.getPlayerType()));
                return;
            case 1:
                GameStartInformation.setInstance(false);
                gameEventsConsumer.createEvent(new GameOverEvent(playerEvent.getUserName(), PlayerMessages.YOU_WON));
                gameEventsConsumer.createEvent(new GameOverEvent(opponentName, PlayerMessages.OPPONENT_SUCCEEDED_TO_CALCULATE_1_YOU_LOST));
                playerInformation.clear();
                break;
            default:
                if (opponentName != null) {
                    PlayerMoveInfo opponentPlayerMoveInfo = playerInformation.get(opponentName);
                    opponentPlayerMoveInfo.setMoveInput(moveOutput);
                    playerInformation.put(opponentName, opponentPlayerMoveInfo);
                    if (!opponentPlayerMoveInfo.getPlayerType().equals(PlayerType.NONE)) {
                        gameEventsConsumer.createEvent(new GameYourTurnEvent(opponentName, PlayerMessages.OPPONENT_PLAYED_YOUR_INPUT_IS + moveOutput, opponentPlayerMoveInfo.getPlayerType(), moveOutput));
                    }
                    break;
                }
        }
    }

    public Integer calculateMoveOutput(PlayerEvent playerEvent, PlayerMoveInfo playedPlayerMoveInfo) {
        Integer moveOutput = -1;
        if (playedPlayerMoveInfo.getMoveInput() != null) {
            Integer moveSum = (playedPlayerMoveInfo.getMoveInput() + playedPlayerMoveInfo.getMoveValue());
            if (Math.floorMod(moveSum, 3) == 0) {
                moveOutput = moveSum / 3;
            }
        } else{
            moveOutput = Integer.valueOf(playerEvent.getPlayerInput());
        }
        return moveOutput;
    }
}

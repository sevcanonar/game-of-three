package com.challenge.service.game;

import com.challenge.config.GameStartInformation;
import com.challenge.constants.PlayerType;
import com.challenge.event.*;
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
        //calculate
        Integer moveOutput;
        if (playedPlayerMoveInfo.getMoveInput() != null) {
            Integer moveSum = (playedPlayerMoveInfo.getMoveInput() + playedPlayerMoveInfo.getMoveValue());
            if (Math.floorMod(moveSum, 3) != 0) {
                gameEventsConsumer.createEvent(new GameYourTurnEvent(playerEvent.getUserName(), "Your input does not result in a number divisable with 3, please enter again.", playerEvent.getPlayerType()));
                return;
            } else
                moveOutput = moveSum / 3;
        } else
            moveOutput = Integer.valueOf(playerEvent.getPlayerInput());
        //inform player
        gameEventsConsumer.createEvent(new GameInformationEvent(playerEvent.getUserName(), "You played with :" + playedPlayerMoveInfo.getMoveValue() + ". And output is " + moveOutput));
        //update for opponent
        if (moveOutput.equals(1)) {
            GameStartInformation.setInstance(false);
            gameEventsConsumer.createEvent(new GameOverEvent(playerEvent.getUserName(), "You Won!"));
            gameEventsConsumer.createEvent(new GameOverEvent(opponentName, "Your opponent succeeded to calculate 1. You Lost!"));
            playerInformation.clear();
        } else {
            if (opponentName != null) {
                PlayerMoveInfo opponentPlayerMoveInfo = playerInformation.get(opponentName);
                opponentPlayerMoveInfo.setMoveInput(moveOutput);
                playerInformation.put(opponentName, opponentPlayerMoveInfo);
                if (opponentPlayerMoveInfo.getPlayerType().equals(PlayerType.NONE)) {
                    gameEventsConsumer.createEvent(new GameAutoManualInformationEvent(opponentName, "Select your play type"));
                } else
                    gameEventsConsumer.createEvent(new GameYourTurnEvent(opponentName, "Your opponent played. Your input is :" + moveOutput, opponentPlayerMoveInfo.getPlayerType(), moveOutput));
            }
        }
    }


}

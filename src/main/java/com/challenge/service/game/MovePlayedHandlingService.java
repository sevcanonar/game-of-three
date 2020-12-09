package com.challenge.service.game;

import com.challenge.constants.PlayerType;
import com.challenge.event.*;
import com.challenge.model.PlayerMoveInfo;
import com.challenge.service.player.GameEventConsumer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class MovePlayedHandlingService implements PlayerEventHandlingService {

    @Autowired
    GameHandlingServiceHelper gameHandlingServiceHelper;

    @Autowired
    GameEventConsumer gameEventConsumer;

    @Override
    public void handle(PlayerEvent playerEvent, Map<String, PlayerMoveInfo> playerInformation) {
        String opponentName = gameHandlingServiceHelper.getOpponent(playerEvent.getUserName(), playerInformation);
        //update for current move
        PlayerMoveInfo playedPlayerMoveInfo = playerInformation.get(playerEvent.getUserName());
        playedPlayerMoveInfo.setMoveValue(Integer.valueOf(playerEvent.getPlayerInput()));
        if (playedPlayerMoveInfo.getMoveInput() == null)
            playedPlayerMoveInfo.setStarted(true);
        else
            playedPlayerMoveInfo.setStarted(false);
        playerInformation.put(playerEvent.getUserName(), playedPlayerMoveInfo);
        //calculate
        Integer moveOutput;
        if (playedPlayerMoveInfo.getMoveInput() != null) {
            Integer moveSum = (playedPlayerMoveInfo.getMoveInput() + playedPlayerMoveInfo.getMoveValue());
            if (Math.floorMod(moveSum, 3) != 0) {
                gameEventConsumer.createEvent(new GameYourTurnEvent(playerEvent.getUserName(), "Your input does not result in a number divisable with 3, please enter again."));
                return;
            } else
                moveOutput = moveSum / 3;
        } else
            moveOutput = Integer.valueOf(playerEvent.getPlayerInput());
        //inform player
        gameEventConsumer.createEvent(new GameInformationEvent(playerEvent.getUserName(), "You played with :" + playedPlayerMoveInfo.getMoveValue() + ". And output is " + moveOutput));
        //update for opponent
        if (moveOutput.equals(1)) {
            gameEventConsumer.createEvent(new GameOverEvent(playerEvent.getUserName(), "You Won!"));
            gameEventConsumer.createEvent(new GameOverEvent(opponentName, "Your opponent succeeded to calculate 1. You Lost!"));
            playerInformation.clear();
        } else {
            if (opponentName != null) {
                PlayerMoveInfo opponentPlayerMoveInfo = playerInformation.get(opponentName);
                opponentPlayerMoveInfo.setMoveInput(moveOutput);
                playerInformation.put(opponentName, opponentPlayerMoveInfo);
                if (opponentPlayerMoveInfo.getPlayerType().equals(PlayerType.NONE)) {
                    gameEventConsumer.createEvent(new GameAutoManualInformationEvent(opponentName, "Select your play type"));
                } else
                    gameEventConsumer.createEvent(new GameYourTurnEvent(opponentName, "Your opponent played. Your input is :" + moveOutput, opponentPlayerMoveInfo.getPlayerType(), moveOutput));
            }
        }
    }


}

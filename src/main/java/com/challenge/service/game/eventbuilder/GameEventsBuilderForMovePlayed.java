package com.challenge.service.game.eventbuilder;

import com.challenge.constants.PlayerMessages;
import com.challenge.constants.PlayerType;
import com.challenge.event.*;
import com.challenge.model.PlayerMoveInfo;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class GameEventsBuilderForMovePlayed implements GameEventBuilder {

    Integer moveOutput;
    String opponentName;
    String exceptionalMessage;
    PlayerMoveInfo opponentMoveInfo;
    @Override
    public List<GameEvent> build(PlayerEvent playerEvent, PlayerMoveInfo playedUserInfo) {
        List<GameEvent> gameEvents = new ArrayList<>();

        if (exceptionalMessage != null) {
            gameEvents.add(new GameYourTurnEvent(playerEvent.getUserName(), exceptionalMessage, playedUserInfo.getPlayerType()));
            return gameEvents;
        }
        gameEvents.add(new GameInformationEvent(playerEvent.getUserName(), PlayerMessages.YOU_PLAYED_WITH + playedUserInfo.getMoveValue() + PlayerMessages.AND_OUTPUT_IS + moveOutput));
        switch (moveOutput) {
            case -1:
                gameEvents.add(new GameYourTurnEvent(playerEvent.getUserName(), PlayerMessages.INPUT_DOES_NOT_RESULT_IN, playedUserInfo.getPlayerType()));
                break;
            case 1:
                gameEvents.add(new GameOverEvent(playerEvent.getUserName(), PlayerMessages.YOU_WON));
                gameEvents.add(new GameOverEvent(opponentName, PlayerMessages.OPPONENT_SUCCEEDED_TO_CALCULATE_1_YOU_LOST));
                break;
            default:
                if (opponentName != null) {
                    if (!opponentMoveInfo.getPlayerType().equals(PlayerType.NONE)) {
                        gameEvents.add(new GameYourTurnEvent(opponentName, PlayerMessages.OPPONENT_PLAYED_YOUR_INPUT_IS + moveOutput, opponentMoveInfo.getPlayerType(), moveOutput));
                    }
                    break;
                }
                break;
        }
        return gameEvents;
    }

    public GameEventsBuilderForMovePlayed withMoveOutput(Integer moveOutput) {
        this.moveOutput = moveOutput;
        return this;
    }

    public GameEventsBuilderForMovePlayed withOpponentName(String opponentName) {
        this.opponentName = opponentName;
        return this;
    }

    public GameEventsBuilderForMovePlayed withExceptionalMessage(String exceptionalMessage) {
        this.exceptionalMessage = exceptionalMessage;
        return this;
    }

    public GameEventsBuilderForMovePlayed withOpponentMoveInfo(PlayerMoveInfo opponentMoveInfo) {
        this.opponentMoveInfo = opponentMoveInfo;
        return this;
    }
}

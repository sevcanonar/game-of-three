package com.challenge.service.game.eventbuilder;

import com.challenge.config.GameStartInformation;
import com.challenge.constants.PlayerMessages;
import com.challenge.event.*;
import com.challenge.model.PlayerMoveInfo;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class GameEventsBuilderForAutoManualSelection implements GameEventBuilder {
    PlayerMoveInfo startedGameInfo;

    @Override
    public List<GameEvent> build(PlayerEvent playerEvent, PlayerMoveInfo playedUserInfo) {
        List<GameEvent> gameEvents = new ArrayList<>();
        if (GameStartInformation.getInstance()) {
            gameEvents.add(new GameInformationEvent(playerEvent.getUserName(), PlayerMessages.THERE_IS_A_GAME_ALREADY_STARTED));
            gameEvents.add(new GameYourTurnEvent(playerEvent.getUserName(), PlayerMessages.OPPONENT_START_VALUE_IS + startedGameInfo.getMoveValue(), playedUserInfo.getPlayerType(), startedGameInfo.getMoveValue()));
        } else {
            gameEvents.add(new GameStartEvent(playerEvent.getUserName(), PlayerMessages.ENTER_A_VALUE_TO_START_THE_GAME, playedUserInfo.getPlayerType()));
        }
        return gameEvents;
    }

    public GameEventsBuilderForAutoManualSelection withStartedGameInfo(PlayerMoveInfo startedGameInfo) {
        this.startedGameInfo = startedGameInfo;
        return this;
    }
}

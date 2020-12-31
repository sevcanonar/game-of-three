package com.challenge.service.game.eventbuilder;

import com.challenge.config.GameStartInformation;
import com.challenge.constants.PlayerMessages;
import com.challenge.event.*;
import com.challenge.model.PlayerMoveInfo;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class GameEventsBuilderForUserLogin implements GameEventBuilder {

    @Override
    public List<GameEvent> build(PlayerEvent playerEvent, PlayerMoveInfo playedUserInfo) {
        List<GameEvent> gameEvents = new ArrayList<>();
        gameEvents.add(new GameInformationEvent(playerEvent.getUserName(), PlayerMessages.YOU_LOGGED_IN));
        if (GameStartInformation.getInstance()) {
            gameEvents.add(new GameInformationEvent(playerEvent.getUserName(), PlayerMessages.THERE_IS_A_GAME_ALREADY_STARTED));
        }
        gameEvents.add(new GameAutoManualInformationEvent(playerEvent.getUserName(), PlayerMessages.SELECT_YOUR_PLAY_TYPE));
        return gameEvents;
    }
}

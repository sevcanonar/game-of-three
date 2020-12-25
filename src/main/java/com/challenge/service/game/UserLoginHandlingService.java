package com.challenge.service.game;

import com.challenge.config.GameStartInformation;
import com.challenge.constants.PlayerMessages;
import com.challenge.constants.PlayerType;
import com.challenge.event.GameAutoManualInformationEvent;
import com.challenge.event.GameEvent;
import com.challenge.event.GameInformationEvent;
import com.challenge.event.PlayerEvent;
import com.challenge.model.PlayerMoveInfo;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class UserLoginHandlingService implements PlayerEventHandlingService {

    GameHandlingServiceHelper gameHandlingServiceHelper;

    public UserLoginHandlingService(GameHandlingServiceHelper gameHandlingServiceHelper) {
        this.gameHandlingServiceHelper = gameHandlingServiceHelper;
    }


    @Override
    public List<GameEvent> handle(PlayerEvent playerEvent, Map<String, PlayerMoveInfo> playerInformation) {
        List<GameEvent> gameEvents = new ArrayList<>();
        PlayerMoveInfo startedGameInfo = gameHandlingServiceHelper.startedGameInformation(playerInformation);
        PlayerMoveInfo playerMoveInfo = new PlayerMoveInfo(false, PlayerType.NONE);
        playerInformation.put(playerEvent.getUserName(), playerMoveInfo);
        gameEvents.add(new GameInformationEvent(playerEvent.getUserName(), PlayerMessages.YOU_LOGGED_IN));
        if (GameStartInformation.getInstance()) {
            gameEvents.add(new GameInformationEvent(playerEvent.getUserName(), PlayerMessages.THERE_IS_A_GAME_ALREADY_STARTED));
            PlayerMoveInfo loggedInUserInfo = playerInformation.get(playerEvent.getUserName());
            loggedInUserInfo.setMoveInput(startedGameInfo.getMoveValue());
            playerInformation.put(playerEvent.getUserName(), loggedInUserInfo);
        }
        gameEvents.add(new GameAutoManualInformationEvent(playerEvent.getUserName(), PlayerMessages.SELECT_YOUR_PLAY_TYPE));
        return gameEvents;
    }
}

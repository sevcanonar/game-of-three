package com.challenge.service.game.eventhandler;

import com.challenge.config.GameStartInformation;
import com.challenge.constants.PlayerType;
import com.challenge.event.GameEvent;
import com.challenge.event.PlayerEvent;
import com.challenge.model.PlayerMoveInfo;
import com.challenge.service.game.helper.PlayerInformationProvider;
import com.challenge.service.game.eventbuilder.GameEventsBuilderMediator;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class UserLoginHandlingService implements PlayerEventHandlingService {

    PlayerInformationProvider playerInformationProvider;
    GameEventsBuilderMediator gameEventsBuilderMediator;

    public UserLoginHandlingService(PlayerInformationProvider playerInformationProvider, GameEventsBuilderMediator gameEventsBuilderMediator) {
        this.playerInformationProvider = playerInformationProvider;
        this.gameEventsBuilderMediator = gameEventsBuilderMediator;
    }


    @Override
    public List<GameEvent> handle(PlayerEvent playerEvent, Map<String, PlayerMoveInfo> playerInformation) {
        PlayerMoveInfo startedGameInfo = playerInformationProvider.startedGameInformation(playerInformation);
        PlayerMoveInfo playerMoveInfo = new PlayerMoveInfo(false, PlayerType.NONE);
        playerInformation.put(playerEvent.getUserName(), playerMoveInfo);
        if (GameStartInformation.getInstance()) {
            PlayerMoveInfo loggedInUserInfo = playerInformation.get(playerEvent.getUserName());
            loggedInUserInfo.setMoveInput(startedGameInfo.getMoveValue());
            playerInformation.put(playerEvent.getUserName(), loggedInUserInfo);
        }
        return gameEventsBuilderMediator.build(playerEvent, playerInformation, null,null);
    }
}

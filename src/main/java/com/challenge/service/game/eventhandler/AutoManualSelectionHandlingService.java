package com.challenge.service.game.eventhandler;

import com.challenge.constants.PlayerMessages;
import com.challenge.constants.PlayerType;
import com.challenge.event.GameEvent;
import com.challenge.event.PlayerEvent;
import com.challenge.model.PlayerMoveInfo;
import com.challenge.service.game.eventbuilder.GameEventsBuilderMediator;
import com.challenge.service.game.helper.PlayerInformationProvider;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class AutoManualSelectionHandlingService implements PlayerEventHandlingService {

    PlayerInformationProvider playerInformationProvider;
    GameEventsBuilderMediator gameEventsBuilderMediator;


    public AutoManualSelectionHandlingService(PlayerInformationProvider playerInformationProvider, GameEventsBuilderMediator gameEventsBuilderMediator) {
        this.playerInformationProvider = playerInformationProvider;
        this.gameEventsBuilderMediator = gameEventsBuilderMediator;
    }

    @Override
    public List<GameEvent> handle(PlayerEvent playerEvent, Map<String, PlayerMoveInfo> playerInformation) {
        PlayerMoveInfo startedGameInfo = playerInformationProvider.startedGameInformation(playerInformation);
        PlayerMoveInfo userInfo = playerInformation.get(playerEvent.getUserName());
        userInfo.setMoveInput(startedGameInfo.getMoveValue());
        userInfo.setPlayerType(playerEvent.getPlayerInput().equals(PlayerMessages.AUTO) ? PlayerType.AUTO : PlayerType.MANUAL);
        playerInformation.put(playerEvent.getUserName(), userInfo);

        List<GameEvent> gameEvents = gameEventsBuilderMediator.build(playerEvent, playerInformation, null, null);
        return gameEvents;
    }

}

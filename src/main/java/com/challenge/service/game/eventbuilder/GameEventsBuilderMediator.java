package com.challenge.service.game.eventbuilder;

import com.challenge.event.GameEvent;
import com.challenge.event.PlayerEvent;
import com.challenge.model.PlayerMoveInfo;
import com.challenge.service.game.helper.PlayerInformationProvider;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public class GameEventsBuilderMediator {

    PlayerInformationProvider playerInformationProvider;
    GameEventBuilder gameEventBuilder;
    GameEventsBuilderForAutoManualSelection gameEventsBuilderForAutoManualSelection;
    GameEventsBuilderForUserLogin gameEventsBuilderForUserLogin;
    GameEventsBuilderForMovePlayed gameEventsBuilderForMovePlayed;

    public GameEventsBuilderMediator(PlayerInformationProvider playerInformationProvider,
                                     GameEventsBuilderForAutoManualSelection gameEventsBuilderForAutoManualSelection,
                                     GameEventsBuilderForUserLogin gameEventsBuilderForUserLogin,
                                     GameEventsBuilderForMovePlayed gameEventsBuilderForMovePlayed) {
        this.playerInformationProvider = playerInformationProvider;
        this.gameEventsBuilderForAutoManualSelection = gameEventsBuilderForAutoManualSelection;
        this.gameEventsBuilderForUserLogin = gameEventsBuilderForUserLogin;
        this.gameEventsBuilderForMovePlayed = gameEventsBuilderForMovePlayed;
    }

    public List<GameEvent> build(PlayerEvent playerEvent, Map<String, PlayerMoveInfo> playerInformation, Integer moveOutput, String exceptionalMessage) {
        PlayerMoveInfo startedGameInfo = playerInformationProvider.startedGameInformation(playerInformation);
        PlayerMoveInfo playedUserInfo = playerInformation.get(playerEvent.getUserName());
        String opponentName = playerInformationProvider.getOpponentName(playerEvent.getUserName(), playerInformation);
        switch (playerEvent.getEventType()) {
            case AUTO_MANUAL_SELECTION:
                gameEventBuilder = gameEventsBuilderForAutoManualSelection.withStartedGameInfo(startedGameInfo);
                break;
            case USER_LOGIN:
                gameEventBuilder = gameEventsBuilderForUserLogin;
                break;
            case MOVE_IS_PLAYED:
                gameEventBuilder = gameEventsBuilderForMovePlayed.withMoveOutput(moveOutput).withOpponentName(opponentName).withExceptionalMessage(exceptionalMessage);
                if (opponentName != null)
                    gameEventBuilder = gameEventsBuilderForMovePlayed.withOpponentMoveInfo(playerInformationProvider.getOpponentMoveInfo(opponentName, playerInformation));
                break;
        }
        return gameEventBuilder.build(playerEvent, playedUserInfo);
    }
}

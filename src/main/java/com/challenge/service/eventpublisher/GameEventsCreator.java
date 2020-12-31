package com.challenge.service.eventpublisher;

import com.challenge.config.GameListenersPerPlayer;
import com.challenge.constants.GameListenerType;
import com.challenge.event.*;
import com.challenge.service.eventpublisher.PlayerPublisher;
import com.challenge.service.initialization.EventRegisterer;
import com.challenge.service.initialization.GameEventsRegisterer;
import com.challenge.service.player.eventlistener.GameListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class GameEventsCreator implements EventCreator {

    GameListener gameListener;

    EventRegisterer gameEventsRegisterer;
    GameListenersPerPlayer gameListenersPerPlayer;
    PlayerPublisher playerEventPublisher;

    public GameEventsCreator(EventRegisterer gameEventsRegisterer, GameListenersPerPlayer gameListenersPerPlayer, PlayerPublisher playerEventPublisher) {
        this.gameEventsRegisterer = gameEventsRegisterer;
        this.gameListenersPerPlayer = gameListenersPerPlayer;
        this.playerEventPublisher = playerEventPublisher;
    }

    @Override
    public void createEvent(GameEvent gameEvent) {
        Map<GameListenerType, GameListener> gameListenerMapForUser = gameListenersPerPlayer.getInstance().get(gameEvent.getTo());
        if (gameEvent instanceof GameYourTurnEvent) {
            gameListener = gameListenerMapForUser.get(GameListenerType.YOURTURN);
        }
        if (gameEvent instanceof GameInformationEvent) {
            gameListener = gameListenerMapForUser.get(GameListenerType.INFO);
        }
        if (gameEvent instanceof GameOverEvent) {
            gameListener = gameListenerMapForUser.get(GameListenerType.GAMEOVER);
            gameEventsRegisterer.deRegisterAllListeners(gameEvent.getTo());
        }
        if (gameEvent instanceof GameStartEvent) {
            gameListener = gameListenerMapForUser.get(GameListenerType.START);
        }
        if (gameEvent instanceof GameAutoManualInformationEvent) {
            gameListener = gameListenerMapForUser.get(GameListenerType.AUTOMAN);
        }
        PlayerEvent playerEvent = gameListener.onGameEvent(gameEvent);
        playerEventPublisher.publish(playerEvent);
    }

}

package com.challenge.service.player;

import com.challenge.config.PlayerEventQueue;
import com.challenge.constants.ExceptionalMessages;
import com.challenge.constants.PlayerEventType;
import com.challenge.constants.PlayerMessages;
import com.challenge.event.GameEvent;
import com.challenge.event.PlayerEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class GameAutoManualInfoEventListener extends GameEventsListener implements GameListener {
    private static final Logger LOG = LoggerFactory.getLogger(GameAutoManualInfoEventListener.class);

    public GameAutoManualInfoEventListener(PrintWriter out, Scanner in, Socket clientSocket, PlayerEventQueue playerEventQueue) {
        super(out, in, clientSocket, playerEventQueue);
    }

    @Override
    public void onGameEvent(GameEvent gameEvent) {
        try {
            out.println(gameEvent.getPlayerOutput());
            String userInput;
            while (true) {
                out.println(PlayerMessages.PLEASE_ENTER_ONE_OF_A_M);
                userInput = in.nextLine();
                if (userInput != null && (userInput.equals(PlayerMessages.AUTO) || userInput.equals(PlayerMessages.MANUAL))) {
                    break;
                }
            }
            playerEventQueue.getInstance().put(new PlayerEvent(gameEvent.getTo(), PlayerEventType.AUTO_MANUAL_SELECTION, userInput));
        } catch (InterruptedException e) {
            LOG.error(ExceptionalMessages.ERROR_WHILE_GETTING_PLAYER_TYPE, e.getMessage());
            Thread.currentThread().interrupt();
        }
    }
}

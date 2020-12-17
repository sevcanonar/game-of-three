package com.challenge.service.player;

import com.challenge.config.PlayerEventQueue;
import com.challenge.constants.ExceptionalMessages;
import com.challenge.event.GameEvent;
import com.challenge.service.game.PlayerEventConsumerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class GameOverEventListener extends GameEventsListener implements GameListener {

    private static final Logger LOG =   LoggerFactory.getLogger(GameOverEventListener.class);

    public GameOverEventListener(PrintWriter out, Scanner in, Socket clientSocket, PlayerEventQueue playerEventQueue) {
        super(out, in, clientSocket, playerEventQueue);
    }

    @Override
    public void onGameEvent(GameEvent gameEvent) {
        out.println(gameEvent.getPlayerOutput());
        try {
            in.close();
            out.close();
            clientSocket.close();
        } catch (IOException e) {
            LOG.error(ExceptionalMessages.CLIENT_THREAD_INTERRUPTED, e.getMessage());
            Thread.currentThread().interrupt();
        }
    }
}

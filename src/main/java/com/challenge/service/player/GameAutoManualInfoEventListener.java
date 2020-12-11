package com.challenge.service.player;

import com.challenge.config.GameStartInformation;
import com.challenge.config.PlayerEventQueue;
import com.challenge.constants.PlayerEventType;
import com.challenge.constants.PlayerType;
import com.challenge.event.GameEvent;
import com.challenge.event.PlayerEvent;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

public class GameAutoManualInfoEventListener extends GameEventsListener implements GameListener {

    public GameAutoManualInfoEventListener(PrintWriter out, BufferedReader in, Socket clientSocket, PlayerEventQueue playerEventQueue) {
        super(out, in, clientSocket, playerEventQueue);
        playerType = PlayerType.NONE;
    }

    @Override
    public void onGameEvent(GameEvent gameEvent) {
        try {
            out.println(gameEvent.getPlayerOutput());
            String userInput = null;
            while (playerType.equals(PlayerType.NONE)) {
                out.println("Please enter one of {A, M}");
                userInput = in.readLine();
                if (userInput != null && (userInput.equals("A") || userInput.equals("M"))) {
                    break;
                }
            }
            playerType = userInput.equals("A") ? PlayerType.AUTO : PlayerType.MANUAL;
            playerEventQueue.getInstance().put(new PlayerEvent(gameEvent.getTo(), PlayerEventType.AUTO_MANUAL_SELECTION, userInput, playerType));
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}

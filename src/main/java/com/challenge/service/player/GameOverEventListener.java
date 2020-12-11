package com.challenge.service.player;

import com.challenge.config.PlayerEventQueue;
import com.challenge.constants.PlayerType;
import com.challenge.event.GameEvent;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

public class GameOverEventListener extends GameEventsListener implements GameListener {

    public GameOverEventListener(PrintWriter out, BufferedReader in, Socket clientSocket, PlayerEventQueue playerEventQueue) {
        super(out, in, clientSocket, playerEventQueue);
    }

    @Override
    public void onGameEvent(GameEvent gameEvent) {
        out.println(gameEvent.getPlayerOutput());
        try {
            out.close();
            clientSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

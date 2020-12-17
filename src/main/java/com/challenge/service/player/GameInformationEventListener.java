package com.challenge.service.player;

import com.challenge.config.PlayerEventQueue;
import com.challenge.event.GameEvent;

import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class GameInformationEventListener extends GameEventsListener implements GameListener {

    public GameInformationEventListener(PrintWriter out, Scanner in, Socket clientSocket, PlayerEventQueue playerEventQueue) {
        super(out, in, clientSocket, playerEventQueue);
    }

    @Override
    public void onGameEvent(GameEvent gameEvent) {
        out.println(gameEvent.getPlayerOutput());
    }
}

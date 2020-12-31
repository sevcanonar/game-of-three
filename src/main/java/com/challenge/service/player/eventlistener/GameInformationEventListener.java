package com.challenge.service.player.eventlistener;

import com.challenge.config.PlayerEventQueue;
import com.challenge.event.GameEvent;
import com.challenge.event.PlayerEvent;

import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class GameInformationEventListener extends GameEventsListener implements GameListener {

    public GameInformationEventListener(PrintWriter out, Scanner in, Socket clientSocket) {
        super(out, in, clientSocket);
    }

    @Override
    public PlayerEvent onGameEvent(GameEvent gameEvent) {
        out.println(gameEvent.getPlayerOutput());
        return null;
    }
}

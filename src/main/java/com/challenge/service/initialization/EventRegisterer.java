package com.challenge.service.initialization;

import com.challenge.constants.GameListenerType;
import com.challenge.service.player.eventlistener.GameListener;

import java.io.PrintWriter;
import java.net.Socket;
import java.util.Map;
import java.util.Scanner;

public interface EventRegisterer {
    void registerAllListeners(String userName, PrintWriter out, Scanner in, Socket clientSocket);

    void deRegisterAllListeners(String userName);

    Map<GameListenerType, GameListener> getGameListeners(String userName);
}

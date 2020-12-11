package com.challenge.service.player;


import com.challenge.config.PlayerEventQueue;
import com.challenge.constants.GameListenerType;
import com.challenge.constants.PlayerEventType;
import com.challenge.constants.PlayerType;
import com.challenge.event.PlayerEvent;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.BlockingQueue;

public class MultiplePlayerMeeterService extends Thread {

    PlayerEventQueue playerEventQueue;
    private Socket clientSocket;
    private GameEventsConsumer gameEventsConsumer;

    public MultiplePlayerMeeterService(ServerSocket socket, GameEventsConsumer gameEventsConsumer, PlayerEventQueue playerEventQueue) {
        try {
            this.clientSocket = socket.accept();
            this.gameEventsConsumer = gameEventsConsumer;
            this.playerEventQueue = playerEventQueue;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        BlockingQueue<PlayerEvent> playerEvents = this.playerEventQueue.getInstance();

        try {
            PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

            out.println("User Name:");
            String userName = in.readLine();
            if (userName != null) {
                playerEvents.put(new PlayerEvent(userName, PlayerEventType.USER_LOGIN, userName, PlayerType.NONE));
            }
            gameEventsConsumer.register(userName, GameListenerType.AUTOMAN, new GameAutoManualInfoEventListener(out, in, clientSocket, playerEventQueue));
            gameEventsConsumer.register(userName, GameListenerType.INFO, new GameInformationEventListener(out, in, clientSocket, playerEventQueue));
            gameEventsConsumer.register(userName, GameListenerType.START, new GameStartEventListener(out, in, clientSocket, playerEventQueue));
            gameEventsConsumer.register(userName, GameListenerType.YOURTURN, new GameYourTurnEventListener(out, in, clientSocket, playerEventQueue));
            gameEventsConsumer.register(userName, GameListenerType.GAMEOVER, new GameOverEventListener(out, in, clientSocket, playerEventQueue));

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}

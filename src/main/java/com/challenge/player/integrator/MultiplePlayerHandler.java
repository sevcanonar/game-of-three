package com.challenge.player.integrator;


import com.challenge.player.config.PlayerEventQueue;
import com.challenge.player.constants.PlayerEventType;
import com.challenge.player.constants.PlayerType;
import com.challenge.player.event.PlayerEvent;
import com.challenge.player.listener.GameEventRegistererCaller;
import com.challenge.player.listener.GameEventsListener;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.BlockingQueue;

@Service
@Scope("prototype")
public class MultiplePlayerHandler implements Runnable {
    private Socket clientSocket;
    private GameEventRegistererCaller gameEventRegistererCaller;
    private PrintWriter out;
    private BufferedReader in;

    public MultiplePlayerHandler(ServerSocket socket, GameEventRegistererCaller gameEventRegistererCaller) {
        try {
            this.clientSocket = socket.accept();
            this.gameEventRegistererCaller = gameEventRegistererCaller;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        BlockingQueue<PlayerEvent> playerEvents = PlayerEventQueue.getInstance();

        try {
            out = new PrintWriter(clientSocket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

            out.println("User Name:");
            String userName = in.readLine();
            if (userName != null) {
                playerEvents.put(new PlayerEvent(userName, PlayerEventType.USER_LOGIN, userName, PlayerType.NONE));
            }
            gameEventRegistererCaller.register(new GameEventsListener(userName, out, in, clientSocket));

            String userInput = "";

            out.println("current thread number is " + Thread.currentThread().getId());

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}

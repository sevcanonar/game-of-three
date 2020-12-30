package com.challenge.service.player;


import com.challenge.config.PlayerEventQueue;
import com.challenge.constants.ExceptionalMessages;
import com.challenge.constants.PlayerEventType;
import com.challenge.constants.PlayerMessages;
import com.challenge.constants.PlayerType;
import com.challenge.event.PlayerEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;
import java.util.concurrent.BlockingQueue;

public class MultiplePlayerMeeterService extends Thread {

    private static final Logger LOG = LoggerFactory.getLogger(MultiplePlayerMeeterService.class);

    PlayerEventQueue playerEventQueue;
    Socket clientSocket;
    GameEventsRegisterer gameEventsRegisterer;

    public MultiplePlayerMeeterService(ServerSocket socket, GameEventsRegisterer gameEventsRegisterer, PlayerEventQueue playerEventQueue) {
        try {
            this.clientSocket = socket.accept();
            this.gameEventsRegisterer = gameEventsRegisterer;
            this.playerEventQueue = playerEventQueue;
        } catch (Exception e) {
            LOG.debug(ExceptionalMessages.SOCKET_IS_CLOSED_BY_GAME);
        }
    }

    @Override
    public void run() {
        BlockingQueue<PlayerEvent> playerEvents = this.playerEventQueue.getInstance();

        try {
            PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
            Scanner in = new Scanner(clientSocket.getInputStream());

            while (true) {
                out.println(PlayerMessages.PLEASE_ENTER_YOUR_USER_NAME);
                String userName = in.nextLine();
                if (userName != null) {
                    if (gameEventsRegisterer.getGameListeners(userName) == null) {
                        playerEvents.put(new PlayerEvent(userName, PlayerEventType.USER_LOGIN, userName));
                        gameEventsRegisterer.registerAllListeners(userName, out, in, clientSocket, playerEventQueue);
                        break;
                    } else {
                        out.println(PlayerMessages.THERE_IS_ALREADY_A_USER_WITH_THIS_NAME);
                    }
                }
            }
        } catch (IOException | InterruptedException e) {
            LOG.error(ExceptionalMessages.ERROR_WHILE_MEETING_PLAYER, e.getMessage());
            Thread.currentThread().interrupt();
        }
    }
}

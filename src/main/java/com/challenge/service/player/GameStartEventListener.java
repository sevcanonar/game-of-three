package com.challenge.service.player;

import com.challenge.config.PlayerEventQueue;
import com.challenge.constants.PlayerEventType;
import com.challenge.constants.PlayerType;
import com.challenge.event.GameEvent;
import com.challenge.event.PlayerEvent;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Random;

public class GameStartEventListener extends GameEventsListener implements GameListener {

    public GameStartEventListener(PrintWriter out, BufferedReader in, Socket clientSocket, PlayerEventQueue playerEventQueue) {
        super(out, in, clientSocket, playerEventQueue);
    }

    @Override
    public void onGameEvent(GameEvent gameEvent) {
        try {
            out.println(gameEvent.getPlayerOutput());
            String userInput;
            if (gameEvent.getPlayerType().equals(PlayerType.AUTO)) {
                out.println("Playing automatically.");
                userInput = String.valueOf(new Random().nextInt(Integer.MAX_VALUE) + 2);
                out.println(userInput);
            } else {
                while (true) {
                    out.println("Please enter a number >=2");
                    userInput = in.readLine();
                    if (userInput != null && isValidInteger(userInput)) {
                        break;
                    }
                }
            }
            playerEventQueue.getInstance().put(new PlayerEvent(gameEvent.getTo(), PlayerEventType.MOVE_IS_PLAYED, userInput, playerType));
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    private boolean isValidInteger(String userInput) {
        Integer result = 0;
        try {
            result = Integer.parseInt(userInput);
        } catch (Exception e) {
            return false;
        }
        if (result < 2) {
            return false;
        }
        return true;
    }
}

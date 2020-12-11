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


public class GameYourTurnEventListener extends GameEventsListener implements GameListener {

    public GameYourTurnEventListener(PrintWriter out, BufferedReader in, Socket clientSocket, PlayerEventQueue playerEventQueue) {
        super(out, in, clientSocket, playerEventQueue);
    }

    @Override
    public void onGameEvent(GameEvent gameEvent) {
        try {
            out.println(gameEvent.getPlayerOutput());
            String userInput = null;
            if (gameEvent.getPlayerType().equals(PlayerType.AUTO)) {
                out.println("Playing automatically.");
                userInput = playRightMove(gameEvent.getPlayerInput());
                out.println(userInput);
            } else {
                while (GameStartInformation.getInstance()) {
                    out.println("Please enter one of {1,0,-1}");
                    userInput = in.readLine();
                    if (userInput != null && (userInput.equals("1") || userInput.equals("0") || userInput.equals("-1"))) {
                        break;
                    }
                }
            }
            playerEventQueue.getInstance().put(new PlayerEvent(gameEvent.getTo(), PlayerEventType.MOVE_IS_PLAYED, userInput, gameEvent.getPlayerType()));

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    private String playRightMove(Integer playerInput) {
        int i = -1;
        while (i <= 1) {
            if ((playerInput + i) % 3 == 0)
                break;
            i++;
        }
        return String.valueOf(i);
    }
}

package com.challenge.service.player;

import com.challenge.config.GameStartInformation;
import com.challenge.config.PlayerEventQueue;
import com.challenge.constants.ExceptionalMessages;
import com.challenge.constants.PlayerEventType;
import com.challenge.constants.PlayerMessages;
import com.challenge.constants.PlayerType;
import com.challenge.event.GameEvent;
import com.challenge.event.PlayerEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;


public class GameYourTurnEventListener extends GameEventsListener implements GameListener {

    private static final Logger LOG =   LoggerFactory.getLogger(GameYourTurnEventListener.class);

    public GameYourTurnEventListener(PrintWriter out, Scanner in, Socket clientSocket, PlayerEventQueue playerEventQueue) {
        super(out, in, clientSocket, playerEventQueue);
    }

    @Override
    public void onGameEvent(GameEvent gameEvent) {
        try {
            out.println(gameEvent.getPlayerOutput());
            String userInput = null;
            if (gameEvent.getPlayerType().equals(PlayerType.AUTO)) {
                out.println(PlayerMessages.PLAYING_AUTOMATICALLY);
                userInput = playRightMove(gameEvent.getPlayerInput());
                out.println(userInput);
            } else {
                while (GameStartInformation.getInstance()) {
                    out.println(PlayerMessages.ENTER_ONE_OF_1_0_1);
                    userInput = in.nextLine();
                    if ((userInput.equals(PlayerMessages.ONE) || userInput.equals(PlayerMessages.ZERO) || userInput.equals(PlayerMessages.MINUS_ONE))) {
                        break;
                    }
                }
            }
            playerEventQueue.getInstance().put(new PlayerEvent(gameEvent.getTo(), PlayerEventType.MOVE_IS_PLAYED, userInput, gameEvent.getPlayerType()));
        } catch (Exception e) {
            LOG.debug(ExceptionalMessages.INPUT_IS_SKIPPED);
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

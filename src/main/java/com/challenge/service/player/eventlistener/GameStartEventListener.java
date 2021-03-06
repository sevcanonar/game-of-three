package com.challenge.service.player.eventlistener;

import com.challenge.config.GameStartInformation;
import com.challenge.constants.PlayerEventType;
import com.challenge.constants.PlayerMessages;
import com.challenge.constants.PlayerType;
import com.challenge.event.GameEvent;
import com.challenge.event.PlayerEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.PrintWriter;
import java.net.Socket;
import java.util.Random;
import java.util.Scanner;

public class GameStartEventListener extends GameEventsListener implements GameListener {
    private static final Logger LOG = LoggerFactory.getLogger(GameStartEventListener.class);

    public GameStartEventListener(PrintWriter out, Scanner in, Socket clientSocket) {
        super(out, in, clientSocket);
    }

    @Override
    public PlayerEvent onGameEvent(GameEvent gameEvent) {
        out.println(gameEvent.getPlayerOutput());
        String userInput = null;
        if (gameEvent.getPlayerType().equals(PlayerType.AUTO)) {
            out.println(PlayerMessages.PLAYING_AUTOMATICALLY);
            userInput = String.valueOf(new Random().nextInt(Integer.MAX_VALUE) + 2);
            out.println(userInput);
        } else {
            while (!GameStartInformation.getInstance()) {
                out.println(PlayerMessages.PLEASE_ENTER_A_NUMBER_2);
                userInput = in.nextLine();
                if (userInput != null && isValidInteger(userInput)) {
                    break;
                }
            }
        }
        return new PlayerEvent(gameEvent.getTo(), PlayerEventType.MOVE_IS_PLAYED, userInput);

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

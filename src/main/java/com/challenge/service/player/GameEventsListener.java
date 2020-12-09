package com.challenge.service.player;

import com.challenge.config.PlayerEventQueue;
import com.challenge.constants.PlayerEventType;
import com.challenge.constants.PlayerType;
import com.challenge.event.GameEvent;
import com.challenge.event.GameStartEvent;
import com.challenge.event.GameYourTurnEvent;
import com.challenge.event.PlayerEvent;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Random;

public class GameEventsListener implements GameListener {
    String userName;
    PlayerType playerType;
    PrintWriter out;
    BufferedReader in;
    Socket clientSocket;
    PlayerEventQueue playerEventQueue;

    public GameEventsListener(String userName, PrintWriter out, BufferedReader in, Socket clientSocket, PlayerEventQueue playerEventQueue) {
        this.userName = userName;
        this.out = out;
        this.in = in;
        this.clientSocket = clientSocket;
        this.playerEventQueue = playerEventQueue;
    }

    @Override
    public void onGameYourTurnEvent(GameYourTurnEvent gameEvent) {
        try {
            if (gameEvent.getTo().equals(userName)) {
                out.println(gameEvent.getPlayerOutput());
                String userInput;
                if (gameEvent.getPlayerType().equals(PlayerType.AUTO)) {
                    out.println("Playing automatically.");
                    userInput = playRightMove(gameEvent.getPlayerInput());
                    out.println(userInput);
                } else {
                while (true) {
                    out.println("Please enter one of {1,0,-1}");
                    userInput = in.readLine();
                    if (userInput != null && (userInput.equals("1") || userInput.equals("0") || userInput.equals("-1"))) {
                        break;
                    }
                }
                }
                playerEventQueue.getInstance().put(new PlayerEvent(userName, PlayerEventType.MOVE_IS_PLAYED, userInput, playerType));
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onGameStartEvent(GameStartEvent gameEvent) {
        try {
            if (gameEvent.getTo().equals(userName)) {
                out.println(gameEvent.getPlayerOutput());
                String userInput;
                if (gameEvent.getPlayerType().equals(PlayerType.AUTO)) {
                    out.println("Playing automatically.");
                    userInput = String.valueOf(new Random().nextInt());
                    out.println(userInput);
                } else {
                    while (true) {
                        out.println("Please enter a number");
                        userInput = in.readLine();
                        if (userInput != null && isInteger(userInput)) {
                            break;
                        }
                    }
                }

                playerEventQueue.getInstance().put(new PlayerEvent(userName, PlayerEventType.MOVE_IS_PLAYED, userInput, playerType));
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onGameAutoManualInformationEvent(GameEvent gameEvent) {
        try {
            if (gameEvent.getTo().equals(userName)) {
                out.println(gameEvent.getPlayerOutput());
                String userInput;
                while (true) {
                    out.println("Please enter one of {A, M}");
                    userInput = in.readLine();
                    if (userInput != null && userInput.equals("A") || userInput.equals("M")) {
                        break;
                    }
                }
                playerType = userInput.equals("A") ? PlayerType.AUTO : PlayerType.MANUAL;
                playerEventQueue.getInstance().put(new PlayerEvent(userName, PlayerEventType.AUTO_MANUAL_SELECTION, userInput, playerType));
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void onGameInformationEvent(GameEvent gameEvent) {
        if (gameEvent.getTo().equals(userName))
            out.println(gameEvent.getPlayerOutput());
    }

    @Override
    public void onGameOverEvent(GameEvent gameEvent) {
        if (gameEvent.getTo().equals(userName)) {
            out.println(gameEvent.getPlayerOutput());
            try {
                out.close();
                clientSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private boolean isInteger(Object object) {
        if (object instanceof Integer) {
            return true;
        } else {
            String string = object.toString();

            try {
                Integer.parseInt(string);
            } catch (Exception e) {
                return false;
            }
        }

        return true;
    }


    private String playRightMove(Integer playerInput) {
        int i=-1;
        while(i<=1) {
            if((playerInput+i)%3==0)
                break;
            i++;
        }
        return String.valueOf(i);
    }
}

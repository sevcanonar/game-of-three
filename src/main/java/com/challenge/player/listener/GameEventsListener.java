package com.challenge.player.listener;

import com.challenge.player.config.PlayerEventQueue;
import com.challenge.player.constants.PlayerEventType;
import com.challenge.player.constants.PlayerType;
import com.challenge.player.event.GameEvent;
import com.challenge.player.event.PlayerEvent;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.concurrent.BlockingQueue;

public class GameEventsListener implements GameListener {
    String userName;
    PlayerType playerType;
    PrintWriter out;
    BufferedReader in;
    Socket clientSocket;
    BlockingQueue<PlayerEvent> playerEvents = PlayerEventQueue.getInstance();

    public GameEventsListener(String userName, PrintWriter out, BufferedReader in, Socket clientSocket) {
        this.userName = userName;
        this.out = out;
        this.in = in;
        this.clientSocket = clientSocket;
    }

    @Override
    public void onGameYourTurnEvent(GameEvent gameEvent) {
        out.println("current thread number is " + Thread.currentThread().getId());
        try {
            if (gameEvent.getTo().equals(userName)) {
                out.println(gameEvent.getPlayerOutput());
                if (gameEvent.isFirstMove() && in != null)
                    in.reset();
                String userInput;
                while (true) {
                    out.println("Please enter one of {1,0,-1}");
                    userInput = in.readLine();
                    if (userInput!=null&&(userInput.equals("1") || userInput.equals("0") || userInput.equals("-1"))) {
                        break;
                    }
                }
                playerEvents.put(new PlayerEvent(userName, PlayerEventType.MOVE_IS_PLAYED, userInput, playerType));
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onGameStartEvent(GameEvent gameEvent) {
        out.println("current thread number is " + Thread.currentThread().getId());
        try {
            if (gameEvent.getTo().equals(userName)) {
                out.println(gameEvent.getPlayerOutput());
                if (gameEvent.isFirstMove() && in != null)
                    in.reset();
                String userInput;
                while (true) {
                    out.println("Please enter a number");
                    userInput = in.readLine();
                    if (userInput != null && isInteger(userInput)) {
                        break;
                    }
                }
                playerEvents.put(new PlayerEvent(userName, PlayerEventType.MOVE_IS_PLAYED, userInput, playerType));
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onGameAutoManualInformationEvent(GameEvent gameEvent) {
        try {
            out.println("current thread number is " + Thread.currentThread().getId());
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
                playerType = userInput.equals("A")?PlayerType.AUTO:PlayerType.MANUAL;
                playerEvents.put(new PlayerEvent(userName, PlayerEventType.AUTO_MANUAL_SELECTION, userInput, playerType));
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onGameStartInformationEvent(GameEvent gameEvent) {
        try {
            out.println("current thread number is " + Thread.currentThread().getId());
            if (gameEvent.getTo().equals(userName)) {
                out.println(gameEvent.getPlayerOutput());
                String userInput;
                while (true) {
                    out.println("Please enter one of {Y,N}");
                    userInput = in.readLine();
                    if(userInput.equals("Y")||userInput.equals("N")){
                        break;
                    }
                }
                playerEvents.put(new PlayerEvent(userName, PlayerEventType.START_SELECTION, userInput, PlayerType.NONE));
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onGameInformationEvent(GameEvent gameEvent) {
        out.println("current thread number is " + Thread.currentThread().getId());
        if (gameEvent.getTo().equals(userName))
            out.println(gameEvent.getPlayerOutput());
    }

    @Override
    public void onGameOverEvent(GameEvent gameEvent) {
        out.println("current thread number is " + Thread.currentThread().getId());
        if (gameEvent.getTo().equals(userName)) {
            out.println(gameEvent.getPlayerOutput());
            try {
                in.close();
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
}

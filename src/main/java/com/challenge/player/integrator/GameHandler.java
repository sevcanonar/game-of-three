package com.challenge.player.integrator;


import com.challenge.player.config.PlayerEventQueue;
import com.challenge.player.constants.PlayerEventType;
import com.challenge.player.event.*;
import com.challenge.player.listener.GameEventRegistererCaller;
import com.challenge.player.model.PlayerMoveInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.BlockingQueue;

@Service
@Scope("prototype")
public class GameHandler extends Thread {
    private static Map<String, PlayerMoveInfo> playerInformation = new HashMap<>(2);

    @Autowired
    GameEventRegistererCaller gameEventRegistererCaller;

    @Override
    public void run() {
        BlockingQueue<PlayerEvent> playerEvents = PlayerEventQueue.getInstance();
        while (true) {
            try {
                PlayerEvent playerEvent = playerEvents.take();
                String opponentName = getOpponent(playerEvent.getUserName());
                String currentUserName = getCurrentUserName();
                PlayerMoveInfo startedGameInfo = startedGameInformation();
                switch (playerEvent.getEventType()) {
                    case USER_LOGIN:
                        if(currentUserName!=null&&currentUserName.equals(playerEvent.getUserName())) {
                            gameEventRegistererCaller.eventHappens(new GameOverEvent(playerEvent.getUserName(), "User name already exists. Please connect again."));
                        }
                        else {
                            System.out.println("User with user name:" + playerEvent.getPlayerInput() + " logged in");
                            PlayerMoveInfo playerMoveInfo = new PlayerMoveInfo(false);
                            playerInformation.put(playerEvent.getUserName(), playerMoveInfo);
                            gameEventRegistererCaller.eventHappens(new GameInformationEvent(playerEvent.getUserName(), "You logged in."));
                            if (startedGameInfo.isStarted()) {
                                gameEventRegistererCaller.eventHappens(new GameInformationEvent(playerEvent.getUserName(), "There is a game already started."));
                                PlayerMoveInfo loggedInUserInfo = playerInformation.get(playerEvent.getUserName());
                                loggedInUserInfo.setMoveInput(startedGameInfo.getMoveValue());
                                playerInformation.put(playerEvent.getUserName(), loggedInUserInfo);
                                gameEventRegistererCaller.eventHappens(new GameAutoManualInformationEvent(playerEvent.getUserName(), "Select your play type"));
                                gameEventRegistererCaller.eventHappens(new GameYourTurnEvent(playerEvent.getUserName(), PlayerEventType.YOUR_TURN, "Opponent start value is " + startedGameInfo.getMoveValue(), false));
                            } else
                             gameEventRegistererCaller.eventHappens(new GameStartInformationEvent(playerEvent.getUserName(), "Do you want to start a new game?"));
                        }
                        break;

                    case START_SELECTION:
                        if (startedGameInfo.isStarted()) {
                            gameEventRegistererCaller.eventHappens(new GameInformationEvent(playerEvent.getUserName(), "There is a game already started."));
                            PlayerMoveInfo userInfo = playerInformation.get(playerEvent.getUserName());
                            userInfo.setMoveInput(startedGameInfo.getMoveValue());
                            playerInformation.put(playerEvent.getUserName(), userInfo);
                            gameEventRegistererCaller.eventHappens(new GameAutoManualInformationEvent(playerEvent.getUserName(), "Select your play type"));
                            gameEventRegistererCaller.eventHappens(new GameYourTurnEvent(playerEvent.getUserName(), PlayerEventType.YOUR_TURN, "Opponent start value is " + startedGameInfo.getMoveValue(), false));
                        }
                        else {
                            gameEventRegistererCaller.eventHappens(new GameAutoManualInformationEvent(playerEvent.getUserName(), "Select your play type"));
                        }
                        break;
                    case AUTO_MANUAL_SELECTION:
                        if (startedGameInfo.isStarted()) {
                            gameEventRegistererCaller.eventHappens(new GameInformationEvent(playerEvent.getUserName(), "There is a game already started."));
                            PlayerMoveInfo userInfo = playerInformation.get(playerEvent.getUserName());
                            userInfo.setMoveInput(startedGameInfo.getMoveValue());
                            playerInformation.put(playerEvent.getUserName(), userInfo);
                            gameEventRegistererCaller.eventHappens(new GameYourTurnEvent(playerEvent.getUserName(), PlayerEventType.YOUR_TURN, "Opponent start value is " + startedGameInfo.getMoveValue(), false));
                        }
                        else {
                            gameEventRegistererCaller.eventHappens(new GameStartEvent(playerEvent.getUserName(), "Enter a value to start the game."));
                        }
                        break;
                    case MOVE_IS_PLAYED:
                        System.out.println("User with user name:" + playerEvent.getUserName() + " played " + playerEvent.getPlayerInput());

                        //update for current move
                        PlayerMoveInfo playedPlayerMoveInfo = playerInformation.get(playerEvent.getUserName());
                        playedPlayerMoveInfo.setMoveValue(Integer.valueOf(playerEvent.getPlayerInput()));
                        if(playedPlayerMoveInfo.getMoveInput()==null)
                            playedPlayerMoveInfo.setStarted(true);
                        else
                            playedPlayerMoveInfo.setStarted(false);
                        playerInformation.put(playerEvent.getUserName(), playedPlayerMoveInfo);
                        //calculate
                        Integer moveOutput;
                        if(playedPlayerMoveInfo.getMoveInput()!=null){
                            Integer moveSum = (playedPlayerMoveInfo.getMoveInput()+playedPlayerMoveInfo.getMoveValue());
                            if(Math.floorMod(moveSum,3)!=0){
                                gameEventRegistererCaller.eventHappens(new GameYourTurnEvent(playerEvent.getUserName(), PlayerEventType.YOUR_TURN, "Your input does not result in a number divisable with 3, please enter again.", false));
                                break;
                            }
                            else
                                moveOutput=moveSum/3;
                        }
                        else
                            moveOutput = Integer.valueOf(playerEvent.getPlayerInput());
                        //inform player
                        gameEventRegistererCaller.eventHappens(new GameInformationEvent(playerEvent.getUserName(), "You played with :" + playedPlayerMoveInfo.getMoveValue() + ". And output is " + moveOutput));
                        //update for opponent
                        if(moveOutput.equals(1)) {
                            gameEventRegistererCaller.eventHappens(new GameOverEvent(playerEvent.getUserName(), "You Won!"));
                            gameEventRegistererCaller.eventHappens(new GameOverEvent(opponentName, "Your opponent succeeded to calculate 1. You Lost!"));
                            playerInformation.clear();
                        }
                        else {
                            if (opponentName != null) {
                                PlayerMoveInfo opponentPlayerMoveInfo = playerInformation.get(opponentName);
                                opponentPlayerMoveInfo.setMoveInput(moveOutput);
                                gameEventRegistererCaller.eventHappens(new GameYourTurnEvent(opponentName, PlayerEventType.YOUR_TURN, "Your opponent played. Your input is :" + moveOutput, playerInformation.get(playerEvent.getUserName()).isStarted()));
                            }
                        }
                        break;
                }
            } catch (InterruptedException interruptedException) {
                interruptedException.printStackTrace();
            }
        }
    }

    private String getCurrentUserName() {
        for (Map.Entry<String, PlayerMoveInfo> entry : playerInformation.entrySet()) {
            return entry.getKey();
        }
        return null;
    }

    private String getOpponent(String userName) {
        for (Map.Entry<String, PlayerMoveInfo> entry : playerInformation.entrySet()) {
            String playerName = entry.getKey();
            if (!playerName.equals(userName)) {
                return playerName;
            }
        }
        return null;
    }

    private PlayerMoveInfo startedGameInformation() {
        for (Map.Entry<String, PlayerMoveInfo> entry : playerInformation.entrySet()) {
            if (entry.getValue().isStarted()) {
                return entry.getValue();
            }
        }
        return new PlayerMoveInfo(false);
    }


}
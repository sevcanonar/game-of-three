package com.challenge.service.game;

import com.challenge.constants.PlayerType;
import com.challenge.model.PlayerMoveInfo;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class GameHandlingServiceHelper {

    public String getCurrentUserName(Map<String, PlayerMoveInfo> playerInformation) {
        for (Map.Entry<String, PlayerMoveInfo> entry : playerInformation.entrySet()) {
            return entry.getKey();
        }
        return null;
    }
    public PlayerMoveInfo startedGameInformation(Map<String, PlayerMoveInfo> playerInformation) {
        for (Map.Entry<String, PlayerMoveInfo> entry : playerInformation.entrySet()) {
            if (entry.getValue().isStarted()) {
                return entry.getValue();
            }
        }
        return new PlayerMoveInfo(false);
    }
    public PlayerType getPlayerType(String userName, Map<String, PlayerMoveInfo> playerInformation) {
        return playerInformation.get(userName).getPlayerType();
    }
    public String getOpponent(String userName, Map<String, PlayerMoveInfo> playerInformation) {
        for (Map.Entry<String, PlayerMoveInfo> entry : playerInformation.entrySet()) {
            String playerName = entry.getKey();
            if (!playerName.equals(userName)) {
                return playerName;
            }
        }
        return null;
    }
}

package com.challenge.service.game.helper;

import com.challenge.model.PlayerMoveInfo;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class PlayerInformationProvider {

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

    public String getOpponentName(String userName, Map<String, PlayerMoveInfo> playerInformation) {
        for (Map.Entry<String, PlayerMoveInfo> entry : playerInformation.entrySet()) {
            String playerName = entry.getKey();
            if (!playerName.equals(userName)) {
                return playerName;
            }
        }
        return null;
    }

    public PlayerMoveInfo getOpponentMoveInfo(String userName, Map<String, PlayerMoveInfo> playerInformation) {
        return playerInformation.get(userName);
    }
}

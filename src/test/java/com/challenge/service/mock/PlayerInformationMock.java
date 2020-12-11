package com.challenge.service.mock;

import com.challenge.model.PlayerMoveInfo;

import java.util.HashMap;
import java.util.Map;

public class PlayerInformationMock {
    public Map<String, PlayerMoveInfo> getStartedPlayerInformation() {
        Map<String, PlayerMoveInfo> playerInformation = new HashMap<>();
        playerInformation.put("a", new StartedGameInfoMock());
        return playerInformation;
    }

    public Map<String, PlayerMoveInfo> getStartedTwoPlayerInformation() {
        Map<String, PlayerMoveInfo> playerInformation = new HashMap<>();
        playerInformation.put("a", new StartedGameInfoMock());
        playerInformation.put("auto", new NotStartedGameInfoMock());
        return playerInformation;
    }

    public Map<String, PlayerMoveInfo> getNotStartedPlayerInformation() {
        Map<String, PlayerMoveInfo> playerInformation = new HashMap<>();
        playerInformation.put("b", new NotStartedGameInfoMock());
        return playerInformation;
    }

    public Map<String, PlayerMoveInfo> getNotStartedTwoPlayerInformation() {
        Map<String, PlayerMoveInfo> playerInformation = new HashMap<>();
        playerInformation.put("a", new NotStartedGameInfoMock());
        playerInformation.put("auto", new NotStartedGameInfoMock());
        return playerInformation;
    }

    public Map<String, PlayerMoveInfo> getEmptyPlayerInformation() {
        Map<String, PlayerMoveInfo> playerInformation = new HashMap<>();
        return playerInformation;
    }

    public Map<String, PlayerMoveInfo> getTwoPlayersNotStartedPlayerInformation() {
        Map<String, PlayerMoveInfo> playerInformation = new HashMap<>();
        playerInformation.put("b", new NotStartedGameInfoMock());
        playerInformation.put("a", new NotStartedGameInfoMock());
        return playerInformation;
    }

    public Map<String, PlayerMoveInfo> getTwoPlayersBeforeWinningInformation() {
        Map<String, PlayerMoveInfo> playerInformation = new HashMap<>();
        playerInformation.put("b", new StartedGameInfoMock());
        playerInformation.put("a", new WinningGameInfoMock());
        return playerInformation;
    }

    public Map<String, PlayerMoveInfo> getTwoPlayersPlayingInformation() {
        Map<String, PlayerMoveInfo> playerInformation = new HashMap<>();
        playerInformation.put("b", new BeforeMiddleGameInfoMock());
        playerInformation.put("a", new MiddleGameInfoMock());
        return playerInformation;
    }
}

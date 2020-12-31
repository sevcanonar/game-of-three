package com.challenge.service.game;

import com.challenge.model.PlayerMoveInfo;
import com.challenge.service.game.helper.PlayerInformationProvider;
import com.challenge.service.mock.PlayerInformationMock;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Map;

@SpringBootTest
public class PlayerInformationProviderTests {

    @InjectMocks
    public PlayerInformationProvider playerInformationProvider;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testGetCurrentUserName() {
        Map<String, PlayerMoveInfo> oneUserPlayerInfo = new PlayerInformationMock().getStartedPlayerInformation();
        String currentUser = playerInformationProvider.getCurrentUserName(oneUserPlayerInfo);
        Assert.assertEquals("a", currentUser);
    }

    @Test
    public void testStartedGameInformation() {
        Map<String, PlayerMoveInfo> oneUserPlayerInfo = new PlayerInformationMock().getStartedPlayerInformation();
        PlayerMoveInfo startedGameInformation = playerInformationProvider.startedGameInformation(oneUserPlayerInfo);
        Assert.assertEquals(true, startedGameInformation.isStarted());
    }

    @Test
    public void testGetOpponent() {
        Map<String, PlayerMoveInfo> twoPlayerInformation = new PlayerInformationMock().getStartedTwoPlayerInformation();
        String opponent = playerInformationProvider.getOpponentName("a", twoPlayerInformation);
        Assert.assertEquals("auto", opponent);
    }
}


package com.challenge.service.game;

import com.challenge.model.PlayerMoveInfo;
import com.challenge.service.mock.PlayerInformationMock;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Map;

@SpringBootTest
public class GameHandlingServiceHelperTests {

    @InjectMocks
    public GameHandlingServiceHelper gameHandlingServiceHelper;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testGetCurrentUserName() {
        Map<String, PlayerMoveInfo> oneUserPlayerInfo = new PlayerInformationMock().getStartedPlayerInformation();
        String currentUser = gameHandlingServiceHelper.getCurrentUserName(oneUserPlayerInfo);
        Assert.assertEquals("a", currentUser);
    }

    @Test
    public void testStartedGameInformation() {
        Map<String, PlayerMoveInfo> oneUserPlayerInfo = new PlayerInformationMock().getStartedPlayerInformation();
        PlayerMoveInfo startedGameInformation = gameHandlingServiceHelper.startedGameInformation(oneUserPlayerInfo);
        Assert.assertEquals(true, startedGameInformation.isStarted());
    }

    @Test
    public void testGetOpponent() {
        Map<String, PlayerMoveInfo> twoPlayerInformation = new PlayerInformationMock().getStartedTwoPlayerInformation();
        String opponent = gameHandlingServiceHelper.getOpponent("a", twoPlayerInformation);
        Assert.assertEquals("auto", opponent);
    }
}


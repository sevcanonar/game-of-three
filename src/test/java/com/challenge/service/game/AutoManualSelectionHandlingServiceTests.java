package com.challenge.service.game;

import com.challenge.config.GameStartInformation;
import com.challenge.model.PlayerMoveInfo;
import com.challenge.service.mock.AutoManualSelectionPlayerEventMock;
import com.challenge.service.mock.NotStartedGameInfoMock;
import com.challenge.service.mock.PlayerInformationMock;
import com.challenge.service.mock.StartedGameInfoMock;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Map;

@SpringBootTest
public class AutoManualSelectionHandlingServiceTests {
    @InjectMocks
    AutoManualSelectionHandlingService autoManualSelectionHandlingService;

    @InjectMocks
    GameStartInformation gameStartInformation;

    @Mock
    GameHandlingServiceHelper gameHandlingServiceHelper;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void doHandleWhenStarted() {
        PlayerMoveInfo startedGameInfo = new StartedGameInfoMock();
        Mockito.doReturn(startedGameInfo).when(gameHandlingServiceHelper).startedGameInformation(Mockito.any());
        Map<String, PlayerMoveInfo> playerInformation = new PlayerInformationMock().getStartedTwoPlayerInformation();
        gameStartInformation.setInstance(true);
        autoManualSelectionHandlingService.handle(new AutoManualSelectionPlayerEventMock(), playerInformation);
        Assert.assertEquals(2, playerInformation.size());
        Assert.assertEquals(java.util.Optional.of(5), java.util.Optional.of(playerInformation.get("a").getMoveInput()));
    }

    @Test
    public void doHandleWhenNotStarted() {
        PlayerMoveInfo notStartedGameInfo = new NotStartedGameInfoMock();
        Mockito.doReturn(notStartedGameInfo).when(gameHandlingServiceHelper).startedGameInformation(Mockito.any());
        Map<String, PlayerMoveInfo> playerInformation = new PlayerInformationMock().getNotStartedTwoPlayerInformation();
        gameStartInformation.setInstance(false);
        autoManualSelectionHandlingService.handle(new AutoManualSelectionPlayerEventMock(), playerInformation);
        Assert.assertEquals(2, playerInformation.size());
        Assert.assertNull(playerInformation.get("a").getMoveInput());
        Assert.assertNull(playerInformation.get("auto").getMoveInput());
    }
}

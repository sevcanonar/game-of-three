package com.challenge.service.game;

import com.challenge.model.PlayerMoveInfo;
import com.challenge.service.mock.*;
import com.challenge.service.player.GameEventsConsumer;
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

    @Mock
    GameHandlingServiceHelper gameHandlingServiceHelper;

    @Mock
    GameEventsConsumer gameEventsConsumer;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void doHandleWhenStarted() {
        PlayerMoveInfo startedGameInfo = new StartedGameInfoMock();
        Mockito.doReturn(startedGameInfo).when(gameHandlingServiceHelper).startedGameInformation(Mockito.any());
        Map<String, PlayerMoveInfo> playerInformation = new PlayerInformationMock().getStartedTwoPlayerInformation();
        autoManualSelectionHandlingService.handle(new AutoManualSelectionPlayerEventMock(), playerInformation);
        Assert.assertEquals(2, playerInformation.size());
        Assert.assertEquals(java.util.Optional.of(5), java.util.Optional.of(playerInformation.get("a").getMoveInput()));
        Mockito.verify(gameEventsConsumer, Mockito.times(2)).createEvent(Mockito.any());
    }
    @Test
    public void doHandleWhenNotStarted() {
        PlayerMoveInfo notStartedGameInfo = new NotStartedGameInfoMock();
        Mockito.doReturn(notStartedGameInfo).when(gameHandlingServiceHelper).startedGameInformation(Mockito.any());
        Map<String, PlayerMoveInfo> playerInformation = new PlayerInformationMock().getNotStartedTwoPlayerInformation();
        autoManualSelectionHandlingService.handle(new StartSelectionPlayerEventMock(), playerInformation);
        Assert.assertEquals(2, playerInformation.size());
        Assert.assertNull(playerInformation.get("a").getMoveInput());
        Assert.assertNull(playerInformation.get("auto").getMoveInput());
        Mockito.verify(gameEventsConsumer, Mockito.times(1)).createEvent(Mockito.any());
    }
}

package com.challenge.service.game;

import com.challenge.event.PlayerEvent;
import com.challenge.model.PlayerMoveInfo;
import com.challenge.service.mock.*;
import com.challenge.service.player.GameEventConsumer;
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
public class UserLoginHandlingServiceTest {
    @InjectMocks
    UserLoginHandlingService userLoginHandlingService;

    @Mock
    GameHandlingServiceHelper gameHandlingServiceHelper;

    @Mock
    GameEventConsumer gameEventConsumer;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void doHandleWhenOpponentWithOtherNameExistsAndStarted() {
        Map<String, PlayerMoveInfo> playerInformation = new PlayerInformationMock().getStartedPlayerInformation();
        Mockito.doReturn("sevcan").when(gameHandlingServiceHelper).getCurrentUserName(Mockito.any());
        Mockito.doReturn(new StartedGameInfoMock()).when(gameHandlingServiceHelper).startedGameInformation(Mockito.any());
        userLoginHandlingService.handle(new UserLoginPlayerEventMock(), playerInformation);
        Mockito.verify(gameEventConsumer, Mockito.times(3)).createEvent(Mockito.any());
        Assert.assertEquals(2, playerInformation.size());
        Assert.assertEquals(playerInformation.get("log").getMoveInput(), playerInformation.get("a").getMoveValue());
    }
    @Test
    public void doHandleWhenOpponentWithOtherNameExistsAndNotStarted() {
        Map<String, PlayerMoveInfo> playerInformation = new PlayerInformationMock().getNotStartedPlayerInformation();
        Mockito.doReturn("sevcan").when(gameHandlingServiceHelper).getCurrentUserName(Mockito.any());
        Mockito.doReturn(new NotStartedGameInfoMock()).when(gameHandlingServiceHelper).startedGameInformation(Mockito.any());
        userLoginHandlingService.handle(new UserLoginPlayerEventMock(), playerInformation);
        Mockito.verify(gameEventConsumer, Mockito.times(2)).createEvent(Mockito.any());
        Assert.assertEquals(2, playerInformation.size());
        Assert.assertNull(playerInformation.get("log").getMoveInput());
    }

    @Test
    public void doHandleWhenOpponentDoesntExist() {
        Map<String, PlayerMoveInfo> playerInformation = new PlayerInformationMock().getEmptyPlayerInformation();
        Mockito.doReturn(null).when(gameHandlingServiceHelper).getCurrentUserName(Mockito.any());
        Mockito.doReturn(new NotStartedGameInfoMock()).when(gameHandlingServiceHelper).startedGameInformation(Mockito.any());
        userLoginHandlingService.handle(new UserLoginPlayerEventMock(), playerInformation);
        Mockito.verify(gameEventConsumer, Mockito.times(2)).createEvent(Mockito.any());
        Assert.assertEquals(1, playerInformation.size());
        Assert.assertNull(playerInformation.get("log").getMoveInput());
    }
    @Test
    public void doHandleWhenOpponentExistWithSameName() {
        Map<String, PlayerMoveInfo> playerInformation = new PlayerInformationMock().getNotStartedPlayerInformation();
        Mockito.doReturn("log").when(gameHandlingServiceHelper).getCurrentUserName(Mockito.any());
        userLoginHandlingService.handle(new UserLoginPlayerEventMock(), playerInformation);
        Mockito.verify(gameEventConsumer, Mockito.times(1)).createEvent(Mockito.any());
        Assert.assertEquals(1, playerInformation.size());
    }
}

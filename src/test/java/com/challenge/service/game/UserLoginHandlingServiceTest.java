package com.challenge.service.game;

import com.challenge.model.PlayerMoveInfo;
import com.challenge.service.game.eventbuilder.GameEventsBuilderMediator;
import com.challenge.service.game.helper.PlayerInformationProvider;
import com.challenge.service.game.eventhandler.UserLoginHandlingService;
import com.challenge.service.mock.NotStartedGameInfoMock;
import com.challenge.service.mock.PlayerInformationMock;
import com.challenge.service.mock.StartedGameInfoMock;
import com.challenge.service.mock.UserLoginPlayerEventMock;
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
    PlayerInformationProvider playerInformationProvider;

    @Mock
    GameEventsBuilderMediator gameEventsBuilderMediator;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void doHandleWhenOpponentWithOtherNameExistsAndStarted() {
        Map<String, PlayerMoveInfo> playerInformation = new PlayerInformationMock().getStartedPlayerInformation();
        Mockito.doReturn("sevcan").when(playerInformationProvider).getCurrentUserName(Mockito.any());
        Mockito.doReturn(new StartedGameInfoMock()).when(playerInformationProvider).startedGameInformation(Mockito.any());
        userLoginHandlingService.handle(new UserLoginPlayerEventMock(), playerInformation);
        Assert.assertEquals(2, playerInformation.size());
        Assert.assertEquals(playerInformation.get("log").getMoveInput(), playerInformation.get("a").getMoveValue());
    }

    @Test
    public void doHandleWhenOpponentWithOtherNameExistsAndNotStarted() {
        Map<String, PlayerMoveInfo> playerInformation = new PlayerInformationMock().getNotStartedPlayerInformation();
        Mockito.doReturn("sevcan").when(playerInformationProvider).getCurrentUserName(Mockito.any());
        Mockito.doReturn(new NotStartedGameInfoMock()).when(playerInformationProvider).startedGameInformation(Mockito.any());
        userLoginHandlingService.handle(new UserLoginPlayerEventMock(), playerInformation);
        Assert.assertEquals(2, playerInformation.size());
        Assert.assertNull(playerInformation.get("log").getMoveInput());
    }

    @Test
    public void doHandleWhenOpponentDoesntExist() {
        Map<String, PlayerMoveInfo> playerInformation = new PlayerInformationMock().getEmptyPlayerInformation();
        Mockito.doReturn(null).when(playerInformationProvider).getCurrentUserName(Mockito.any());
        Mockito.doReturn(new NotStartedGameInfoMock()).when(playerInformationProvider).startedGameInformation(Mockito.any());
        userLoginHandlingService.handle(new UserLoginPlayerEventMock(), playerInformation);
        Assert.assertEquals(1, playerInformation.size());
        Assert.assertNull(playerInformation.get("log").getMoveInput());
    }

    @Test
    public void doHandleWhenOpponentExistWithSameName() {
        Map<String, PlayerMoveInfo> playerInformation = new PlayerInformationMock().getNotStartedPlayerInformation();
        Mockito.doReturn(new NotStartedGameInfoMock()).when(playerInformationProvider).startedGameInformation(Mockito.any());
        userLoginHandlingService.handle(new UserLoginPlayerEventMock().UserLoginPlayerEventSameName(), playerInformation);
        Assert.assertEquals(1, playerInformation.size());
    }
}

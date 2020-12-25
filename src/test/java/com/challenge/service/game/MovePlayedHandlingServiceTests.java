package com.challenge.service.game;

import com.challenge.model.PlayerMoveInfo;
import com.challenge.service.mock.FirstMovePlayedPlayerEventMock;
import com.challenge.service.mock.MiddleMovePlayedPlayerEventMock;
import com.challenge.service.mock.MiddleMovePlayedWrongNumberPlayerEventMock;
import com.challenge.service.mock.PlayerInformationMock;
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
public class MovePlayedHandlingServiceTests {
    @InjectMocks
    MovePlayedHandlingService movePlayedHandlingService;

    @Mock
    GameHandlingServiceHelper gameHandlingServiceHelper;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void doHandleWhenItIsFirstMove() {
        Map<String, PlayerMoveInfo> playerInformation = new PlayerInformationMock().getTwoPlayersNotStartedPlayerInformation();
        Mockito.doCallRealMethod().when(gameHandlingServiceHelper).getOpponent(Mockito.anyString(), Mockito.any());
        movePlayedHandlingService.handle(new FirstMovePlayedPlayerEventMock(), playerInformation);
        Assert.assertEquals(java.util.Optional.of(5), java.util.Optional.of(playerInformation.get("b").getMoveInput()));
        Assert.assertEquals(java.util.Optional.of(5), java.util.Optional.of(playerInformation.get("a").getMoveValue()));
    }

    @Test
    public void doHandleWhenItIsWinningMove() {
        Map<String, PlayerMoveInfo> playerInformation = new PlayerInformationMock().getTwoPlayersBeforeWinningInformation();
        Mockito.doCallRealMethod().when(gameHandlingServiceHelper).getOpponent(Mockito.anyString(), Mockito.any());
        movePlayedHandlingService.handle(new MiddleMovePlayedPlayerEventMock(), playerInformation);
        Assert.assertEquals(0, playerInformation.size());
    }

    @Test
    public void doHandleWhenMoveResultIsNotDivisableBy3() {
        Map<String, PlayerMoveInfo> playerInformation = new PlayerInformationMock().getTwoPlayersBeforeWinningInformation();
        Mockito.doCallRealMethod().when(gameHandlingServiceHelper).getOpponent(Mockito.anyString(), Mockito.any());
        movePlayedHandlingService.handle(new MiddleMovePlayedWrongNumberPlayerEventMock(), playerInformation);
        Assert.assertEquals(2, playerInformation.size());
        Assert.assertEquals(java.util.Optional.of(0), java.util.Optional.of(playerInformation.get("a").getMoveValue()));
        Assert.assertEquals(java.util.Optional.of(5), java.util.Optional.of(playerInformation.get("b").getMoveInput()));
    }

    @Test
    public void doHandleWhenGameContinues() {
        Map<String, PlayerMoveInfo> playerInformation = new PlayerInformationMock().getTwoPlayersPlayingInformation();
        Mockito.doCallRealMethod().when(gameHandlingServiceHelper).getOpponent(Mockito.anyString(), Mockito.any());
        movePlayedHandlingService.handle(new MiddleMovePlayedPlayerEventMock(), playerInformation);
        Assert.assertEquals(2, playerInformation.size());
        Assert.assertEquals(java.util.Optional.of(1), java.util.Optional.of(playerInformation.get("a").getMoveValue()));
        Assert.assertEquals(java.util.Optional.of(5), java.util.Optional.of(playerInformation.get("b").getMoveInput()));
    }

}

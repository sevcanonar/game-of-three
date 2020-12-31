package com.challenge.service.player;

import com.challenge.config.GameListenersPerPlayer;
import com.challenge.config.PlayerEventQueue;
import com.challenge.service.GameEventsRegisterer;
import com.challenge.service.mock.GameListenersPerPlayerMock;
import com.challenge.service.player.eventlistener.GameListener;
import com.challenge.service.player.eventlistener.GameYourTurnEventListener;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

@SpringBootTest
public class GameEventsRegistererTests {
    @InjectMocks
    GameEventsRegisterer gameEventsRegisterer;

    @Mock
    GameListenersPerPlayer gameListenersPerPlayer;


    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void doRegisterWhenGameListenerMapEmpty() {
        Mockito.doReturn(new GameListenersPerPlayerMock().getGameListenersEmptyMock()).when(gameListenersPerPlayer).getInstance();
        gameEventsRegisterer.registerAllListeners("a", null, null, null);
        Assert.assertEquals(1, gameListenersPerPlayer.getInstance().size());
    }

    @Test
    public void doRegisterWhenGameListenerMapNotEmptyWithSameUser() {
        Mockito.doReturn(new GameListenersPerPlayerMock().getGameListenersOneMock()).when(gameListenersPerPlayer).getInstance();
        gameEventsRegisterer.registerAllListeners("a", null, null, null);
        Assert.assertEquals(1, gameListenersPerPlayer.getInstance().size());
        Assert.assertEquals(5, gameListenersPerPlayer.getInstance().get("a").size());
    }

    @Test
    public void doRegisterWhenGameListenerMapNotEmptyWithDifferentUser() {
        Mockito.doReturn(new GameListenersPerPlayerMock().getGameListenersOneMock()).when(gameListenersPerPlayer).getInstance();
        gameEventsRegisterer.registerAllListeners("b", null, null, null);
        Assert.assertEquals(2, gameListenersPerPlayer.getInstance().size());
        Assert.assertEquals(5, gameListenersPerPlayer.getInstance().get("b").size());
        Assert.assertEquals(1, gameListenersPerPlayer.getInstance().get("a").size());
    }

}

package com.challenge.service.player;

import com.challenge.config.GameListenersPerPlayer;
import com.challenge.config.GameStartInformation;
import com.challenge.config.PlayerEventQueue;
import com.challenge.constants.GameListenerType;
import com.challenge.constants.PlayerType;
import com.challenge.event.GameEvent;
import com.challenge.event.GameYourTurnEvent;
import com.challenge.service.mock.GameListenersPerPlayerMock;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.BufferedReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Map;

@SpringBootTest
public class GameEventsConsumerTests {
    @InjectMocks
    GameEventsConsumer gameEventsConsumer;

    @Mock
    GameYourTurnEventListener gameYourTurnEventListener;

    @Mock
    GameListenersPerPlayer gameListenersPerPlayer;

    @Mock
    GameListener gameListener;

    @Mock
    PrintWriter out;
    @Mock
    BufferedReader in;
    @Mock
    Socket clientSocket;
    @Mock
    PlayerEventQueue playerEventQueue;


    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void doRegisterWhenGameListenerMapEmpty(){
        Mockito.doReturn(new GameListenersPerPlayerMock().getGameListenersEmptyMock()).when(gameListenersPerPlayer).getInstance();
        gameEventsConsumer.register("a", GameListenerType.YOURTURN, new GameYourTurnEventListener(null, null, null, null));
        Assert.assertEquals(1, gameListenersPerPlayer.getInstance().size());
    }

    @Test
    public void doRegisterWhenGameListenerMapNotEmptyWithSameUser(){
        Mockito.doReturn(new GameListenersPerPlayerMock().getGameListenersOneMock()).when(gameListenersPerPlayer).getInstance();
        gameEventsConsumer.register("a", GameListenerType.START, new GameStartEventListener(null, null, null, null));
        Assert.assertEquals(1, gameListenersPerPlayer.getInstance().size());
        Assert.assertEquals(2, gameListenersPerPlayer.getInstance().get("a").size());
    }
    @Test
    public void doRegisterWhenGameListenerMapNotEmptyWithDifferentUser(){
        Mockito.doReturn(new GameListenersPerPlayerMock().getGameListenersOneMock()).when(gameListenersPerPlayer).getInstance();
        gameEventsConsumer.register("b", GameListenerType.YOURTURN, new GameYourTurnEventListener(null, null, null, null));
        Assert.assertEquals(2, gameListenersPerPlayer.getInstance().size());
        Assert.assertEquals(1, gameListenersPerPlayer.getInstance().get("b").size());
        Assert.assertEquals(1, gameListenersPerPlayer.getInstance().get("a").size());
    }

}

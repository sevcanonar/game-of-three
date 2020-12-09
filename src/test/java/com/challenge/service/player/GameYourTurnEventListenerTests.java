package com.challenge.service.player;

import com.challenge.config.PlayerEventQueue;
import com.challenge.constants.PlayerType;
import com.challenge.event.GameYourTurnEvent;
import com.challenge.service.mock.PlayerEventQueueMock;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;


@SpringBootTest
public class GameYourTurnEventListenerTests {
    @InjectMocks
    GameYourTurnEventListener gameYourTurnEventListener;

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
    public void doHandleOnGameEventWhenAuto() throws IOException {
        GameYourTurnEvent gameEvent = new GameYourTurnEvent("s", "Play your turn", PlayerType.AUTO, 5);
        Mockito.doReturn(new PlayerEventQueueMock().getPlayerEventQueueEmptyMock()).when(playerEventQueue).getInstance();
        gameYourTurnEventListener.onGameEvent(gameEvent);
        Mockito.verify(playerEventQueue, Mockito.times(1)).getInstance();
        Mockito.verify(out, Mockito.times(3)).println(Mockito.anyString());
        Mockito.verify(in, Mockito.times(0)).readLine();
        Assert.assertEquals(1, playerEventQueue.getInstance().size());
    }

    @Test
    public void doHandleOnGameEventWhenManualAndInputSuitable() throws IOException {
        GameYourTurnEvent gameEvent = new GameYourTurnEvent("s", "Play your turn", PlayerType.MANUAL, 5);
        Mockito.doReturn("1").when(in).readLine();
        Mockito.doReturn(new PlayerEventQueueMock().getPlayerEventQueueEmptyMock()).when(playerEventQueue).getInstance();
        gameYourTurnEventListener.onGameEvent(gameEvent);
        Mockito.verify(playerEventQueue, Mockito.times(1)).getInstance();
        Mockito.verify(out, Mockito.times(2)).println(Mockito.anyString());
        Mockito.verify(in, Mockito.times(1)).readLine();
        Assert.assertEquals(1, playerEventQueue.getInstance().size());
    }
}


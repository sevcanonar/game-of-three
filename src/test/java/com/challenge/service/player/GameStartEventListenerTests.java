package com.challenge.service.player;

import com.challenge.config.GameStartInformation;
import com.challenge.config.PlayerEventQueue;
import com.challenge.constants.PlayerType;
import com.challenge.event.GameStartEvent;
import com.challenge.service.mock.PlayerEventQueueMock;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

@SpringBootTest
public class GameStartEventListenerTests {
    @InjectMocks
    GameStartEventListener gameStartEventListener;

    @InjectMocks
    GameStartInformation gameStartInformation;


    @Mock
    PrintWriter out;
    @Mock
    Scanner in;
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
        GameStartEvent gameEvent = new GameStartEvent("s", "Start", PlayerType.AUTO);
        Mockito.doReturn(new PlayerEventQueueMock().getPlayerEventQueueEmptyMock()).when(playerEventQueue).getInstance();
        gameStartEventListener.onGameEvent(gameEvent);
        Mockito.verify(playerEventQueue, Mockito.times(1)).getInstance();
        Mockito.verify(out, Mockito.times(3)).println(Mockito.anyString());
        Mockito.verify(in, Mockito.times(0)).nextLine();
        Assert.assertEquals(1, playerEventQueue.getInstance().size());
    }

    @Test
    public void doHandleGameStartEventWhenManualAndInputSuitable() throws IOException {
        GameStartEvent gameEvent = new GameStartEvent("s", "Start", PlayerType.MANUAL);
        Mockito.doReturn("5").when(in).nextLine();
        Mockito.doReturn(new PlayerEventQueueMock().getPlayerEventQueueEmptyMock()).when(playerEventQueue).getInstance();
        gameStartInformation.setInstance(false);
        gameStartEventListener.onGameEvent(gameEvent);
        Mockito.verify(playerEventQueue, Mockito.times(1)).getInstance();
        Mockito.verify(out, Mockito.times(2)).println(Mockito.anyString());
        Mockito.verify(in, Mockito.times(1)).nextLine();
        Assert.assertEquals(1, playerEventQueue.getInstance().size());
    }

}


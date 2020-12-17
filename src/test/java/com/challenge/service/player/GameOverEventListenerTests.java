package com.challenge.service.player;

import com.challenge.config.PlayerEventQueue;
import com.challenge.event.GameOverEvent;
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
public class GameOverEventListenerTests {
    @InjectMocks
    GameOverEventListener gameOverEventListener;

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
    public void doHandleOnGameEvent() throws IOException {
        GameOverEvent gameEvent = new GameOverEvent("s", "Info");
        Mockito.doReturn(new PlayerEventQueueMock().getPlayerEventQueueEmptyMock()).when(playerEventQueue).getInstance();
        Mockito.doNothing().when(out).close();
        Mockito.doNothing().when(clientSocket).close();
        gameOverEventListener.onGameEvent(gameEvent);
        Mockito.verify(playerEventQueue, Mockito.times(0)).getInstance();
        Mockito.verify(out, Mockito.times(1)).println(Mockito.anyString());
        Mockito.verify(out, Mockito.times(1)).close();
        Mockito.verify(clientSocket, Mockito.times(1)).close();
        Mockito.verify(in, Mockito.times(0)).nextLine();
        Assert.assertEquals(0, playerEventQueue.getInstance().size());
    }
}


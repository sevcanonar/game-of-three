package com.challenge.service.player;

import com.challenge.config.PlayerEventQueue;
import com.challenge.event.GameAutoManualInformationEvent;
import com.challenge.service.mock.PlayerEventQueueMock;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.util.ReflectionTestUtils;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;


@SpringBootTest
public class GameAutoManualInfoEventListenerTests {
    @InjectMocks
    GameAutoManualInfoEventListener gameAutoManualInfoEventListener;

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
        ReflectionTestUtils.setField(gameAutoManualInfoEventListener, "playerEventQueue", playerEventQueue);
    }

    @Test
    public void doHandleOnGameEvent() throws IOException {
        GameAutoManualInformationEvent gameEvent = new GameAutoManualInformationEvent("s", "Auto");
        Mockito.doReturn(new PlayerEventQueueMock().getPlayerEventQueueEmptyMock()).when(playerEventQueue).getInstance();
        Mockito.doReturn("A").when(in).nextLine();
        gameAutoManualInfoEventListener.onGameEvent(gameEvent);
        Mockito.verify(playerEventQueue, Mockito.times(1)).getInstance();
        Mockito.verify(out, Mockito.times(2)).println(Mockito.anyString());
        Mockito.verify(in, Mockito.times(1)).nextLine();
        Assert.assertEquals(1, playerEventQueue.getInstance().size());
    }
}


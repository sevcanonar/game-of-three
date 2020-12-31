package com.challenge.service.player;

import com.challenge.config.PlayerEventQueue;
import com.challenge.event.GameAutoManualInformationEvent;
import com.challenge.service.mock.PlayerEventQueueMock;
import com.challenge.service.player.eventlistener.GameAutoManualInfoEventListener;
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

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void doHandleOnGameEvent() {
        GameAutoManualInformationEvent gameEvent = new GameAutoManualInformationEvent("s", "Auto");
        Mockito.doReturn("A").when(in).nextLine();
        gameAutoManualInfoEventListener.onGameEvent(gameEvent);
        Mockito.verify(out, Mockito.times(2)).println(Mockito.anyString());
        Mockito.verify(in, Mockito.times(1)).nextLine();
    }
}


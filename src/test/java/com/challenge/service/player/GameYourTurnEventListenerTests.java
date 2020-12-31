package com.challenge.service.player;

import com.challenge.config.GameStartInformation;
import com.challenge.config.PlayerEventQueue;
import com.challenge.constants.PlayerType;
import com.challenge.event.GameYourTurnEvent;
import com.challenge.service.mock.PlayerEventQueueMock;
import com.challenge.service.player.eventlistener.GameYourTurnEventListener;
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
public class GameYourTurnEventListenerTests {
    @InjectMocks
    GameYourTurnEventListener gameYourTurnEventListener;
    @InjectMocks
    GameStartInformation gameStartInformation;

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
    public void doHandleOnGameEventWhenAuto() throws IOException {
        GameYourTurnEvent gameEvent = new GameYourTurnEvent("s", "Play your turn", PlayerType.AUTO, 5);
        gameYourTurnEventListener.onGameEvent(gameEvent);
        Mockito.verify(out, Mockito.times(3)).println(Mockito.anyString());
        Mockito.verify(in, Mockito.times(0)).nextLine();
    }

    @Test
    public void doHandleOnGameEventWhenManualAndInputSuitable() throws IOException {
        GameYourTurnEvent gameEvent = new GameYourTurnEvent("s", "Play your turn", PlayerType.MANUAL, 5);
        Mockito.doReturn("1").when(in).nextLine();
        gameStartInformation.setInstance(true);
        gameYourTurnEventListener.onGameEvent(gameEvent);
        Mockito.verify(out, Mockito.times(2)).println(Mockito.anyString());
        Mockito.verify(in, Mockito.times(1)).nextLine();
    }
}


/*
package com.challenge.service.player;

import com.challenge.config.PlayerEventQueue;
import com.challenge.constants.PlayerType;
import com.challenge.event.*;
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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

@SpringBootTest
public class GameEventsListenerTest {
    @InjectMocks
    GameEventsListener gameEventsListener;

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
        ReflectionTestUtils.setField(gameEventsListener, "userName", "s");
    }

    @Test
    public void testOnGameStartEventWhenAuto() throws IOException {
        GameStartEvent gameEvent = new GameStartEvent("s", "Start", PlayerType.AUTO);
        Mockito.doReturn(new PlayerEventQueueMock().getPlayerEventQueueEmptyMock()).when(playerEventQueue).getInstance();
        gameEventsListener.onGameStartEvent(gameEvent);
        Mockito.verify(playerEventQueue, Mockito.times(1)).getInstance();
        Mockito.verify(out, Mockito.times(3)).println(Mockito.anyString());
        Mockito.verify(in, Mockito.times(0)).readLine();
        Assert.assertEquals(1, playerEventQueue.getInstance().size());
    }

    @Test
    public void testOnGameStartEventWhenManualAndInputSuitable() throws IOException {
        GameStartEvent gameEvent = new GameStartEvent("s", "Start", PlayerType.MANUAL);
        Mockito.doReturn("5").when(in).readLine();
        Mockito.doReturn(new PlayerEventQueueMock().getPlayerEventQueueEmptyMock()).when(playerEventQueue).getInstance();
        gameEventsListener.onGameStartEvent(gameEvent);
        Mockito.verify(playerEventQueue, Mockito.times(1)).getInstance();
        Mockito.verify(out, Mockito.times(2)).println(Mockito.anyString());
        Mockito.verify(in, Mockito.times(1)).readLine();
        Assert.assertEquals(1, playerEventQueue.getInstance().size());
    }


    @Test
    public void testOnGameStartEventWhenUserNameDifferent() throws IOException {
        GameStartEvent gameEvent = new GameStartEvent("a", "Start", PlayerType.AUTO);
        Mockito.doReturn(new PlayerEventQueueMock().getPlayerEventQueueEmptyMock()).when(playerEventQueue).getInstance();
        gameEventsListener.onGameStartEvent(gameEvent);
        Mockito.verify(playerEventQueue, Mockito.times(0)).getInstance();
        Mockito.verify(out, Mockito.times(0)).println(Mockito.anyString());
        Mockito.verify(in, Mockito.times(0)).readLine();
        Assert.assertEquals(0, playerEventQueue.getInstance().size());
    }

    @Test
    public void testOnYourTurnEventWhenAuto() throws IOException {
        GameYourTurnEvent gameEvent = new GameYourTurnEvent("s", "Play your turn", PlayerType.AUTO, 5);
        Mockito.doReturn(new PlayerEventQueueMock().getPlayerEventQueueEmptyMock()).when(playerEventQueue).getInstance();
        gameEventsListener.onGameYourTurnEvent(gameEvent);
        Mockito.verify(playerEventQueue, Mockito.times(1)).getInstance();
        Mockito.verify(out, Mockito.times(3)).println(Mockito.anyString());
        Mockito.verify(in, Mockito.times(0)).readLine();
        Assert.assertEquals(1, playerEventQueue.getInstance().size());
    }

    @Test
    public void testOnYourTurnEventWhenManual() throws IOException {
        GameYourTurnEvent gameEvent = new GameYourTurnEvent("s", "Play your turn", PlayerType.MANUAL, 5);
        Mockito.doReturn("1").when(in).readLine();
        Mockito.doReturn(new PlayerEventQueueMock().getPlayerEventQueueEmptyMock()).when(playerEventQueue).getInstance();
        gameEventsListener.onGameYourTurnEvent(gameEvent);
        Mockito.verify(playerEventQueue, Mockito.times(1)).getInstance();
        Mockito.verify(out, Mockito.times(2)).println(Mockito.anyString());
        Mockito.verify(in, Mockito.times(1)).readLine();
        Assert.assertEquals(1, playerEventQueue.getInstance().size());
    }

    @Test
    public void testOnYourTurnEventWhenUserNameDifferent() throws IOException {
        GameYourTurnEvent gameEvent = new GameYourTurnEvent("a", "Start", PlayerType.AUTO, 5);
        Mockito.doReturn(new PlayerEventQueueMock().getPlayerEventQueueEmptyMock()).when(playerEventQueue).getInstance();
        gameEventsListener.onGameYourTurnEvent(gameEvent);
        Mockito.verify(playerEventQueue, Mockito.times(0)).getInstance();
        Mockito.verify(out, Mockito.times(0)).println(Mockito.anyString());
        Mockito.verify(in, Mockito.times(0)).readLine();
        Assert.assertEquals(0, playerEventQueue.getInstance().size());
    }

    @Test
    public void testOnGameInformationEvent() throws IOException {
        GameInformationEvent gameEvent = new GameInformationEvent("s", "Info");
        Mockito.doReturn(new PlayerEventQueueMock().getPlayerEventQueueEmptyMock()).when(playerEventQueue).getInstance();
        gameEventsListener.onGameInformationEvent(gameEvent);
        Mockito.verify(playerEventQueue, Mockito.times(0)).getInstance();
        Mockito.verify(out, Mockito.times(1)).println(Mockito.anyString());
        Mockito.verify(in, Mockito.times(0)).readLine();
        Assert.assertEquals(0, playerEventQueue.getInstance().size());
    }

    @Test
    public void testOnGameInformationEventWhenUserDifferent() throws IOException {
        GameInformationEvent gameEvent = new GameInformationEvent("a", "Info");
        Mockito.doReturn(new PlayerEventQueueMock().getPlayerEventQueueEmptyMock()).when(playerEventQueue).getInstance();
        gameEventsListener.onGameInformationEvent(gameEvent);
        Mockito.verify(playerEventQueue, Mockito.times(0)).getInstance();
        Mockito.verify(out, Mockito.times(0)).println(Mockito.anyString());
        Mockito.verify(in, Mockito.times(0)).readLine();
        Assert.assertEquals(0, playerEventQueue.getInstance().size());
    }

    @Test
    public void testOnAutoPlayInformationEvent() throws IOException {
        GameAutoManualInformationEvent gameEvent = new GameAutoManualInformationEvent("s", "Auto");
        Mockito.doReturn(new PlayerEventQueueMock().getPlayerEventQueueEmptyMock()).when(playerEventQueue).getInstance();
        Mockito.doReturn("A").when(in).readLine();
        gameEventsListener.onGameAutoManualInformationEvent(gameEvent);
        Mockito.verify(playerEventQueue, Mockito.times(1)).getInstance();
        Mockito.verify(out, Mockito.times(2)).println(Mockito.anyString());
        Mockito.verify(in, Mockito.times(1)).readLine();
        Assert.assertEquals(1, playerEventQueue.getInstance().size());
    }

    @Test
    public void testOnAutoPlayInformationEventWhenUserDifferent() throws IOException {
        GameAutoManualInformationEvent gameEvent = new GameAutoManualInformationEvent("a", "Auto");
        Mockito.doReturn(new PlayerEventQueueMock().getPlayerEventQueueEmptyMock()).when(playerEventQueue).getInstance();
        gameEventsListener.onGameAutoManualInformationEvent(gameEvent);
        Mockito.verify(playerEventQueue, Mockito.times(0)).getInstance();
        Mockito.verify(out, Mockito.times(0)).println(Mockito.anyString());
        Mockito.verify(in, Mockito.times(0)).readLine();
        Assert.assertEquals(0, playerEventQueue.getInstance().size());
    }

    @Test
    public void testOnGameOverEvent() throws IOException {
        GameOverEvent gameEvent = new GameOverEvent("s", "Info");
        Mockito.doReturn(new PlayerEventQueueMock().getPlayerEventQueueEmptyMock()).when(playerEventQueue).getInstance();
        Mockito.doNothing().when(out).close();
        Mockito.doNothing().when(clientSocket).close();
        gameEventsListener.onGameOverEvent(gameEvent);
        Mockito.verify(playerEventQueue, Mockito.times(0)).getInstance();
        Mockito.verify(out, Mockito.times(1)).println(Mockito.anyString());
        Mockito.verify(out, Mockito.times(1)).close();
        Mockito.verify(clientSocket, Mockito.times(1)).close();
        Mockito.verify(in, Mockito.times(0)).readLine();
        Assert.assertEquals(0, playerEventQueue.getInstance().size());
    }

    @Test
    public void testOnGameOverEventWhenUserDifferent() throws IOException {
        GameOverEvent gameEvent = new GameOverEvent("a", "Info");
        Mockito.doReturn(new PlayerEventQueueMock().getPlayerEventQueueEmptyMock()).when(playerEventQueue).getInstance();
        Mockito.doNothing().when(out).close();
        Mockito.doNothing().when(clientSocket).close();
        gameEventsListener.onGameOverEvent(gameEvent);
        Mockito.verify(playerEventQueue, Mockito.times(0)).getInstance();
        Mockito.verify(out, Mockito.times(0)).println(Mockito.anyString());
        Mockito.verify(out, Mockito.times(0)).close();
        Mockito.verify(clientSocket, Mockito.times(0)).close();
        Mockito.verify(in, Mockito.times(0)).readLine();
        Assert.assertEquals(0, playerEventQueue.getInstance().size());

    }

}
*/

package com.challenge.service.player;

import com.challenge.config.GameListeners;
import com.challenge.event.GameEvent;
import com.challenge.event.GameYourTurnEvent;
import com.challenge.service.mock.GameListenersMock;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;

@SpringBootTest
public class GameEventsConsumerTest {
    @InjectMocks
    GameEventConsumer gameEventConsumer;

    @Mock
    GameListeners gameListeners;


    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void doRegister() throws IOException {
        Mockito.doReturn(new GameListenersMock().getGameListenersEmptyMock()).when(gameListeners).getInstance();
        gameEventConsumer.register(new GameEventsListener("a", null, null, null, null));
        Assert.assertEquals(1, gameListeners.getInstance().size());
    }

    @Ignore
    @Test
    public void doCreateGameYourTurnEvent() {
        Mockito.doReturn(new GameListenersMock().getGameListenersOneMock()).when(gameListeners).getInstance();
        GameEventsListener gameListener = Mockito.mock(GameEventsListener.class);
        Mockito.doNothing().when(gameListener).onGameYourTurnEvent(Mockito.any());
        GameEvent gameEvent = new GameYourTurnEvent("a", "Opponent start value is " + 5);
        gameEventConsumer.createEvent(gameEvent);
        Assert.assertEquals(1, gameListeners.getInstance().size());
        Mockito.verify(gameListener).onGameYourTurnEvent(Mockito.any());
    }
}

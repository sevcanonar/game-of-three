package com.challenge.service.game;

import com.challenge.config.PlayerEventQueue;
import com.challenge.event.PlayerEvent;
import com.challenge.service.mock.MiddleMovePlayedPlayerEventMock;
import com.challenge.service.mock.StartSelectionPlayerEventMock;
import com.challenge.service.mock.UserLoginPlayerEventMock;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingDeque;

@SpringBootTest
public class PlayerEventConsumerServiceTests {

    @InjectMocks
    PlayerEventConsumerService playerEventConsumerService;

    @Mock
    UserLoginHandlingService userLoginHandlingService;

    @Mock
    AutoManualSelectionHandlingService autoManualSelectionHandlingService;

    @Mock
    MovePlayedHandlingService movePlayedHandlingService;

    @Mock
    PlayerEventQueue playerEventQueue;

    ExecutorService executor;
    ;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
        executor = Executors.newFixedThreadPool(1);
    }

    @Test
    public void testRunWithLogin() throws InterruptedException {
        BlockingQueue<PlayerEvent> playerEvents = new LinkedBlockingDeque<>();
        playerEvents.put(new UserLoginPlayerEventMock());
        Mockito.doReturn(playerEvents).when(playerEventQueue).getInstance();
        Mockito.doNothing().when(userLoginHandlingService).handle(Mockito.any(PlayerEvent.class), Mockito.any(Map.class));
        executor.execute(playerEventConsumerService);
        executor.shutdownNow();
    }

    @Test
    public void testRunWithMovePlayed() throws InterruptedException {
        BlockingQueue<PlayerEvent> playerEvents = new LinkedBlockingDeque<>();
        playerEvents.put(new MiddleMovePlayedPlayerEventMock());
        Mockito.doReturn(playerEvents).when(playerEventQueue).getInstance();
        Mockito.doNothing().when(movePlayedHandlingService).handle(Mockito.any(PlayerEvent.class), Mockito.any(Map.class));
        executor.execute(playerEventConsumerService);
        executor.shutdownNow();
    }

    @Test
    public void testRunWithAutoManualSelection() throws InterruptedException {
        BlockingQueue<PlayerEvent> playerEvents = new LinkedBlockingDeque<>();
        playerEvents.put(new StartSelectionPlayerEventMock());
        Mockito.doReturn(playerEvents).when(playerEventQueue).getInstance();
        Mockito.doNothing().when(autoManualSelectionHandlingService).handle(Mockito.any(PlayerEvent.class), Mockito.any(Map.class));
        executor.execute(playerEventConsumerService);
        executor.shutdownNow();
    }
}

package com.challenge.service.game;

import com.challenge.config.PlayerEventQueue;
import com.challenge.event.PlayerEvent;
import com.challenge.service.mock.AutoManualSelectionPlayerEventMock;
import com.challenge.service.mock.MiddleMovePlayedPlayerEventMock;
import com.challenge.service.mock.PlayerEventQueueMock;
import com.challenge.service.mock.UserLoginPlayerEventMock;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.util.ReflectionTestUtils;

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

    @Mock
    BlockingQueue<PlayerEvent> playerEvents;

    ExecutorService executor;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
        executor = Executors.newFixedThreadPool(1);
        ReflectionTestUtils.setField(playerEventConsumerService, "playerEvents", playerEvents);
    }

    @Test
    public void testRunWithLogin() throws InterruptedException {
        Mockito.doReturn(playerEvents).when(playerEventQueue).getInstance();
        Mockito.doReturn(new UserLoginPlayerEventMock(), null).when(playerEvents).take();
        Mockito.doNothing().when(userLoginHandlingService).handle(Mockito.any(PlayerEvent.class), Mockito.any(Map.class));
        playerEventConsumerService.run();
        Mockito.verify(userLoginHandlingService, Mockito.times(1)).handle(Mockito.any(PlayerEvent.class), Mockito.any(Map.class));
    }

    @Test
    public void testRunWithMovePlayed() throws InterruptedException {
        Mockito.doReturn(new MiddleMovePlayedPlayerEventMock(), null).when(playerEvents).take();
        Mockito.doNothing().when(movePlayedHandlingService).handle(Mockito.any(PlayerEvent.class), Mockito.any(Map.class));
        playerEventConsumerService.run();
        Mockito.verify(movePlayedHandlingService, Mockito.times(1)).handle(Mockito.any(PlayerEvent.class), Mockito.any(Map.class));
    }

    @Test
    public void testRunWithAutoManualSelection() throws InterruptedException {
        Mockito.doReturn(new AutoManualSelectionPlayerEventMock(), null).when(playerEvents).take();
        Mockito.doNothing().when(autoManualSelectionHandlingService).handle(Mockito.any(PlayerEvent.class), Mockito.any(Map.class));
        playerEventConsumerService.run();
        Mockito.verify(autoManualSelectionHandlingService, Mockito.times(1)).handle(Mockito.any(PlayerEvent.class), Mockito.any(Map.class));
    }
}

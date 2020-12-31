package com.challenge.config;

import com.challenge.constants.GameListenerType;
import com.challenge.service.mock.GameListenersPerPlayerMock;
import com.challenge.service.player.eventlistener.GameListener;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Map;

@SpringBootTest
public class GameListenersPerPlayerTests {
    public Map<String, Map<GameListenerType, GameListener>> gameListeners = new GameListenersPerPlayerMock().getGameListenersOneMock();
    @InjectMocks
    GameListenersPerPlayer gameListenersPerPlayer;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
        ReflectionTestUtils.setField(gameListenersPerPlayer, "gameListeners", gameListeners);
    }

    @Test
    public void testGetInstance() throws InterruptedException {
        Assert.assertEquals(1, gameListenersPerPlayer.getInstance().size());
    }

}

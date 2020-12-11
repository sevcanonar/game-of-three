package com.challenge.config;

import com.challenge.event.PlayerEvent;
import com.challenge.service.mock.UserLoginPlayerEventMock;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;

@SpringBootTest
public class PlayerEventQueueTests {

    public BlockingQueue<PlayerEvent> queue = new LinkedBlockingDeque<>();
    @InjectMocks
    PlayerEventQueue playerEventQueue;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
        ReflectionTestUtils.setField(playerEventQueue, "queue", queue);
    }

    @Test
    public void testGetInstance() throws InterruptedException {
        queue.put(new UserLoginPlayerEventMock());
        BlockingQueue<PlayerEvent> queueInstance = playerEventQueue.getInstance();
        Assert.assertEquals(1, queueInstance.size());
    }

}

package com.challenge.config;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

@SpringBootTest
public class ThreadConfigTests {
    @InjectMocks
    ThreadConfig threadConfig;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testClientThreadPoolTaskExecutor() {
        ThreadPoolExecutor customThreadPoolExecutor = threadConfig.clientThreadPoolTaskExecutor();
        Assert.assertEquals(2, customThreadPoolExecutor.getCorePoolSize());
        Assert.assertEquals(10, customThreadPoolExecutor.getMaximumPoolSize());
        Assert.assertEquals(0l, customThreadPoolExecutor.getKeepAliveTime(TimeUnit.MILLISECONDS));
        Assert.assertEquals(0, customThreadPoolExecutor.getQueue().size());
    }

    @Test
    public void testServerThreadPoolTaskExecutor() {
        ThreadPoolExecutor customThreadPoolExecutor = threadConfig.serverThreadPoolTaskExecutor();
        Assert.assertEquals(1, customThreadPoolExecutor.getCorePoolSize());
        Assert.assertEquals(10, customThreadPoolExecutor.getMaximumPoolSize());
        Assert.assertEquals(0l, customThreadPoolExecutor.getKeepAliveTime(TimeUnit.MILLISECONDS));
        Assert.assertEquals(0, customThreadPoolExecutor.getQueue().size());
    }

    @Test
    public void testServerSocketCreation() throws IOException {
        ServerSocket serverSocket = threadConfig.serverSocket();
        Assert.assertEquals(6666, serverSocket.getLocalPort());
    }
}

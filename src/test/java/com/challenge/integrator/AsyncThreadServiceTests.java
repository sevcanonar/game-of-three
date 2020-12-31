package com.challenge.integrator;

import com.challenge.service.GameEventsRegisterer;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import java.net.ServerSocket;
import java.util.concurrent.ThreadPoolExecutor;

@SpringBootTest
public class AsyncThreadServiceTests {
    @InjectMocks
    AsyncThreadService asyncThreadService;

    @Mock
    private ThreadPoolExecutor threadPoolExecutor;

    @Mock
    private ServerSocket serverSocket;

    @Mock
    private GameEventsRegisterer gameEventsRegisterer;

    @Test
    public void testExecuteAsynchronously() {
        MockitoAnnotations.initMocks(this);
        asyncThreadService.executeAsynchronously();
    }
}

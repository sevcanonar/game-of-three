package com.challenge.integrator;

import com.challenge.service.initialization.EventRegisterer;
import com.challenge.service.initialization.GameEventsRegisterer;
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
    private EventRegisterer gameEventsRegisterer;

    @Test
    public void testExecuteAsynchronously() {
        MockitoAnnotations.initMocks(this);
        asyncThreadService.executeAsynchronously();
    }
}

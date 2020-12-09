package com.challenge.integrator;

import com.challenge.config.PlayerEventQueue;
import com.challenge.service.player.GameEventsConsumer;
import com.challenge.service.player.MultiplePlayerMeeterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import java.net.ServerSocket;
import java.util.concurrent.ThreadPoolExecutor;

@Service
public class AsyncThreadService {

    @Autowired
    ApplicationContext applicationContext;
    @Autowired
    @Qualifier("taskExecutor")
    private ThreadPoolExecutor threadPoolExecutor;
    @Autowired
    @Qualifier("serverSocket")
    private ServerSocket serverSocket;
    @Autowired
    private GameEventsConsumer gameEventsConsumer;
    @Autowired
    private PlayerEventQueue playerEventQueue;

    public void executeAsynchronously() {
        threadPoolExecutor.execute(new MultiplePlayerMeeterService(serverSocket, gameEventsConsumer, playerEventQueue));
    }
}
package com.challenge.integrator;

import com.challenge.config.PlayerEventQueue;
import com.challenge.service.player.GameEventsConsumer;
import com.challenge.service.player.MultiplePlayerMeeterService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.net.ServerSocket;
import java.util.concurrent.ThreadPoolExecutor;

@Service
public class AsyncThreadService {

    private static final Logger LOG = LoggerFactory.getLogger(MultiplePlayerMeeterService.class);

    ThreadPoolExecutor threadPoolExecutor;
    ServerSocket serverSocket;
    GameEventsConsumer gameEventsConsumer;
    PlayerEventQueue playerEventQueue;

    public AsyncThreadService(@Qualifier("taskExecutor") ThreadPoolExecutor threadPoolExecutor, @Qualifier("serverSocket") ServerSocket serverSocket, GameEventsConsumer gameEventsConsumer, PlayerEventQueue playerEventQueue) {
        this.threadPoolExecutor = threadPoolExecutor;
        this.serverSocket = serverSocket;
        this.gameEventsConsumer = gameEventsConsumer;
        this.playerEventQueue = playerEventQueue;
    }

    public void executeAsynchronously() {
        try {
            threadPoolExecutor.execute(new MultiplePlayerMeeterService(serverSocket, gameEventsConsumer, playerEventQueue));
        } catch (Exception e) {
            LOG.debug("Game is interrupted");
            System.exit(-1);
        }
    }
}
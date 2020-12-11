package com.challenge.integrator;

import com.challenge.config.PlayerEventQueue;
import com.challenge.service.player.GameEventsConsumer;
import com.challenge.service.player.MultiplePlayerMeeterService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.net.ServerSocket;
import java.util.concurrent.ThreadPoolExecutor;

@Service
public class AsyncThreadService {

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
        threadPoolExecutor.execute(new MultiplePlayerMeeterService(serverSocket, gameEventsConsumer, playerEventQueue));
    }
}
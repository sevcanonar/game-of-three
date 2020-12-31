package com.challenge.integrator;

import com.challenge.config.PlayerEventQueue;
import com.challenge.constants.ExceptionalMessages;
import com.challenge.service.initialization.EventRegisterer;
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
    EventRegisterer eventRegisterer;
    PlayerEventQueue playerEventQueue;

    public AsyncThreadService(@Qualifier("taskExecutor") ThreadPoolExecutor threadPoolExecutor, @Qualifier("serverSocket") ServerSocket serverSocket, EventRegisterer eventRegisterer, PlayerEventQueue playerEventQueue) {
        this.threadPoolExecutor = threadPoolExecutor;
        this.serverSocket = serverSocket;
        this.eventRegisterer = eventRegisterer;
        this.playerEventQueue = playerEventQueue;
    }

    public void executeAsynchronously() {
        try {
            threadPoolExecutor.execute(new MultiplePlayerMeeterService(serverSocket, eventRegisterer, playerEventQueue));
        } catch (Exception e) {
            LOG.debug(ExceptionalMessages.GAME_IS_INTERRUPTED_EXTERNALLY);
            System.exit(-1);
        }
    }
}
package com.challenge.player.service;

import com.challenge.player.listener.GameEventRegistererCaller;
import com.challenge.player.integrator.MultiplePlayerHandler;
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
    @Qualifier("clientHandlerTaskExecutor")
    private ThreadPoolExecutor threadPoolExecutor;
    @Autowired
    @Qualifier("serverSocket")
    private ServerSocket serverSocket;
    @Autowired
    private GameEventRegistererCaller gameEventRegistererCaller;

    public void executeAsynchronously() {
        threadPoolExecutor.execute(new MultiplePlayerHandler(serverSocket, gameEventRegistererCaller));
    }
}
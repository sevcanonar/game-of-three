package com.challenge.service;

import com.challenge.integrator.MultiplePlayerHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.net.ServerSocket;
import java.util.concurrent.ThreadPoolExecutor;

@Service
public class AsyncThreadService {

    @Autowired
    @Qualifier("clientHandlerTaskExecutor")
    private ThreadPoolExecutor threadPoolExecutor;

    @Autowired
    @Qualifier("serverSocket")
    private ServerSocket serverSocket;

    public void executeAsynchronously() {
            threadPoolExecutor.execute(new MultiplePlayerHandler(serverSocket));
    }
}
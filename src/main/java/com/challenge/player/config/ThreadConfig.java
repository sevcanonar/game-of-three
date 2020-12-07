package com.challenge.player.config;

import com.challenge.player.service.CustomThreadPoolExecutor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

@Configuration
@EnableAsync
public class ThreadConfig {

    @Bean(name = "clientHandlerTaskExecutor")
    public ThreadPoolExecutor clientThreadPoolTaskExecutor() {
        return new CustomThreadPoolExecutor(2, 10, 0l, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<>());
    }

    @Bean(name = "serverHandlerTaskExecutor")
    public ThreadPoolExecutor serverThreadPoolTaskExecutor() {
        return new CustomThreadPoolExecutor(1, 10, 0l, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<>());
    }

    @Bean(name = "serverSocket")
    public ServerSocket serverSocket() throws IOException {
        return new ServerSocket(6666);
    }
}

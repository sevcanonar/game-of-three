package com.challenge;

import com.challenge.integrator.GameSocketServer;
import com.challenge.service.game.PlayerEventConsumerService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import java.util.concurrent.ThreadPoolExecutor;

@SpringBootApplication(scanBasePackages = {"com.challenge"})
public class Application {

    public static void main(final String[] args) {
        ApplicationContext applicationContext = SpringApplication.run(Application.class, args);
        ThreadPoolExecutor serverThreadPoolExecutor = (ThreadPoolExecutor) applicationContext.getBean("serverHandlerTaskExecutor");
        PlayerEventConsumerService playerEventConsumerService = applicationContext.getBean(PlayerEventConsumerService.class);
        serverThreadPoolExecutor.execute(playerEventConsumerService);
        GameSocketServer server = applicationContext.getBean(GameSocketServer.class);
        server.start();
    }
}

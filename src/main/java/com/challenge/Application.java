package com.challenge;

import com.challenge.integrator.GameSocketServer;
import com.challenge.service.game.PlayerEventConsumerService;
import com.challenge.service.player.MultiplePlayerMeeterService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import java.util.concurrent.ThreadPoolExecutor;

@SpringBootApplication(scanBasePackages = {"com.challenge"})
public class Application {

    public static void main(final String[] args) {
        ApplicationContext applicationContext = SpringApplication.run(Application.class, args);
        ThreadPoolExecutor serverThreadPoolExecutor = (ThreadPoolExecutor)applicationContext.getBean("serverHandlerTaskExecutor");
        ThreadPoolExecutor clientThreadPoolExecutor = (ThreadPoolExecutor)applicationContext.getBean("taskExecutor");
        PlayerEventConsumerService playerEventConsumerService = applicationContext.getBean(PlayerEventConsumerService.class);
        MultiplePlayerMeeterService multiplePlayerMeeterService = applicationContext.getBean(MultiplePlayerMeeterService.class);
        clientThreadPoolExecutor.execute(multiplePlayerMeeterService);
        serverThreadPoolExecutor.execute(playerEventConsumerService);
        GameSocketServer server = applicationContext.getBean(GameSocketServer.class);
        server.start();
    }
}

package com.challenge;

import com.challenge.player.integrator.GameSocketServer;
import com.challenge.player.integrator.GameHandler;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import java.util.concurrent.ThreadPoolExecutor;

@SpringBootApplication(scanBasePackages = {"com.challenge"})
public class Application {

    public static void main(final String[] args) {
        ApplicationContext applicationContext = SpringApplication.run(Application.class, args);
        ThreadPoolExecutor serverThreadPoolExecutor = (ThreadPoolExecutor)applicationContext.getBean("serverHandlerTaskExecutor");
        GameHandler gameHandler = applicationContext.getBean(GameHandler.class);
        serverThreadPoolExecutor.execute(gameHandler);
        GameSocketServer server = applicationContext.getBean(GameSocketServer.class);
        server.start();
    }
}

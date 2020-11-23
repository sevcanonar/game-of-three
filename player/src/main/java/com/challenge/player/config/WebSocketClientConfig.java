package com.challenge.player.config;

import com.challenge.player.session.PlayerSessionHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.messaging.simp.stomp.StompSessionHandler;
import org.springframework.web.socket.client.WebSocketClient;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.messaging.WebSocketStompClient;

import java.util.concurrent.ExecutionException;

@Configuration
public class WebSocketClientConfig {

    private static String URL = "ws://localhost:8080/chat";

    /**
     * Provide Stomp Session Handler
     *
     * @return
     */
    @Bean
    protected StompSessionHandler provideStompSessionHandler() {
        return new PlayerSessionHandler();
    }

    /**
     * Provide WebSocket Client
     *
     * @return
     */
    @Bean
    protected WebSocketClient provideWebSocketClient() {
        return new StandardWebSocketClient();
    }

    /**
     * Provide StompSession session
     *
     * @param sessionHandler
     * @param webSocketClient
     * @return
     * @throws java.lang.InterruptedException
     * @throws java.util.concurrent.ExecutionException
     */
    @Bean
    protected StompSession provideStompSession(final StompSessionHandler sessionHandler, final WebSocketClient webSocketClient) throws InterruptedException, ExecutionException {
        final WebSocketStompClient stompClient = new WebSocketStompClient(webSocketClient);
        stompClient.setMessageConverter(new MappingJackson2MessageConverter());
        return stompClient.connect(URL, sessionHandler).get();
    }
}

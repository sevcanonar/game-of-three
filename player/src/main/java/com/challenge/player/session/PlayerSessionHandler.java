package com.challenge.player.session;

import com.challenge.player.event.IncomingEvent.IncomingEventHandler;
import com.challenge.player.model.PlayerEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.simp.stomp.*;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.client.WebSocketClient;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.messaging.WebSocketStompClient;

import java.lang.reflect.Type;
import java.util.concurrent.ExecutionException;

@Component
public class PlayerSessionHandler extends StompSessionHandlerAdapter {

    private static String URL = "ws://localhost:8080/chat";

    @Autowired
    IncomingEventHandler incomingEventHandler;

    @Bean
    protected WebSocketClient provideWebSocketClient() {
        return new StandardWebSocketClient();
    }

    @Bean
    protected StompSession provideStompSession(final WebSocketClient webSocketClient) throws InterruptedException, ExecutionException {
        final WebSocketStompClient stompClient = new WebSocketStompClient(webSocketClient);
        stompClient.setMessageConverter(new MappingJackson2MessageConverter());
        return stompClient.connect(URL, this).get();
    }

    @Override
    public void afterConnected(StompSession session, StompHeaders connectedHeaders) {
        System.out.println("New session established : " + session.getSessionId());
    }

    @Override
    public void handleException(StompSession session, StompCommand command, StompHeaders headers, byte[] payload, Throwable exception) {
        System.out.println("Got an exception");
    }

    @Override
    public Type getPayloadType(StompHeaders headers) {
        return PlayerEvent.class;
    }

    @Override
    public void handleFrame(StompHeaders headers, Object payload) {
        PlayerEvent incomingEvent = (PlayerEvent) payload;
        incomingEventHandler.handleIncomingEvent(incomingEvent);
    }
}
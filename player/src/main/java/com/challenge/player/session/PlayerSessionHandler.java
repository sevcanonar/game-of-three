package com.challenge.player.session;

import com.challenge.player.model.PlayerMessage;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaders;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.messaging.simp.stomp.StompSessionHandlerAdapter;

import java.lang.reflect.Type;

public class PlayerSessionHandler extends StompSessionHandlerAdapter {


    @Override
    public void afterConnected(StompSession session, StompHeaders connectedHeaders) {
        System.out.println("New session established : " + session.getSessionId());
        session.subscribe("/topic", this);
    }

    @Override
    public void handleException(StompSession session, StompCommand command, StompHeaders headers, byte[] payload, Throwable exception) {
        System.out.println("Got an exception");
    }

    @Override
    public Type getPayloadType(StompHeaders headers) {
        return PlayerMessage.class;
    }

    @Override
    public void handleFrame(StompHeaders headers, Object payload) {
        PlayerMessage msg = (PlayerMessage) payload;
        System.out.println("Received : " + msg);
    }
/*

    private PlayerMessage getSampleMessage() {
        PlayerMessage msg = new PlayerMessage();
        msg.setFrom("Nicky");
        msg.setText("Howdy!!");
        return msg;
    }*/
}
package com.challenge.game.controller;

import com.challenge.game.model.PlayerMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import java.text.SimpleDateFormat;
import java.util.Date;

@Controller
public class MessageHandlingController {

    @Autowired
    private SimpMessagingTemplate messagingTemplate;


    @MessageMapping("/chat")
    public void handlePlayerMessage(PlayerMessage message) throws Exception {
        String time = new SimpleDateFormat("HH:mm").format(new Date());
        System.out.println("message received from client " + "from: " + message.getFrom() + " text: " + message.getText());
        messagingTemplate.convertAndSend("/topic", message);
    }
}

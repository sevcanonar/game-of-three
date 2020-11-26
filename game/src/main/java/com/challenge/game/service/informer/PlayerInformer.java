package com.challenge.game.service.informer;

import com.challenge.game.model.PlayerEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
public class PlayerInformer implements PlayerInformerService {

    @Autowired
    SimpMessagingTemplate playerInformerEventTemplate;

    @Override
    public void informPlayer(PlayerEvent playerEvent) {

        System.out.println("message received from client " + "from: " + playerEvent.getFrom() + " text: " + playerEvent.getTo());
        playerInformerEventTemplate.convertAndSend("/topic/"+playerEvent.getTo(), playerEvent);
    }
}

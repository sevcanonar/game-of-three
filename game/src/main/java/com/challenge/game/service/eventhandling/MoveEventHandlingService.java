package com.challenge.game.service.eventhandling;

import com.challenge.game.config.GameCache;
import com.challenge.game.constants.EventType;
import com.challenge.game.model.PlayerEvent;
import com.challenge.game.service.informer.PlayerInformerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class MoveEventHandlingService {
    @Autowired
    private PlayerInformerService playerInformerService;

    public void handlePlayerEvent(PlayerEvent playerEvent) {
        System.out.println("asdasdkadkdsk");
    }
}

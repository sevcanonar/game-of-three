package com.challenge.service.mock;

import com.challenge.config.PlayerEventQueue;
import com.challenge.constants.PlayerEventType;
import com.challenge.constants.PlayerType;
import com.challenge.event.PlayerEvent;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;

public class PlayerEventQueueMock extends PlayerEventQueue {
    public BlockingQueue<PlayerEvent> getPlayerEventQueueEmptyMock() {
        return new LinkedBlockingDeque<>();
    }

    public BlockingQueue<PlayerEvent> getPlayerEventQueueUserLoginMock() throws InterruptedException {
        BlockingQueue<PlayerEvent> playerEvents = new LinkedBlockingDeque<>();
        PlayerEvent playerEvent = new PlayerEvent("a", PlayerEventType.USER_LOGIN, "Y");
        playerEvents.put(playerEvent);
        return playerEvents;
    }
}

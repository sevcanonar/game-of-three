package com.challenge.service.mock;

import com.challenge.config.PlayerEventQueue;
import com.challenge.constants.PlayerEventType;
import com.challenge.constants.PlayerType;
import com.challenge.event.PlayerEvent;
import com.challenge.service.player.GameEventsListener;
import com.challenge.service.player.GameListener;

import java.util.Collection;
import java.util.HashSet;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;

public class PlayerEventQueueMock extends PlayerEventQueue {
    public BlockingQueue<PlayerEvent> getPlayerEventQueueEmptyMock() {
        return new LinkedBlockingDeque<>();
    }

    public BlockingQueue<PlayerEvent> getPlayerEventQueueUserLoginMock() throws InterruptedException {
        BlockingQueue<PlayerEvent> playerEvents = new LinkedBlockingDeque<>();
        PlayerEvent playerEvent = new PlayerEvent("a", PlayerEventType.USER_LOGIN,"Y", PlayerType.NONE);
        playerEvents.put(playerEvent);
        return playerEvents;
    }
}

package com.challenge.service.mock;

import com.challenge.constants.PlayerEventType;
import com.challenge.constants.PlayerType;
import com.challenge.event.PlayerEvent;

public class MiddleMovePlayedPlayerEventMock extends PlayerEvent {
    public MiddleMovePlayedPlayerEventMock() {
        super("a", PlayerEventType.MOVE_IS_PLAYED, "1");
    }
}

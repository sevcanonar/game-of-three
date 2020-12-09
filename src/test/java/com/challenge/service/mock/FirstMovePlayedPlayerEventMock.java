package com.challenge.service.mock;

import com.challenge.constants.PlayerEventType;
import com.challenge.constants.PlayerType;
import com.challenge.event.PlayerEvent;

public class FirstMovePlayedPlayerEventMock extends PlayerEvent {
    public FirstMovePlayedPlayerEventMock() {
        super("a", PlayerEventType.MOVE_IS_PLAYED, "5", PlayerType.NONE);
    }
}

package com.challenge.service.mock;

import com.challenge.constants.PlayerEventType;
import com.challenge.constants.PlayerType;
import com.challenge.event.PlayerEvent;

public class StartSelectionPlayerEventMock extends PlayerEvent {
    public StartSelectionPlayerEventMock() {
        super("a", PlayerEventType.START_SELECTION, "Y", PlayerType.NONE);
    }
}

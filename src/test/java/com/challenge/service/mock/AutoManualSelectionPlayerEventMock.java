package com.challenge.service.mock;

import com.challenge.constants.PlayerEventType;
import com.challenge.constants.PlayerType;
import com.challenge.event.PlayerEvent;

public class AutoManualSelectionPlayerEventMock extends PlayerEvent {
    public AutoManualSelectionPlayerEventMock() {
        super("auto", PlayerEventType.AUTO_MANUAL_SELECTION, "Y");
    }
}

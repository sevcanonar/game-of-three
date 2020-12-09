package com.challenge.service.mock;

import com.challenge.constants.PlayerEventType;
import com.challenge.constants.PlayerType;
import com.challenge.event.PlayerEvent;

public class UserLoginPlayerEventMock extends PlayerEvent {
    public UserLoginPlayerEventMock() {
        super("log", PlayerEventType.USER_LOGIN, "a", PlayerType.NONE);
    }
}

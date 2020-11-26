package com.challenge.player.service;

import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.messaging.simp.stomp.StompSessionHandler;

public interface PlayerLoginService {
    void scanPlayerCredentials();
}

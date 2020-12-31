package com.challenge.service.game.eventbuilder;

import com.challenge.event.GameEvent;
import com.challenge.event.PlayerEvent;
import com.challenge.model.PlayerMoveInfo;

import java.util.List;

public interface GameEventBuilder {
    List<GameEvent> build(PlayerEvent playerEvent, PlayerMoveInfo playedUserInfo);
}

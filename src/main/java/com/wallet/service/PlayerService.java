package com.wallet.service;


import com.fasterxml.jackson.core.JsonProcessingException;

import com.wallet.model.Player;

import java.util.Map;


public interface PlayerService {
    void createPlayer(Player player);
    Player findPlayer(Long playerId);
    Player updatePlayer(Long playerId, Map<Object,Object> objectMap);
}

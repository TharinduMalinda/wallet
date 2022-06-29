package com.wallet.service;

import com.wallet.dto.PlayerRequestDTO;
import com.wallet.model.Player;
import java.util.Map;

public interface PlayerService {

    Player createPlayer(PlayerRequestDTO playerRequestDTO) ;
    Player findPlayer(Long playerId);
    Player updatePlayer(Long playerId, Map<Object,Object> objectMap);

}

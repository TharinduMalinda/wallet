package com.wallet.controller;


import com.wallet.dto.PlayerDTO;
import com.wallet.dto.ResponseDTO;
import com.wallet.mapper.Mapper;
import com.wallet.model.Player;
import com.wallet.service.PlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Map;


@RestController
@RequestMapping("/player")

public class PlayerController {

    @Autowired
    PlayerService playerService;

    @Autowired
    Mapper mapper;

    @GetMapping("/{playerId}")
    public ResponseEntity<PlayerDTO> test(@PathVariable Long playerId){
        return mapper.playerDtoMapper(playerService.findPlayer(playerId));
    }


    @PostMapping
    public ResponseEntity<ResponseDTO> createPlayer(@RequestBody @Valid Player player)  {
        playerService.createPlayer(player);
        return new ResponseEntity<>(new ResponseDTO("success","Process successful"), HttpStatus.CREATED);

    }
    @PatchMapping(path = "/{playerId}" )
    public ResponseEntity<PlayerDTO> updateCustomer(@PathVariable Long playerId, @RequestBody Map<Object,Object> objectMap) {

        Player player = playerService.updatePlayer(playerId,objectMap);
        return mapper.playerDtoMapper(player);

    }


}

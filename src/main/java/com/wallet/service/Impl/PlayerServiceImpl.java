package com.wallet.service.Impl;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wallet.exception.ResourceAlreadyExistException;
import com.wallet.exception.ResourceNotFoundException;
import com.wallet.exception.StaticFieldUpdateException;
import com.wallet.model.Player;
import com.wallet.repository.AccountRepository;
import com.wallet.repository.PlayerRepository;
import com.wallet.service.AccountService;
import com.wallet.service.PlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.Map;


@Service
public class PlayerServiceImpl implements PlayerService {
    @Autowired
    PlayerRepository playerRepository;

    @Autowired
    AccountRepository accountRepository;

    @Lazy
    @Autowired
    AccountService accountService;

    private ObjectMapper objectMapper;

    @Override
    @Transactional
    public void createPlayer(Player player) {
        try{
            findPlayer(player.getPlayerId());
            throw new ResourceAlreadyExistException("Player already exist in the system");
        }catch (ResourceNotFoundException ex){
            try {
                accountService.findAccountByAccId(player.getAccount().getAccountId());
                throw new ResourceAlreadyExistException("Account already exist in the system");
            }catch (ResourceNotFoundException e) {
                //set the same status as Player
                player.getAccount().setAccountStatus(player.getPlayerStatus());
                accountRepository.save(player.getAccount());
                playerRepository.save(player);
            }
        }
    }

    @Override
    public Player findPlayer(Long playerId) {
        return playerRepository.findByPlayerId(playerId)
                .orElseThrow(()-> new ResourceNotFoundException("No such player in the system"));



    }

    public Player updatePlayer(Long playerId, Map<Object,Object> objectMap){
        Player player = findPlayer(playerId);
        objectMap.forEach((key,value) -> {
            Field field = ReflectionUtils.findField(Player.class,(String) key);
            field.setAccessible(true);
            if(field.getName().equals("playerId")){
                throw new StaticFieldUpdateException("Can not update 'playerId' field");
            }
            ReflectionUtils.setField(field,player,value);
        });

        return playerRepository.save(player);


    }




}

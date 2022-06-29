package com.wallet.service.impl;

import com.wallet.dto.PlayerRequestDTO;
import com.wallet.exception.ResourceAlreadyExistException;
import com.wallet.exception.ResourceNotFoundException;
import com.wallet.exception.StaticFieldUpdateException;
import com.wallet.mapper.Mapper;
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

/**
 * player related services and logics.
 *
 * @author Malinda
 *
 */

@Service
public class PlayerServiceImpl implements PlayerService {

    @Autowired
    private PlayerRepository playerRepository;

    @Autowired
    private AccountRepository accountRepository;

    @Lazy
    @Autowired
    private AccountService accountService;

    @Autowired
    private Mapper mapper;

    //create a new player,account instances, if there is no such player,account
    @Override
    @Transactional
    public Player createPlayer(PlayerRequestDTO playerRequestDTO) {
        Player player = mapper.playerRequestDtoMapper(playerRequestDTO);
        Player pl;

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
                pl = playerRepository.save(player);
            }
        }
        return  pl;
    }

    @Override
    public Player findPlayer(Long playerId) {
        return playerRepository.findByPlayerId(playerId)
                .orElseThrow(()-> new ResourceNotFoundException("No such player in the system"));

    }

    // update player details. can not update playerId
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

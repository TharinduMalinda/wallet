package com.wallet.controller;


import com.wallet.dto.AuthRequestDTO;
import com.wallet.dto.PlayerDTO;
import com.wallet.dto.ResponseDTO;
import com.wallet.mapper.Mapper;
import com.wallet.model.Player;
import com.wallet.service.Impl.UserDetailsServiceImpl;
import com.wallet.service.PlayerService;
import com.wallet.util.JwtUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Map;


@RestController
@RequestMapping("/player")
@Api(tags = "Player APIs")
public class PlayerController {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private PlayerService playerService;

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @Autowired
    private Mapper mapper;

    @Autowired
    private JwtUtil jwtUtil;

    @GetMapping("/{playerId}")
    public ResponseEntity<PlayerDTO> test(@PathVariable Long playerId){
        return mapper.playerDtoMapper(playerService.findPlayer(playerId));
    }

    @PostMapping("/auth")
    public ResponseEntity<?> createAuthToken (@RequestBody @Valid AuthRequestDTO authRequestDTO){
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        authRequestDTO.getUserName(),authRequestDTO.getPassword()
                )
        );

        final UserDetails userDetails = userDetailsService.loadUserByUsername(authRequestDTO.getUserName());
        final String jwt = jwtUtil.generateToken(userDetails.getUsername());

        return ResponseEntity.ok(jwt);
    }


    @PostMapping
    public ResponseEntity<ResponseDTO> createPlayer(@RequestBody @Valid Player player)  {
        playerService.createPlayer(player);
        return new ResponseEntity<>(new ResponseDTO("success","Process successful"), HttpStatus.CREATED);

    }
    @PatchMapping(path = "/{playerId}" )
    public ResponseEntity<PlayerDTO> updatePlayer(@PathVariable Long playerId, @RequestBody Map<Object,Object> objectMap) {

        Player player = playerService.updatePlayer(playerId,objectMap);
        return mapper.playerDtoMapper(player);

    }


}

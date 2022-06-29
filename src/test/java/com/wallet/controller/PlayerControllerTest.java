package com.wallet.controller;


import com.wallet.model.Account;
import com.wallet.model.Player;
import com.wallet.model.ResourceStatus;
import com.wallet.repository.AccountRepository;
import com.wallet.repository.PlayerRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.*;
import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class PlayerControllerTest  {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private PlayerRepository playerRepository;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        playerRepository.deleteAll();
        accountRepository.deleteAll();
    }

    @Test
     void testGivenPlayerObject_whenCreatePlayer_thenReturnSavedPlayer() throws Exception{

        ResultActions createResponse = createPlayer(100001L,200001L,new BigDecimal(0.00));
        createResponse.andDo(print()).
                andExpect(status().isCreated())
                .andExpect(jsonPath("$.playerId", is(100001)));
    }

    @Test
     void testUpdatePlayerObject_whenUpdateStaticField_GetBadRequest() throws Exception{
        createPlayer(100001L,200001L,new BigDecimal(0.00));
        updatePlayer(100001L);// update name

    }

    public ResultActions createPlayer(Long playerID,Long accounId,BigDecimal balance) throws Exception {
        Account account = Account.build(
                accounId,
                ResourceStatus.ACTIVE,
                balance,
                "LKR",
                ZonedDateTime.now()
        );
        Player player = Player.build(
                playerID,
                "Tharindu",
                "Malinda",
                ZonedDateTime.now(),
                ResourceStatus.ACTIVE,
                account
        );

        ResultActions response = mockMvc.perform(post("/player")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(player)));

        return  response;
    }

    public ResultActions getPlayer(Long playerId) throws Exception {
        ResultActions response = mockMvc.perform(MockMvcRequestBuilders
                                    .get("/player/{playerId}",playerId)
                                    .accept(MediaType.APPLICATION_JSON));

        return response;
    }

    public void updatePlayer(Long playerId) throws Exception {

        Map<Object,Object> player = new HashMap<>();
        player.put("firstName","Tharindu_updated");
        player.put("lastName","Malinda_updated");

        Map<Object,Object> player2 = new HashMap<>();
        player2.put("playerId",2000001L);

        ResultActions response = mockMvc.perform(patch("/player/{playerId}",playerId)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(player)));

        response.andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.firstName", is("Tharindu_updated")))
                .andExpect(jsonPath("$.lastName", is("Malinda_updated")));
    }
}
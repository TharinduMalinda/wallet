package com.wallet.controller;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.wallet.dto.TransactionRequestDTO;
import com.wallet.model.TransactionTypes;
import com.wallet.repository.TransactionRepository;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import com.wallet.model.Account;
import com.wallet.model.Player;
import com.wallet.model.ResourceStatus;
import com.wallet.repository.AccountRepository;
import com.wallet.repository.PlayerRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.ZonedDateTime;
import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@RunWith(SpringRunner.class)
class WalletControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private PlayerRepository playerRepository;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() throws Exception {
        transactionRepository.deleteAll();
        playerRepository.deleteAll();
        accountRepository.deleteAll();
    }

    @Test
     void testAddCreditTransaction_CheckBallanceBeforeAndAfter() throws Exception {
        BigDecimal startBalance = new BigDecimal(0.00);
        BigDecimal txnAmount = new BigDecimal(100.00);
        BigDecimal expectedBalance = startBalance.add(txnAmount);

        createPlayer(10000L,200001L,startBalance);
        doTransaction(200001L,TransactionTypes.CREDIT,txnAmount,expectedBalance);
    }

    @Test
     void testAddDebitTransaction_CheckBallanceBeforeAndAfter() throws Exception {
        BigDecimal startBalance = new BigDecimal(1000.00);
        BigDecimal txnAmount = new BigDecimal(100.00);
        BigDecimal expectedBalance = startBalance.subtract(txnAmount);

        createPlayer(10000L,200001L,startBalance);
        doTransaction(200001L,TransactionTypes.DEBIT,txnAmount,expectedBalance);
    }

    @Test
     void testAddTransaction_WhichExceedMaxTransactionLimit() throws Exception {
        BigDecimal startBalance = new BigDecimal(10000.00);
        BigDecimal txnAmount = new BigDecimal(1001.00);

        createPlayer(10000L,200001L,startBalance);

        doTransactionAndCheckIsAllowed(
                200001L,
                TransactionTypes.DEBIT,
                txnAmount,
                HttpStatus.BAD_REQUEST,
                "must be less than or equal to 1000.0");
    }

    @Test
     void testAddTransaction_WhichLowerThanMinTransactionLimit() throws Exception {
        BigDecimal startBalance = new BigDecimal(10000.00);
        BigDecimal txnAmount = new BigDecimal(0.00);

        createPlayer(10000L,200001L,startBalance);
        doTransactionAndCheckIsAllowed(
                200001L,
                TransactionTypes.DEBIT,
                txnAmount,
                HttpStatus.BAD_REQUEST,
                "must be greater than or equal to 0.1");
    }

    public void doTransaction(Long accountId,TransactionTypes transactionType,BigDecimal amount,BigDecimal expectedBalance) throws Exception {

        TransactionRequestDTO transactionRequestDTO = TransactionRequestDTO.build(
                "txn0001",accountId,transactionType,amount
        );


        ResultActions response = mockMvc.perform(post("/transaction")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(transactionRequestDTO)))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.endBalance",Matchers.comparesEqualTo(expectedBalance.doubleValue())));

    }

    public void doTransactionAndCheckIsAllowed(Long accountId,TransactionTypes transactionType,BigDecimal amount,HttpStatus expectedStatus,String expectedMsg) throws Exception {

        TransactionRequestDTO transactionRequestDTO = TransactionRequestDTO.build(
                "txn0001",accountId,transactionType,amount
        );

        ResultActions response = mockMvc.perform(post("/transaction")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(transactionRequestDTO)))
                .andDo(print())
                .andExpect(jsonPath("$.description.txnAmount",is(expectedMsg)));
    }

    protected <T> T stringToJson(String json, Class<T> classToConvert)
            throws JsonParseException, IOException {

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        objectMapper.registerModule(new JavaTimeModule());
        return objectMapper.readValue(json, classToConvert);
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

        response.andExpect(status().isCreated());
        return  response;
    }
}
package com.wallet.mapper;


import com.wallet.dto.*;
import com.wallet.model.Account;
import com.wallet.model.Player;
import com.wallet.model.Transaction;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class Mapper {

//    public Player playerMapper(PlayerDTO playerDTO){
//        Player player = Player.build(playerDTO.getPlayerId(), playerDTO.getFirstName(), playerDTO.getLastName(), ZonedDateTime.now(),playerDTO.getStatus(), playerDTO.getAccount());
//        return player;
//    }

    public Transaction txnMapper(TransactionRequestDTO txnRequest, BigDecimal newBalance, BigDecimal oldBalance){
        return Transaction.build(
                txnRequest.getTxnId(),
                txnRequest.getAccountId(),
                txnRequest.getTxnType(),
                txnRequest.getTxnAmount(),
                oldBalance,
                newBalance,
                ZonedDateTime.now()
        );
    }

    public ResponseEntity<AccountDTO> acountDtoMapper(Account account){
        AccountDTO accountDTO = AccountDTO.build(
                account.getAccountId(),
                account.getAccountStatus(),
                account.getBalance(),
                account.getCurrency()
        );
        return new ResponseEntity<>(accountDTO, HttpStatus.OK);
    }

    public ResponseEntity<List<TransactionDTO>> transactionDtoListMapper(List<Transaction> txnList){

        List<TransactionDTO> txnDtoList = txnList.stream()
                .map(txn -> TransactionDTO.build(txn.getTxnId(), txn.getAccountId(),txn.getTxnType(),txn.getTxnAmount(),txn.getStartBalance(),txn.getEndBalance(),txn.getTxnDateTime()))
                .collect(Collectors.toList());

        return new ResponseEntity<>(txnDtoList, HttpStatus.OK);
    }

    public ResponseEntity<TransactionDTO> transactionDtoMapper(Transaction transaction){
        TransactionDTO transactionDTO = TransactionDTO.build(
                transaction.getTxnId(),
                transaction.getAccountId(),
                transaction.getTxnType(),
                transaction.getTxnAmount(),
                transaction.getStartBalance(),
                transaction.getEndBalance(),
                transaction.getTxnDateTime()
        );

        return new ResponseEntity<>(transactionDTO, HttpStatus.CREATED);
    }


    public ResponseEntity<PlayerDTO> playerDtoMapper(Player player,HttpStatus expectedStatus){
        PlayerDTO playerDTO = PlayerDTO.build(
                player.getPlayerId(),
                player.getFirstName(),
                player.getLastName(),
                player.getCreatedDate(),
                player.getPlayerStatus(),
                player.getAccount().getAccountId()
        );

        return new ResponseEntity<>(playerDTO, expectedStatus);
    }



}

package com.wallet.controller;


import com.wallet.dto.AccountDTO;
import com.wallet.dto.ResponseDTO;
import com.wallet.dto.TransactionDTO;
import com.wallet.dto.TransactionRequestDTO;
import com.wallet.mapper.Mapper;
import com.wallet.model.Transaction;
import com.wallet.repository.AccountRepository;
import com.wallet.repository.TransactionRepository;
import com.wallet.service.AccountService;
import com.wallet.service.TransactionService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/transaction")
@Api(tags = "Wallet APIs")
public class WalletController {

    @Autowired
    AccountService accountService;

    @Autowired
    TransactionService transactionService;

    @Autowired
    Mapper mapper;

    //ping endpoint for check
    @GetMapping("/hello")
    public String ping(){
        return "hello from server";
    }

    @GetMapping("/{accountId}/{pageNumber}/{pageSize}")
    public ResponseEntity<List<TransactionDTO>> getTransactionList(@PathVariable Long accountId, @PathVariable int pageNumber, @PathVariable int pageSize){
        return mapper.transactionDtoListMapper(transactionService.getTransactionList(accountId,pageNumber,pageSize));
    }

    @GetMapping("/balance/{accountId}")
    public ResponseEntity<AccountDTO> getAccountDetails(@PathVariable Long accountId){
        return mapper.acountDtoMapper(accountService.findAccountByAccId(accountId));
    }

    @PostMapping()
    public ResponseEntity<TransactionDTO>  doTransaction(@RequestBody @Valid TransactionRequestDTO transactionRequestDTO){
        Transaction transaction = transactionService.createTransaction(transactionRequestDTO);
        return mapper.transactionDtoMapper(transaction);

    }


}

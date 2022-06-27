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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/transaction")

public class WalletController {
    @Autowired
    AccountService accountService;
    @Autowired
    TransactionRepository transactionRepository;

    @Autowired
    TransactionService transactionService;

    @Autowired
    Mapper mapper;

    @GetMapping("/hello")
    public String test(){
        return "hello";
    }

    @GetMapping("/{accountId}/{pageNumber}/{pageSize}")
    public ResponseEntity<List<TransactionDTO>> getTransactionList(@PathVariable Long accountId, @PathVariable int pageNumber, @PathVariable int pageSize){
        return mapper.transactionDtoListMapper(transactionService.getTransactionList(accountId,pageNumber,pageSize));
    }

    @GetMapping("/balance/{accountId}")
    public ResponseEntity<AccountDTO> getAccountDetails(@PathVariable Long accountId){
        return mapper.acountDtoMapper(accountService.findAccountByAccId(accountId));
    }

    @GetMapping("/deleteall")
    public ResponseEntity<String> deleet(){
        transactionRepository.deleteAll();
        return new ResponseEntity("deleeted",HttpStatus.OK);
    }

    @PostMapping()
    public ResponseEntity<TransactionDTO>  doTransaction(@RequestBody @Valid TransactionRequestDTO transactionRequestDTO){
        Transaction transaction = transactionService.createTransaction(transactionRequestDTO);

        return mapper.transactionDtoMapper(transaction);
        //return transactionRequest;
    }


}

package com.wallet.service.Impl;


import com.wallet.exception.InsufficientBalanceException;
import com.wallet.exception.ResourceNotFoundException;
import com.wallet.model.Account;
import com.wallet.repository.AccountRepository;
import com.wallet.repository.TransactionRepository;
import com.wallet.service.AccountService;
import com.wallet.service.PlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.math.BigDecimal;

@Service
public class AccountServiceImpl implements AccountService {

    @Autowired
    PlayerService playerService;

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    TransactionRepository transactionRepository;

    @Override
    public Account findAccountByPlayerId(Long playerId){
        return playerService.findPlayer(playerId).getAccount();

    }

    @Override
    public Account findAccountByAccId(Long accountId){
        return accountRepository.findByAccountId(accountId)
                .orElseThrow(()-> new ResourceNotFoundException("No such account in the system"));
    }

    //check balance for perform debit transactions
    public boolean checkBalance(BigDecimal accountBalance, BigDecimal txnAmount){
        if( accountBalance.compareTo(txnAmount)>0){
            return true;
        }else{
            throw new InsufficientBalanceException("Your account balance is not sufficient to perform the transaction");
        }
    }


}

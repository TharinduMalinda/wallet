package com.wallet.service.impl;

import com.wallet.dto.TransactionRequestDTO;
import com.wallet.exception.ResourceAlreadyExistException;
import com.wallet.exception.ResourceNotFoundException;
import com.wallet.mapper.Mapper;
import com.wallet.model.Account;
import com.wallet.model.ResourceStatus;
import com.wallet.model.Transaction;
import com.wallet.model.TransactionTypes;
import com.wallet.repository.AccountRepository;
import com.wallet.repository.TransactionRepository;
import com.wallet.service.AccountService;
import com.wallet.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;
import java.util.List;

/**
 * transaction related services and logics.
 *
 * @author Malinda
 *
 */

@Service
public class TransactionServiceImpl implements TransactionService {
    @Autowired
    TransactionRepository transactionRepository;

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    AccountService accountService;

    @Autowired
    Mapper mapper;

    @Override
    public List<Transaction> getTransactionList(Long accountId, int pageNo, int pageSize) {

        // create paging object based on pageNo, pageSize and sort by transaction date
        Pageable paging = PageRequest.of(pageNo, pageSize, Sort.by("txnDateTime").descending());
        accountService.findAccountByAccId(accountId);
        return transactionRepository.findAllByAccountId(accountId, paging).getContent();

    }

    @Override
    @Transactional
    public Transaction createTransaction(TransactionRequestDTO transactionRequestDTO) {
        if(transactionRepository.existsById(transactionRequestDTO.getTxnId())){
            throw  new ResourceAlreadyExistException("Duplicate transaction ID");
        }
        Account account = accountService.findAccountByAccId(transactionRequestDTO.getAccountId());
        //only accept active player's transactions
        if(!account.getAccountStatus().equals(ResourceStatus.ACTIVE)){
            throw new ResourceNotFoundException("Transactione denied. Account is already "+account.getAccountStatus());
        }

        BigDecimal oldBalance = account.getBalance();

        //debit transactions
        if(transactionRequestDTO.getTxnType().equals(TransactionTypes.DEBIT)){
            if(accountService.checkBalance(account.getBalance(), transactionRequestDTO.getTxnAmount())){
                account.setBalance (account.getBalance().subtract(transactionRequestDTO.getTxnAmount()));
            }
        }

        //credit transactions
        else{
            account.setBalance (account.getBalance().add(transactionRequestDTO.getTxnAmount()));
        }
        accountRepository.save(account);
        return transactionRepository.save(mapper.txnMapper(transactionRequestDTO,account.getBalance(),oldBalance));

    }
}

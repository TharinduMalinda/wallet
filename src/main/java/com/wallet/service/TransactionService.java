package com.wallet.service;

import com.wallet.dto.TransactionRequestDTO;
import com.wallet.model.Transaction;
import java.util.List;

public interface TransactionService {

    List<Transaction> getTransactionList(Long accountId,int pageNo,int pageSize);

    Transaction createTransaction(TransactionRequestDTO transactionRequestDTO);
}

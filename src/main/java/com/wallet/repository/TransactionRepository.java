package com.wallet.repository;

import com.wallet.model.Transaction;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface TransactionRepository extends CrudRepository<Transaction,String> {

    Page<Transaction> findAllByAccountId(Long accountId,Pageable pageable);
}

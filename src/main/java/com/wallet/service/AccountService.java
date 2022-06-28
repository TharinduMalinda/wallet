package com.wallet.service;

import com.wallet.model.Account;
import java.math.BigDecimal;

public interface AccountService {

     Account findAccountByPlayerId(Long playerId);

     Account findAccountByAccId(Long accountId);

     boolean checkBalance(BigDecimal accountBalance, BigDecimal txnAmount);

}

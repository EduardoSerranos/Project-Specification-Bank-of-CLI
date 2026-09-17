package com.bankofcli.Service;

import java.math.BigDecimal;
import java.util.List;

import com.bankofcli.Model.Account;
import com.bankofcli.Model.Transaction;

public interface AccountService {

    void createAccount(Account account);

    Account login(int accountId, String pin);

    BigDecimal checkBalance(int accountId);

    void deposit(int accountId, BigDecimal amount);

    void withdraw(int accountId, BigDecimal amount);

    void transfer(int senderId, int receiverId, BigDecimal amount);

    List<Transaction> getTransactionHistory(int accountId);
    
}

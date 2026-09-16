package com.bankofcli.Persistence;

import java.util.List;

import com.bankofcli.Model.Transaction;

public interface TransactionDAO {
    
    public void createTransaction(Transaction transaction);

    List<Transaction> getTransactionByAccountId(int accountId);
}

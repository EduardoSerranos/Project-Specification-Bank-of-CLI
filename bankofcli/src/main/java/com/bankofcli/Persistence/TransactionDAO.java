package com.bankofcli.Persistence;

public interface TransactionDAO {
    
    public void deposit(int accountId, double amount);

    public void withdraw(int accountId, double amount);

    public void transfer(int fromAccountId, int toAccountId, double amount);

    public void checkBalance(int accountId);
}

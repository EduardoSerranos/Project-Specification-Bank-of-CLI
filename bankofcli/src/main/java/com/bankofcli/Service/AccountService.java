package com.bankofcli.Service;

import com.bankofcli.Model.Account;

public interface AccountService {

    void createAccount(Account account);

    Account login(int accountId, String pin);

    double checkBalance(int accountId);

    void deposit(int accountId, double amount);

    void withdraw(int accountId, double account);

    void transfer(int senderId, int receiverId, double amount);
}

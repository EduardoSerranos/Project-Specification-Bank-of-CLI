package com.bankofcli.Persistence;

import com.bankofcli.Model.Account;

public interface AccountDAO {

    public void createAccount(Account account);

    public Account getAccountById(int accountId);

    public void updateAccount(Account account);

    public void deleteAccount(Account account);

    public void transfer(Account sender, Account receiver);

}

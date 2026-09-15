package com.bankofcli.Persistence;

import com.bankofcli.Model.Account;

public interface AccountDAO {

    public void createAccount(Account account);

    public Account getAccountById(int account_id);

    public void updateAccount(Account account);

    public void deleteAccount(Account account);

}

package com.bankofcli.Service;

import com.bankofcli.Model.Account;
import com.bankofcli.Model.Transaction;
import com.bankofcli.Persistence.AccountDAO;
import com.bankofcli.Persistence.TransactionDAO;

public class AccountServiceImpl implements AccountService{

    private AccountDAO accountDAO;

    private TransactionDAO transactionDAO;

    public AccountServiceImpl(AccountDAO accountDAO, TransactionDAO transactionDAO){

        this.accountDAO = accountDAO;
        this.transactionDAO = transactionDAO;
    }

    @Override
    public void createAccount(Account account){
        if (account == null || accountDAO.getAccountById(account.getAccountId()) != null){
            throw new IllegalArgumentException("Invalid account");
        }

        accountDAO.createAccount(account);
    }

    @Override
    public Account login(int accountId, String pin){
        Account account = accountDAO.getAccountById(accountId);

        if(account == null || !account.getPin().equals(pin)){
            throw new IllegalArgumentException("Invalid credentials");
        }
        return account;
    }
    
    @Override
    public double checkBalance(int accountId){
        Account account = accountDAO.getAccountById(accountId);

        if (account == null){
            throw new IllegalArgumentException("Account not found");
        }

        return account.getBalance();
    }

    @Override
    public void deposit(int accountId, double amount){
        Account account = accountDAO.getAccountById(accountId);
        if (amount <= 0 || account == null){
            throw new IllegalArgumentException("Must have an account and/or amount must be greater than 0");
        }

        account.setBalance(account.getBalance() + amount);
        accountDAO.updateAccount(account);
        Transaction transaction = new Transaction(accountId, "DEPOSIT", amount, null);
        transactionDAO.createTransaction(transaction);
    }

    @Override
    public void withdraw(int accountId, double Damount){
        Account account = accountDAO.getAccountById(accountId);

        if (account == null || Damount <=0){
            throw new IllegalArgumentException("Must have an account and/or amount must be greater than 0");
        }
        
        if (account.getBalance() < Damount){
            throw new IllegalArgumentException("Insufficient funds");
        }



    }

    @Override
    public void transfer(int senderId, int receiverId, double amount){

    }





}   

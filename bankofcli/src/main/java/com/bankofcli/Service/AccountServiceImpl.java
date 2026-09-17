package com.bankofcli.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.logging.Logger;

import com.bankofcli.Model.Account;
import com.bankofcli.Model.Transaction;
import com.bankofcli.Persistence.AccountDAO;
import com.bankofcli.Persistence.TransactionDAO;

public class AccountServiceImpl implements AccountService{

    private AccountDAO accountDAO;
    private TransactionDAO transactionDAO;
    private static final Logger logger = Logger.getLogger(AccountServiceImpl.class.getName());

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
    public BigDecimal checkBalance(int accountId){
        Account account = accountDAO.getAccountById(accountId);

        if (account == null){
            throw new IllegalArgumentException("Account not found");
        }

        return account.getBalance();
    }

    @Override
    public void deposit(int accountId, BigDecimal amount){
        Account account = accountDAO.getAccountById(accountId);
        if (amount.compareTo(BigDecimal.ZERO) <= 0 || account == null){
            throw new IllegalArgumentException("Must have an account and/or amount must be greater than 0");
        }

        account.setBalance(account.getBalance().add(amount));
        accountDAO.updateAccount(account);
        Transaction transaction = new Transaction(accountId, "DEPOSIT", amount, null);
        transactionDAO.createTransaction(transaction);
    }

    @Override
    public void withdraw(int accountId, BigDecimal amount){
        Account account = accountDAO.getAccountById(accountId);

        if (account == null || amount.compareTo(BigDecimal.ZERO) <= 0){
            throw new IllegalArgumentException("Must have an account and/or amount must be greater than 0");
        }
        
        if (account.getBalance().compareTo(amount) <= 0){
            throw new IllegalArgumentException("Insufficient funds");
        }
        
        account.setBalance(account.getBalance().subtract(amount));
        accountDAO.updateAccount(account);
        Transaction transaction = new Transaction(accountId, "WITHDRAW", amount, null);
        transactionDAO.createTransaction(transaction);

    }

    @Override
    public void transfer(int senderId, int receiverId, BigDecimal amount){
        Account sender = accountDAO.getAccountById(senderId);
        Account receiver = accountDAO.getAccountById(receiverId);

        if (sender == null || receiver == null || amount.compareTo(BigDecimal.ZERO) <= 0){
            throw new IllegalArgumentException("Unable to transfer");
        }

        if (amount.compareTo(sender.getBalance()) > 0 ){
            throw new IllegalArgumentException("Insufficient funds");
        }

        if(senderId == receiverId){
            throw new IllegalArgumentException("Cannot transfer to the same account");
        }

        sender.setBalance(sender.getBalance().subtract(amount));
        receiver.setBalance(receiver.getBalance().add(amount));

        accountDAO.transfer(sender, receiver);

        Transaction transaction = new Transaction(senderId, "TRANSFER", amount, receiverId);
        Transaction transaction2 = new Transaction(receiverId, "TRANSFER", amount, senderId);

        transactionDAO.createTransaction(transaction);
        transactionDAO.createTransaction(transaction2);
    }

    @Override
    public List<Transaction> getTransactionHistory(int accountId){
        Account account = accountDAO.getAccountById(accountId);

        if(account == null){
            throw new IllegalArgumentException("Account not found");
        }
        return transactionDAO.getTransactionByAccountId(accountId);
    }

}   

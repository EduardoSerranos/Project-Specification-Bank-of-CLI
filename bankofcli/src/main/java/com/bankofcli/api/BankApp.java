package com.bankofcli.api;

import com.bankofcli.Persistence.TransactionDAO;
import com.bankofcli.Persistence.TransactionDAOImpl;
import com.bankofcli.Service.AccountService;
import com.bankofcli.Persistence.AccountDAO;
import com.bankofcli.Persistence.AccountDAOImpl;
import com.bankofcli.Service.AccountServiceImpl;

public class BankApp {
    public static void main(String[] args) {
        
        AccountDAO accountDAO = new AccountDAOImpl();
        TransactionDAO transactionDAO = new TransactionDAOImpl();

        AccountService service = new AccountServiceImpl(accountDAO, transactionDAO);

        new BankRepl(service).run();

        
    }
}
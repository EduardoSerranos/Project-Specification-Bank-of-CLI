package com.bankofcli.Service;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.bankofcli.Model.Account;
import com.bankofcli.Persistence.AccountDAO;
import com.bankofcli.Persistence.TransactionDAO;

public class AccountServiceImplTest {
    private AccountDAO accountDAO;
    private TransactionDAO transactionDAO;
    private AccountServiceImpl service;

    @BeforeEach
    void setUp(){
        accountDAO = mock(AccountDAO.class);
        transactionDAO = mock(TransactionDAO.class);

        service = new AccountServiceImpl(accountDAO, transactionDAO);   
    }

    @Test
    void loginSuccessful(){
        Account account = new Account(1, "1234");

        when(accountDAO.getAccountById(1)).thenReturn(account);

        Account result = service.login(1, "1234");

        assertNotNull(result);
        assertEquals(1, result.getAccountId());
    }

    @Test
    void loginWrongPin(){
        Account account = new Account(1, "1234");

        when(accountDAO.getAccountById(1)).thenReturn(account);

        assertThrows(IllegalArgumentException.class,() -> service.login(1, "9999"));
    }

    @Test
    void depositSuccessful(){
        Account account = new Account(1, new BigDecimal("100.00"), "1234");

        when(accountDAO.getAccountById(1)).thenReturn(account);

        service.deposit(1, new BigDecimal("50.00"));

        assertEquals(new BigDecimal("150.00"), account.getBalance());

        verify(accountDAO).updateAccount(account);
        verify(transactionDAO).createTransaction(any());
    }

    @Test
    void withdrawInsufficientFunds(){
        Account account = new Account(1, new BigDecimal("50.00"), "1234");

        when(accountDAO.getAccountById(1)).thenReturn(account);

        assertThrows(IllegalArgumentException.class,() -> service.withdraw(1, new BigDecimal("100.00")));

        assertEquals(new BigDecimal("50.00"), account.getBalance());

        verify(accountDAO, never()).updateAccount(account);
    }

    @Test
    void transferSuccessful(){
        Account sender = new Account(1, new BigDecimal("100.00"), "1234");
        Account receiver = new Account(2, new BigDecimal("25.00"), "5678");

        when(accountDAO.getAccountById(1)).thenReturn(sender);
        when(accountDAO.getAccountById(2)).thenReturn(receiver);

        service.transfer(1, 2, new BigDecimal("25.00"));

        assertEquals(new BigDecimal("75.00"), sender.getBalance());
        assertEquals(new BigDecimal("50.00"), receiver.getBalance());

        verify(accountDAO).transfer(sender, receiver);
        verify(transactionDAO, times(2)).createTransaction(any());
        
    }
}

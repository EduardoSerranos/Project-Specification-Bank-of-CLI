package com.bankofcli.Persistence;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

import com.bankofcli.Service.AccountServiceImpl;

public class TransactionDAOImplTest {
    private AccountDAO accountDAO;
    private TransactionDAO transactionDAO;
    private AccountServiceImpl accountServiceImpl;

    @BeforeEach
    void setUp(){
        accountDAO = mock(AccountDAO.class);
        transactionDAO = mock(TransactionDAO.class);

        ser

    }
}

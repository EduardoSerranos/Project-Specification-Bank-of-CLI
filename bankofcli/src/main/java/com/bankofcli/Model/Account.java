package com.bankofcli.Model;

import java.math.BigDecimal;

public class Account {

    private int accountId;
    private BigDecimal balance;
    private String pin;

    public Account(){

    }

    public Account(int accountId, String pin) {
        this.accountId = accountId;
        this.pin = pin;
        //Otherwise balance starts as null
        this.balance = BigDecimal.ZERO;
    }

    public Account(int accountId, BigDecimal balance, String pin) {
        this.accountId = accountId;
        this.balance = balance;
        this.pin = pin;
    }

    public int getAccountId() {
        return accountId;
    }

    public void setAccountId(int accountId) {
        this.accountId = accountId;
    }

    public String getPin() {
        return pin;
    }

    public void setPin(String pin) {
        this.pin = pin;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    @Override
    public String toString() {
        return "Account [accountId= " + accountId + ", balance= " + balance + " ]";
    }
}

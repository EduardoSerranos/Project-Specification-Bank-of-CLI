package com.bankofcli.Model;

public class Account {

    public int accountId;

    public double balance;

    public String pin;

    public Account(){

    }

    public Account(int accountId, String pin) {
        this.accountId = accountId;
        this.pin = pin;
    }

    public Account(int accountId, double balance, String pin) {
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

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    @Override
    public String toString() {
        return "Account [accountId=" + accountId + ", balance=" + balance + ", pin=" + pin + "]";
    }

    

}

package com.bankofcli.Model;

public class Account {

    public int account_id;

    public int balance;

    public String pin;

    public Account(){

    }

    public Account(int account_id, String pin) {
        this.account_id = account_id;
        this.pin = pin;
    }

    public Account(int account_id, int balance, String pin) {
        this.account_id = account_id;
        this.balance = balance;
        this.pin = pin;
    }

    public int getAccount_id() {
        return account_id;
    }

    public void setAccount_id(int account_id) {
        this.account_id = account_id;
    }

    public String getPin() {
        return pin;
    }

    public void setPin(String pin) {
        this.pin = pin;
    }

    public int getBalance() {
        return balance;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }

    @Override
    public String toString() {
        return "Account [account_id=" + account_id + ", balance=" + balance + ", pin=" + pin + "]";
    }

    

}

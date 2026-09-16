package com.bankofcli.Model;

import java.time.LocalDateTime;

public class Transaction {
    private int transactionId;
    private int accountId;
    private String transactionType;
    private double amount;
    private Integer targetAccountId;
    private LocalDateTime timestamp;

    public Transaction(){

    }

    public Transaction(int accountId, String transactionType, double amount, int targetAccountId) {
        this.accountId = accountId;
        this.transactionType = transactionType;
        this.amount = amount;
        this.targetAccountId = targetAccountId;
    }

    public int getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(int transactionId) {
        this.transactionId = transactionId;
    }

    public int getAccountId() {
        return accountId;
    }

    public void setAccountId(int accountId) {
        this.accountId = accountId;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(String transactionType){
        this.transactionType = transactionType;
    }

    public Integer getTargetAccountId() {
        return targetAccountId;
    }

    public void setTargetAccountId(int targetAccountId){
        this.targetAccountId = targetAccountId;
    }

    public LocalDateTime getTimestamp(){
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp){
        this.timestamp = timestamp;
    }

}

package com.bankofcli.Model;

public class Transaction {
    private int transactionId;
    private int accountId;
    private double amount;
    private String transactionType;
    private int targetAccountId;

    public Transaction(){

    }

    public Transaction(int transactionId, int accountId, double amount, String transactionType, int targetAccountId) {
        this.transactionId = transactionId;
        this.accountId = accountId;
        this.amount = amount;
        this.transactionType = transactionType;
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

    public void getTransactionType(String transactionType){
        this.transactionType = transactionType;
    }

    public int getTargetAccountId() {
        return targetAccountId;
    }

    public void setTargetAccountId(int targetAccountId){
        this.targetAccountId = targetAccountId;
    }

}

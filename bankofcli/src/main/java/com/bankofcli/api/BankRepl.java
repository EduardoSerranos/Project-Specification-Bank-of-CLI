package com.bankofcli.api;

import java.math.BigDecimal;
import java.util.List;
import java.util.Scanner;

import com.bankofcli.Model.Account;
import com.bankofcli.Model.Transaction;
import com.bankofcli.Service.AccountService;

public class BankRepl {
    
    private final AccountService service;
    private final Scanner scanner = new Scanner(System.in);
    private Account currentAccount;

    public BankRepl(AccountService service){
        this.service = service;
    }

    public void run(){
        printHelp();

        while(scanner.hasNextLine()){
            System.out.println(">");
            String command = scanner.nextLine().trim();

            if (command.equals("exit")) {
                return;
            }
            try {
                handle(command);
            } catch (IllegalArgumentException e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
        
    }

    public void handle(String command){
        switch(command){
            case "1" -> register();
            case "2" -> login();
            case "3" -> printHelp();
            case "4" -> logout();
            default -> System.out.println("Unknown command");
        }
    }

    public void register(){
        System.out.println("Account ID: ");
        int accountId = Integer.parseInt(scanner.nextLine().trim());

        System.out.println("PIN: ");
        String pin = scanner.nextLine().trim();

        Account account = new Account(accountId, pin);

        service.createAccount(account);

        printSuccess("Account created successfully.");

        printHelp();
    }

    public void login(){
        System.out.println("Account ID: ");
        int accountId = Integer.parseInt(scanner.nextLine().trim());

        System.out.println("PIN: ");
        String pin = scanner.nextLine().trim();

        currentAccount = service.login(accountId, pin);

        printSuccess("Login successful.");

        loggedInMenu();
    }

    public void loggedInMenu(){
        printLoggedInHelp();
        
        while(currentAccount != null){

            System.out.println(">");
            String command = scanner.nextLine().trim();

            try {
                switch(command){
                    case "1" -> checkBalance();
                    case "2" -> deposit();
                    case "3" -> withdraw();
                    case "4" -> transfer();
                    case "5" -> history();
                    case "6" -> logout();
                    default -> System.out.println("Unknown command");
                }
                
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
            
        }
    }

    public void checkBalance(){
        BigDecimal balance = service.checkBalance(currentAccount.getAccountId());
        System.out.println("----------------------------------------");
        System.out.println("Current Balance: $" + balance);
        System.out.println("----------------------------------------");
    }
    
    public void deposit() { 
        System.out.println(); 
        System.out.println(" ┌────────────────────────────────────────┐"); 
        System.out.println(" │ DEPOSIT MONEY │"); 
        System.out.println(" └────────────────────────────────────────┘"); 
        System.out.print(" Amount to deposit: $"); 
        BigDecimal amount = new BigDecimal(scanner.nextLine().trim()); 
        service.deposit(currentAccount.getAccountId(), amount); 
        System.out.println(); 
        printSuccess("Deposit successful.");
        loggedInMenu(); 
    }

    public void withdraw() { 
        System.out.println(); 
        System.out.println(" ┌────────────────────────────────────────┐"); 
        System.out.println(" │ WITHDRAW MONEY │"); 
        System.out.println(" └────────────────────────────────────────┘"); 
        System.out.print(" Amount to withdraw: $"); 
        BigDecimal amount = new BigDecimal(scanner.nextLine().trim()); 
        service.withdraw(currentAccount.getAccountId(), amount); 
        System.out.println(); 
        printSuccess("Withdrawal successful.");
        loggedInMenu(); 
    }

    public void transfer() { 
        System.out.println(); 
        System.out.println(" ┌────────────────────────────────────────┐"); 
        System.out.println(" │ TRANSFER MONEY │"); 
        System.out.println(" └────────────────────────────────────────┘"); 
        System.out.print(" Target Account ID: "); 
        int receiverId = Integer.parseInt(scanner.nextLine().trim()); 
        System.out.print(" Amount to transfer: $"); 
        BigDecimal amount = new BigDecimal(scanner.nextLine().trim()); 
        service.transfer( currentAccount.getAccountId(), receiverId, amount ); 
        System.out.println(); 
        printSuccess("Transfer successful.");
        loggedInMenu();
    }

    public void history() { 
        List<Transaction> transactions = service.getTransactionHistory( currentAccount.getAccountId() ); 
        System.out.println(); 
        System.out.println(" ┌──────────────────────────────────────────────────────┐"); 
        System.out.println(" │ TRANSACTION HISTORY │"); 
        System.out.println(" └──────────────────────────────────────────────────────┘"); 
        if (transactions.isEmpty()) { 
            System.out.println(); 
            System.out.println(" No transactions found."); 
            return; 
        } 
        System.out.println();
        System.out.println(" DATE/TIME TYPE AMOUNT"); 
        System.out.println(" ------------------------------------------------------"); 
        for (Transaction transaction : transactions) { 
            System.out.printf( " %-24s %-16s $%s%n", transaction.getTimestamp(), transaction.getTransactionType(), transaction.getAmount() ); 
        } 
        System.out.println( " ------------------------------------------------------" );
        loggedInMenu(); 
    }

    public void logout() { 
        currentAccount = null; 
        System.out.println(); 
        printSuccess("Logged out successfully."); 
        System.out.println(); 
        printHelp();    
    }

    public void printHelp() {
        printHeader();
        System.out.println();
        System.out.println("Available Commands");
        System.out.println("----------------------------------------");
        System.out.println(" 1. register  - Create a new account");
        System.out.println(" 2. login     - Login to your account");
        System.out.println(" 3. help      - Show available commands");
        System.out.println(" 4. exit      - Exit the application");
        System.out.println("----------------------------------------");
    }

    public void printLoggedInHelp() {
        printHeader();

        System.out.println("Account: " + currentAccount.getAccountId());
        System.out.println();
        System.out.println("Account Menu");
        System.out.println("----------------------------------------");
        System.out.println(" 1. balance   - Check account balance");
        System.out.println(" 2. deposit   - Deposit money");
        System.out.println(" 3. withdraw  - Withdraw money");
        System.out.println(" 4. transfer  - Transfer money");
        System.out.println(" 5. history   - View transactions");
        System.out.println(" 6. logout    - Logout");
        System.out.println("----------------------------------------");
    }

    public void printHeader() {
        System.out.println("========================================");
        System.out.println("           BANK OF CLI");
        System.out.println("        Terminal Banking");
        System.out.println("========================================");
    }

    public void printSuccess(String message) { 
        System.out.println(" + " + message); 
    }
}

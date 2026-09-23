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

        while(true){
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
            case "register" -> register();
            case "login" -> login();
            case "help" -> printHelp();
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

        System.out.println("Account created successfully.");
    }

    public void login(){
        System.out.println("Account ID: ");
        int accountId = Integer.parseInt(scanner.nextLine().trim());

        System.out.println("PIN: ");
        String pin = scanner.nextLine().trim();

        currentAccount = service.login(accountId, pin);

        System.out.println("Login successful.");

        loggedInMenu();

    }

    public void loggedInMenu(){
        printLoggedInHelp();
        
        while(currentAccount != null){
            System.out.println(">");
            String command = scanner.nextLine().trim();

            switch(command){
                case "balance" -> checkBalance();
                case "deposit" -> deposit();
                case "withdraw" -> withdraw();
                case "transfer" -> transfer();
                case "history" -> history();
                case "logout" -> logout();
                default -> System.out.println("Unknown command");
            }
        }
    }

    public void checkBalance(){
        BigDecimal balance = service.checkBalance(currentAccount.getAccountId());
        System.out.println("Current balance: $" + balance);
    }
    
    public void deposit(){
        System.out.println("Amount to deposit: ");
        BigDecimal amount = new BigDecimal(scanner.nextLine().trim());
        service.deposit(currentAccount.getAccountId(), amount);
        System.out.println("Deposit successful.");
    }

    public void withdraw(){
        System.out.println("Amount to withdraw: ");
        BigDecimal amount = new BigDecimal(scanner.nextLine().trim());
        service.withdraw(currentAccount.getAccountId(), amount);
        System.out.println("Withdrawal successful.");
    }

    public void transfer(){
        System.out.println("Target Account ID: ");
        int receiverId = Integer.parseInt(scanner.nextLine().trim());

        System.out.println("Amount to transfer: ");
        BigDecimal amount = new BigDecimal(scanner.nextLine().trim());

        service.transfer(currentAccount.getAccountId(), receiverId, amount);
        System.out.println("Transfer successful.");
    }

    public void history(){
        List<Transaction> transactions = service.getTransactionHistory(currentAccount.getAccountId());
        if (transactions.isEmpty()){
            System.out.println("No transaction found.");
            //Has to be return because is normal to not have a transaction.
            return;
        }
        for(Transaction transaction : transactions){
            System.out.println(transaction.getTimestamp() + " | " + transaction.getTransactionType() + " | $" + transaction.getAmount());
        }
    }

    public void logout(){
        currentAccount = null;
        System.out.println("Logged out successfully.");
    }

    public void printHelp(){
        System.out.println("Available commands: ");
        System.out.println("register - Register an account");
        System.out.println("login - Login to an account");
        System.out.println("help - Show this message again");
        System.out.println("exit - Exit the application");

    }

    public void printLoggedInHelp(){
        System.out.println("Available commands: ");
        System.out.println("balance - Check your balance");
        System.out.println("deposit - Deposit money");
        System.out.println("withdraw - Withdraw money");
        System.out.println("transfer - Transfer money");
        System.out.println("history - View transaction history");
        System.out.println("logout - Logout");
    }
}

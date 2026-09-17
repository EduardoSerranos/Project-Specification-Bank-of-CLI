package com.bankofcli.api;

import java.util.Scanner;

import com.bankofcli.Model.Account;
import com.bankofcli.Service.AccountService;

public class BankRepl {
    
    private final AccountService service;
    private final Scanner scanner = new Scanner(System.in);

    public BankRepl(AccountService service){
        this.service = service;
    }

    public void run(){
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

        Account account = service.login(accountId, pin);

        System.out.println("Login successfully.");

    }

    public void printHelp(){
        System.out.println("Available commanda: ");
        System.out.println("register - Register an account");
        System.out.println("login - Login to an account");
        System.out.println("help - Show this message again");
        System.out.println("exit - Exit the application");

    }
}

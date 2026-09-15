package com.bankofcli.Persistence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import com.bankofcli.Model.Account;

public class AccountDAOImpl implements AccountDAO {

    
    private static final String CREATE_TABLE_SQL = """
            CREATE TABLE IF NOT EXISTS accounts (
                account_id INT PRIMARY KEY,
                username VARCHAR(255) NOT NULL,
                password VARCHAR(255) NOT NULL,
                balance DECIMAL(10, 2) NOT NULL
            """;

    private static final String INSERT_SQL = "INSERT INTO accounts (account_id, pin, balance) VALUES (?, ?, ?)";
    private static final String FIND_BY_ID_SQL = "SELECT * FROM accounts WHERE account_id = ?";
    private static final String UPDATE_SQL = "UPDATE accounts SET pin = ?, balance = ? WHERE account_id = ?";
    private static final String DELETE_SQL = "DELETE FROM accounts WHERE account_id = ?";


    
    public AccountDAOImpl(){
        InitializeSchema();
    }


    @Override
    public void createAccount(Account account) {
        // Implementation for creating an account
    }

    @Override
    public Account getAccountById(int account_id) {
        // Implementation for retrieving an account by ID
        return null;
    }

    @Override
    public void updateAccount(Account account) {
        // Implementation for updating an account
    }

    @Override
    public void deleteAccount(Account account) {
        // Implementation for deleting an account
    }

    public void InitializeSchema() {
        try(Connection connection = ConnectionFactory.getConnectionFactory().getConnection();
                PreparedStatement statement = connection.prepareStatement(CREATE_TABLE_SQL) ){
            statement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Error initializing database schema", e);
        }
    }

}

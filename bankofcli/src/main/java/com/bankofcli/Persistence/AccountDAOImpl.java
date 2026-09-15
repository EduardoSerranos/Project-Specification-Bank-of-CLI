package com.bankofcli.Persistence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.bankofcli.Model.Account;

public class AccountDAOImpl implements AccountDAO {

    
    private static final String CREATE_TABLE_SQL = """
            CREATE TABLE IF NOT EXISTS accounts (
                accountId INT PRIMARY KEY,
                pin VARCHAR(255) NOT NULL,
                balance DECIMAL(10, 2) NOT NULL);
            """;

    private static final String INSERT_SQL = "INSERT INTO accounts (accountId, pin, balance) VALUES (?, ?, ?)";
    private static final String FIND_BY_ID_SQL = "SELECT * FROM accounts WHERE accountId = ?";
    private static final String UPDATE_SQL = "UPDATE accounts SET pin = ?, balance = ? WHERE accountId = ?";
    private static final String DELETE_SQL = "DELETE FROM accounts WHERE accountId = ?";

    
    public AccountDAOImpl(){
        InitializeSchema();
    }


    @Override
    public void createAccount(Account account) {
        try(Connection connection = ConnectionFactory.getConnectionFactory().getConnection();
            PreparedStatement statement = connection.prepareStatement(INSERT_SQL)) {
            statement.setInt(1, account.getAccountId());
            statement.setString(2, account.getPin());
            statement.setDouble(3, account.getBalance());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error creating account", e);
        }
    }

    @Override
    public Account getAccountById(int accountId) {
        try(Connection connection = ConnectionFactory.getConnectionFactory().getConnection();
            PreparedStatement statement = connection.prepareStatement(FIND_BY_ID_SQL)) {

            statement.setInt(1, accountId);
            var resultSet = statement.executeQuery();
            if(resultSet.next()) {
                int id = resultSet.getInt("accountId");
                String pin = resultSet.getString("pin");
                double balance = resultSet.getDouble("balance");
                return new Account(id, balance, pin);
            } else {
                return null;
            }

        } catch(SQLException e) {
            throw new RuntimeException("Error retrieving account", e);
        }
    }

    @Override
    public void updateAccount(Account account) {
        try(Connection connection = ConnectionFactory.getConnectionFactory().getConnection();
            PreparedStatement statement = connection.prepareStatement(UPDATE_SQL)) {
            statement.setString(1, account.getPin());
            statement.setDouble(2, account.getBalance());
            statement.setInt(3, account.getAccountId());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error updating account", e);
        }
    }

    @Override
    public void deleteAccount(Account account) {
        try(Connection connection = ConnectionFactory.getConnectionFactory().getConnection();
            PreparedStatement statement = connection.prepareStatement(DELETE_SQL)) {
            statement.setInt(1, account.getAccountId());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error deleting account", e);
        }
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

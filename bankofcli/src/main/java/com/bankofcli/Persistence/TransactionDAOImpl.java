package com.bankofcli.Persistence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.bankofcli.Model.Transaction;

public class TransactionDAOImpl implements TransactionDAO {

    private static final String CREATE_TABLE_SQL = """
            CREATE TABLE IF NOT EXISTS transaction (
                transaction_id SERIAL PRIMARY KEY,
                accountId INTEGER NOT NULL REFERENCES account(AccountID),
                type VARCHAR(20) NOT NULL,
                amount NUMERIC(12, 2) NOT NULL,
                targetAccountId INTEGER REFERENCES account(accountId),
                timestamp TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP);
            """;

    private static final String CREATE_TRANSACTION_SQL = "INSERT INTO transaction (accountId, type, amount, targetAccountId) VALUES (?, ?, ?, ?)";
    private static final String FIND_TRANSACTION_SQL = "SELECT * FROM transaction WHERE transactionId = ? ORDERED BY timestamp DESC";



    public TransactionDAOImpl(){
        InitializeSchema();
    }


    @Override
    public void createTransaction(Transaction transaction){
        try(Connection connection = ConnectionFactory.getConnectionFactory().getConnection();
            PreparedStatement statement = connection.prepareStatement(CREATE_TRANSACTION_SQL)) {

            statement.setInt(1, transaction.getAccountId());
            statement.setString(2, transaction.getTransactionType());
            statement.setDouble(3, transaction.getAmount());

            if (transaction.getTargetAccountId() != null){
                statement.setInt(4, transaction.getTargetAccountId());
            } else{
                statement.setNull(4, java.sql.Types.INTEGER);
            }
            statement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Error creating a transaction: ", e);
        }
    }

    @Override
    public List<Transaction> getTransactionByAccountId(int accountId){
        List<Transaction> transactions = new ArrayList<>();
        try(Connection connection = ConnectionFactory.getConnectionFactory().getConnection();
            PreparedStatement statement = connection.prepareStatement(FIND_TRANSACTION_SQL)) {
            statement.setInt(1, accountId);

            ResultSet resultSet = statement.executeQuery();

            while(resultSet.next()){
                Transaction transaction = new Transaction();

                transaction.setTransactionId(resultSet.getInt("transactionId"));
                transaction.setAccountId(resultSet.getInt("accountId"));
                transaction.setTransactionType(resultSet.getString("type"));
                transaction.setAmount(resultSet.getDouble("amount"));
                int targetAccountId = resultSet.getInt("targetAccountId");
                if (!resultSet.wasNull()){
                    transaction.setTargetAccountId(targetAccountId);
                }
                transaction.setTimestamp(resultSet.getTimestamp("timestamp").toLocalDateTime());
                transactions.add(transaction);

            }


        } catch (SQLException e) {
            throw new RuntimeException("Error getting a transaction", e);
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

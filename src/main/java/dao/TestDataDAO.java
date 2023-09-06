package dao;

import connection.DatabaseConnectionFactory;
import payload.UserPayload;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;

public class TestDataDAO {
    private final DatabaseConnectionFactory connectionFactory;

    public TestDataDAO(DatabaseConnectionFactory connectionFactory) {
        this.connectionFactory = connectionFactory;
    }

    public void insertTestDataIntoMySQL(UserPayload userPayload , String description , int scenarioId) {
        try (Connection connection = connectionFactory.getConnection()) {
            String insertQuery = "INSERT INTO test_data (name, gender, status, email, created , description , scenario_id) VALUES (?, ?, ?, ?, ? , ? , ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(insertQuery);

            preparedStatement.setString(1, userPayload.getName());
            preparedStatement.setString(2, userPayload.getGender());
            preparedStatement.setString(3, userPayload.getStatus());
            preparedStatement.setString(4, userPayload.getEmail());
            preparedStatement.setTimestamp(5, new Timestamp(System.currentTimeMillis()));
            preparedStatement.setString(6, description);
            preparedStatement.setInt(7, scenarioId);

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}

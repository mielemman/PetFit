package utilities;

import java.sql.*;
import models.User;

public class DatabaseConnection {

    private static final String URL = "jdbc:mysql://localhost:3306/PetFit";
    private static final String USER = "root"; 
    private static final String PASSWORD = "amiel"; 

    // Establish the database connection
    public static Connection getConnection() {
        try {
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (SQLException e) {
            System.out.println("Error connecting to the database. Please try again later.");
            logError(e);
            return null;
        }
    }

    // Authenticate user by username and password
    public User authenticateUser(String username, String password) {
        String query = "SELECT * FROM Users WHERE username = ? AND password = ?";

        try (Connection connection = getConnection(); 
             PreparedStatement stmt = connection.prepareStatement(query)) {

            stmt.setString(1, username);
            stmt.setString(2, password);

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new User(
                    rs.getInt("id"),
                    rs.getString("username"),
                    rs.getString("password"),
                    rs.getInt("coins"),
                    rs.getInt("points"),
                    rs.getInt("streak"),
                    rs.getInt("workouts_completed")

                );
            } else {
                return null;
            }
        } catch (SQLException e) {
            System.out.println("Error authenticating user. Please try again later.");
            logError(e);  
            return null;
        }
    }

    // Register a new user
    public boolean registerUser(String username, String password) {
        String query = "INSERT INTO Users (username, password) VALUES (?, ?)";

        try (Connection connection = getConnection();  
             PreparedStatement stmt = connection.prepareStatement(query)) {

            stmt.setString(1, username);
            stmt.setString(2, password);

            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            String errorMessage = e.getMessage();
            if (errorMessage.contains("Duplicate entry")) {
                System.out.println("Username already exists. Please choose another username.");
            } else {
                System.out.println("Error registering user. Please try again later.");
            }
            logError(e); 
            return false;
        }
    }

    // Update user data in the database
    public static boolean updateUser(User user) {
        String query = "UPDATE Users SET coins = ?, points = ?, streak = ?, workouts_completed = ? WHERE id = ?";

        try (Connection connection = getConnection();  
             PreparedStatement stmt = connection.prepareStatement(query)) {

            stmt.setInt(1, user.getCoins());
            stmt.setInt(2, user.getPoints());
            stmt.setInt(3, user.getStreak());
            stmt.setInt(4, user.getWorkoutsCompleted());
            stmt.setInt(5, user.getId());

            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            System.out.println("Error updating user data. Please try again later.");
            logError(e); 
            return false;
        }
    }

    // Retrieve user data by ID
    public User getUserById(int id) {
        String query = "SELECT * FROM Users WHERE id = ?";

        try (Connection connection = getConnection();  
             PreparedStatement stmt = connection.prepareStatement(query)) {

            stmt.setInt(1, id);

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new User(
                    rs.getInt("id"),
                    rs.getString("username"),
                    rs.getString("password"),
                    rs.getInt("coins"),
                    rs.getInt("points"),
                    rs.getInt("streak"),
                    rs.getInt("workouts_completed")
                );
            } else {
                return null;
            }
        } catch (SQLException e) {
            System.out.println("Error retrieving user by ID. Please try again later.");
            logError(e);  
            return null;
        }
    }

    // Log SQL errors
    private static void logError(SQLException e) {
        System.err.println("SQL Error: " + e.getMessage());
    }
}

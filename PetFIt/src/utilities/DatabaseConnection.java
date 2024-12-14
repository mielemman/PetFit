package utilities;

import java.sql.*;
import models.User;

public class DatabaseConnection {

    // Database connection details
    private static final String URL = "jdbc:mysql://localhost:3306/PetFit"; // Replace with your database URL
    private static final String USER = "root"; // Your MySQL username
    private static final String PASSWORD = "amiel"; // Your MySQL password

    // Method to get a new connection to the database
    public static Connection getConnection() {
        try {
            // Always return a new connection
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (SQLException e) {
            System.out.println("Error connecting to the database. Please try again later.");
            logError(e);  // Log the error without showing stack trace to user
            return null;
        }
    }

    // Method to authenticate user
    public User authenticateUser(String username, String password) {
        String query = "SELECT * FROM Users WHERE username = ? AND password = ?";

        try (Connection connection = getConnection();  // Get a new connection for each operation
             PreparedStatement stmt = connection.prepareStatement(query)) {

            // Set the parameters for the prepared statement
            stmt.setString(1, username);
            stmt.setString(2, password);

            // Execute the query and process the result
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                // Return a User object created from the database result
                return new User(
                    rs.getInt("id"),
                    rs.getString("username"),
                    rs.getString("password"),
                    rs.getInt("coins"),
                    rs.getInt("points"),
                    rs.getInt("streak"),
                    rs.getInt("workouts_completed"),
                    rs.getInt("daily_tasks_completed")
                );
            } else {
                return null; // User not found
            }
        } catch (SQLException e) {
            System.out.println("Error authenticating user. Please try again later.");
            logError(e);  // Log the error without showing stack trace to user
            return null;
        }
    }

    // Method to register a new user
    public boolean registerUser(String username, String password) {
        String query = "INSERT INTO Users (username, password) VALUES (?, ?)";

        try (Connection connection = getConnection();  // Get a new connection for each operation
             PreparedStatement stmt = connection.prepareStatement(query)) {

            // Set the parameters for the prepared statement
            stmt.setString(1, username);
            stmt.setString(2, password);

            // Execute the update and return whether it was successful
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            String errorMessage = e.getMessage();
            if (errorMessage.contains("Duplicate entry")) {
                System.out.println("Username already exists. Please choose another username.");
            } else {
                System.out.println("Error registering user. Please try again later.");
            }
            logError(e);  // Log the error without showing stack trace to user
            return false;
        }
    }

    // Method to update user data (e.g., coins, points, etc.)
    public boolean updateUser(User user) {
        String query = "UPDATE Users SET coins = ?, points = ?, streak = ?, workouts_completed = ?, daily_tasks_completed = ? WHERE id = ?";

        try (Connection connection = getConnection();  // Get a new connection for each operation
             PreparedStatement stmt = connection.prepareStatement(query)) {

            // Set the parameters for the prepared statement
            stmt.setInt(1, user.getCoins());
            stmt.setInt(2, user.getPoints());
            stmt.setInt(3, user.getStreak());
            stmt.setInt(4, user.getWorkoutsCompleted());
            stmt.setInt(5, user.getDailyTasksCompleted());
            stmt.setInt(6, user.getId());  // Use user ID to update the correct record

            // Execute the update and return whether it was successful
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            System.out.println("Error updating user data. Please try again later.");
            logError(e);  // Log the error without showing stack trace to user
            return false;
        }
    }

    // Method to retrieve a user by ID (if needed)
    public User getUserById(int id) {
        String query = "SELECT * FROM Users WHERE id = ?";

        try (Connection connection = getConnection();  // Get a new connection for each operation
             PreparedStatement stmt = connection.prepareStatement(query)) {

            // Set the parameters for the prepared statement
            stmt.setInt(1, id);

            // Execute the query and process the result
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                // Return a User object created from the database result
                return new User(
                    rs.getInt("id"),
                    rs.getString("username"),
                    rs.getString("password"),
                    rs.getInt("coins"),
                    rs.getInt("points"),
                    rs.getInt("streak"),
                    rs.getInt("workouts_completed"),
                    rs.getInt("daily_tasks_completed")
                );
            } else {
                return null;
            }
        } catch (SQLException e) {
            System.out.println("Error retrieving user by ID. Please try again later.");
            logError(e);  // Log the error without showing stack trace to user
            return null;
        }
    }

    // Helper method to log errors (now static)
    private static void logError(SQLException e) {
        // Log the error for development (can be stored in a file or logging system)
        System.err.println("SQL Error: " + e.getMessage());
        // Comment out this line to prevent stack trace from being printed to the console
        // e.printStackTrace();  // Optional: Log to file or handle separately
    }
}

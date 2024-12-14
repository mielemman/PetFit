package services;

import java.sql.*;
import java.util.Scanner;
import models.User;
import utilities.DatabaseConnection;

public class ProgressTracking {
    private User user;
    private Connection connection;

    // Constructor
    public ProgressTracking(User user) {
        this.user = user;
        this.connection = DatabaseConnection.getConnection(); // Assuming you have a DatabaseConnection class
    }

    // Display progress stats (total workouts, total pets, total points, total coins)
    public void displayProgress() {
        try {
            // Query to get total coins, points, workouts, and pets
            String query = "SELECT coins, points, workouts_completed FROM Users WHERE id = ?";
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setInt(1, user.getId());
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                int totalCoins = rs.getInt("coins");
                int totalPoints = rs.getInt("points");
                int totalWorkoutsCompleted = rs.getInt("workouts_completed");

                // Get total pets owned
                query = "SELECT COUNT(*) AS total_pets FROM Pets WHERE user_id = ?";
                stmt = connection.prepareStatement(query);
                stmt.setInt(1, user.getId());
                rs = stmt.executeQuery();
                int totalPetsOwned = 0;
                if (rs.next()) {
                    totalPetsOwned = rs.getInt("total_pets");
                }

                // Display progress stats

                System.out.println("██████████████████████▀█████████████████████████");
                System.out.println("█▄─▄▄─█▄─▄▄▀█─▄▄─█─▄▄▄▄█▄─▄▄▀█▄─▄▄─█─▄▄▄▄█─▄▄▄▄█");
                System.out.println("██─▄▄▄██─▄─▄█─██─█─██▄─██─▄─▄██─▄█▀█▄▄▄▄─█▄▄▄▄─█");
                System.out.println("▀▄▄▄▀▀▀▄▄▀▄▄▀▄▄▄▄▀▄▄▄▄▄▀▄▄▀▄▄▀▄▄▄▄▄▀▄▄▄▄▄▀▄▄▄▄▄▀");

                System.out.println("\n============= Progress Tracking ==============");
                System.out.println("Total Workouts Completed: " + totalWorkoutsCompleted);
                System.out.println("Total Pets Owned: " + totalPetsOwned);
                System.out.println("Total Points: " + totalPoints);
                System.out.println("Total Coins: " + totalCoins);
            }

        } catch (SQLException e) {
            System.out.println("Error retrieving user progress: " + e.getMessage());
        }
    }

    // User Interface for progress tracking
    public void manageProgress() {
        Scanner scanner = new Scanner(System.in);
        boolean running = true;

        while (running) {
            System.out.println("\n--- Progress Tracking Menu ---");
            System.out.println("1. Display Progress");
            System.out.println("0. Return to Main Menu");
            System.out.print("Choose an option: ");
            int choice = getValidInput(scanner, 1);

            switch (choice) {
                case 1:
                    displayProgress();
                    break;
                case 0:
                    running = false;
                    System.out.println("Returning to Main Menu...");
                    break;
                default:
                    System.out.println("Invalid option! Please try again.");
            }
        }
    }

    // Method to ensure valid input within the valid range
    private int getValidInput(Scanner scanner, int max) {
        int choice;
        while (true) {
            if (scanner.hasNextInt()) {
                choice = scanner.nextInt();
                if (choice >= 0 && choice <= max) {
                    break;
                } else {
                    System.out.println("Invalid choice. Please select between 0 and " + max + ".");
                }
            } else {
                System.out.println("Invalid input. Please enter a number.");
                scanner.next(); // Clear invalid input
            }
        }
        return choice;
    }
}

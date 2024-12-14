package services;

import models.User;
import utilities.DatabaseConnection;

public class CoinPointsManager {
    private User user;
    private DatabaseConnection dbConnection;

    // Constructor that accepts a User object
    public CoinPointsManager(User user) {
        this.user = user;
        this.dbConnection = new DatabaseConnection();  // Initialize the database connection
    }

    // Helper method to update the database
    private boolean updateUser(User user) {
        return dbConnection.updateUser(user);  // Update the database
    }

    // Method to increase coins
    public void increaseCoins(int amount) {
        user.setCoins(user.getCoins() + amount);
        if (updateUser(user)) {
            System.out.println(amount + " coins have been added.");
        } else {
            System.out.println("Failed to update coins in the database.");
        }
    }

    // Method to decrease coins
    public void decreaseCoins(int amount) {
        if (user.getCoins() >= amount) {
            user.setCoins(user.getCoins() - amount);
            if (updateUser(user)) {
                System.out.println(amount + " coins have been deducted.");
            } else {
                System.out.println("Failed to update coins in the database.");
            }
        } else {
            System.out.println("Not enough coins.");
        }
    }

    // Method to increase points
    public void increasePoints(int amount) {
        user.setPoints(user.getPoints() + amount);
        if (updateUser(user)) {
            System.out.println(amount + " points have been added.");
        } else {
            System.out.println("Failed to update points in the database.");
        }
    }

    // Method to decrease points
    public void decreasePoints(int amount) {
        if (user.getPoints() >= amount) {
            user.setPoints(user.getPoints() - amount);
            if (updateUser(user)) {
                System.out.println(amount + " points have been deducted.");
            } else {
                System.out.println("Failed to update points in the database.");
            }
        } else {
            System.out.println("Not enough points.");
        }
    }

    // Method to transfer points from one user to another (if needed)
    public void transferPoints(User fromUser, User toUser, int amount) {
        if (fromUser.getPoints() >= amount) {
            fromUser.setPoints(fromUser.getPoints() - amount);
            toUser.setPoints(toUser.getPoints() + amount);

            // Update both users in the database
            boolean successFrom = updateUser(fromUser);
            boolean successTo = updateUser(toUser);

            if (successFrom && successTo) {
                System.out.println(amount + " points have been transferred from " + fromUser.getUsername() + " to " + toUser.getUsername());
            } else {
                System.out.println("Failed to transfer points.");
            }
        } else {
            System.out.println("Not enough points to transfer.");
        }
    }

    // Method to check if a user has enough points or coins for a transaction
    public boolean canAfford(int points, int coins) {
        if (user.getPoints() >= points && user.getCoins() >= coins) {
            return true;
        } else {
            System.out.println("User cannot afford this transaction.");
            return false;
        }
    }

    // Method to increase points specifically for workout completion
    public void increasePointsForWorkout(int points) {
        increasePoints(points);  // Reuse the increasePoints method
    }
}

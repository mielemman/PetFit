package services;

import models.User;
import utilities.DatabaseConnection;

public class CoinPointsManager {
    private User user;
    private DatabaseConnection dbConnection;

  
    public CoinPointsManager(User user) {
        this.user = user;
        this.dbConnection = new DatabaseConnection();  
    }

    private boolean updateUser(User user) {
        return dbConnection.updateUser(user); 
    }

    public void increaseCoins(int amount) {
        user.setCoins(user.getCoins() + amount);
        if (updateUser(user)) {
            System.out.println(amount + " coins have been added.");
        } else {
            System.out.println("Failed to update coins in the database.");
        }
    }
  
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

    public void increasePoints(int amount) {
        user.setPoints(user.getPoints() + amount);
        if (updateUser(user)) {
            System.out.println(amount + " points have been added.");
        } else {
            System.out.println("Failed to update points in the database.");
        }
    }

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
    
    public boolean canAfford(int points, int coins) {
        if (user.getPoints() >= points && user.getCoins() >= coins) {
            return true;
        } else {
            System.out.println("User cannot afford this transaction.");
            return false;
        }
    }

    public void increaseWorkout(int amount) {
        user.setWorkoutsCompleted(user.getWorkoutsCompleted() + amount);
        if (updateUser(user)) {
            System.out.println(amount + " workout completed have been added.");
        } else {
            System.out.println("Failed to update workout completed in the database.");
        }
    }
}

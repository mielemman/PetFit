package models;

import java.util.ArrayList;
import java.util.List;
import utilities.DatabaseConnection;

public class User {
    private int id;
    private String username;
    private String password;
    private int coins;
    private int points;
    private int streak;
    private int workoutsCompleted;
    private int dailyTasksCompleted;
    private List<Pet> pets;
    private List<String> inventory;
    private int totalWorkoutsCompleted;
    private int totalPetsOwned;
    private int totalPoints;
    private int totalCoins;

    private DatabaseConnection dbConnection;

    // Constructor with ID (used when loading user data from the database)
    public User(int id, String username, String password, int coins, int points, int streak, int workoutsCompleted, int dailyTasksCompleted) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.coins = coins;
        this.points = points;
        this.streak = streak;
        this.workoutsCompleted = workoutsCompleted;
        this.dailyTasksCompleted = dailyTasksCompleted;
        this.pets = new ArrayList<>();
        this.inventory = new ArrayList<>();
        this.dbConnection = new DatabaseConnection();  // Initialize the database connection
        this.totalWorkoutsCompleted = workoutsCompleted;
        this.totalPetsOwned = 0;  // Or retrieve this from the database if required
        this.totalPoints = points;
        this.totalCoins = coins;
    }

    // Constructor without ID (used during registration)
    public User(String username, String password) {
        this.username = username;
        this.password = password;
        this.coins = 0;
        this.points = 0;
        this.streak = 0;
        this.workoutsCompleted = 0;
        this.dailyTasksCompleted = 0;
        this.pets = new ArrayList<>();
        this.inventory = new ArrayList<>();
        this.dbConnection = new DatabaseConnection();  // Initialize the database connection
        this.totalWorkoutsCompleted = 0;
        this.totalPetsOwned = 0;
        this.totalPoints = 0;
        this.totalCoins = 0;
    }

    // Getters and setters for all fields
    public int getTotalWorkoutsCompleted() {
        return totalWorkoutsCompleted;
    }

    public int getTotalPetsOwned() {
        return totalPetsOwned;
    }

    public int getTotalPoints() {
        return totalPoints;
    }

    public int getTotalCoins() {
        return totalCoins;  // Ensure this method is in the User class
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getCoins() {
        return coins;
    }

    public void setCoins(int coins) {
        this.coins = coins;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public int getStreak() {
        return streak;
    }

    public void setStreak(int streak) {
        this.streak = streak;
    }

    public int getWorkoutsCompleted() {
        return workoutsCompleted;
    }

    public void setWorkoutsCompleted(int workoutsCompleted) {
        this.workoutsCompleted = workoutsCompleted;
    }

    public int getDailyTasksCompleted() {
        return dailyTasksCompleted;
    }

    public void setDailyTasksCompleted(int dailyTasksCompleted) {
        this.dailyTasksCompleted = dailyTasksCompleted;
    }

    // Method to update progress
    public void updateProgress() {
        // Update the user's progress in the database
        boolean success = dbConnection.updateUser(this);
        if (success) {
            System.out.println("Progress Updated!");
        } else {
            System.out.println("Failed to update progress.");
        }
    }

    // Method to view the user's profile
    public void viewProfile() {
        System.out.println("Username: " + username);
        System.out.println("Coins: " + coins);
        System.out.println("Points: " + points);
        System.out.println("Streak: " + streak);
        System.out.println("Workouts Completed: " + workoutsCompleted);
        System.out.println("Daily Tasks Completed: " + dailyTasksCompleted);
    }

    // Method to get the list of pets
    public List<Pet> getPets() {
        return pets;
    }

    // Method to set the list of pets
    public void setPets(List<Pet> pets) {
        this.pets = pets;
        System.out.println("Pets list has been updated.");
    }

    // Method to add a pet
    public void addPet(Pet pet) {
        pets.add(pet);
        this.totalPetsOwned++;  // Update total pets owned
        System.out.println(pet.getName() + " has been added to your pets!");
    }

    // Method to remove a pet
    public void removePet(Pet pet) {
        if (pets.remove(pet)) {
            this.totalPetsOwned--;  // Update total pets owned
            System.out.println(pet.getName() + " has been removed from your pets.");
        } else {
            System.out.println("Pet not found.");
        }
    }

    // Method to add an item to the inventory
    public void addItem(String item) {
        inventory.add(item);
    }

    // Method to remove an item from the inventory
    public void removeItem(String item) {
        inventory.remove(item);
    }

    // Method to get the user's inventory
    public List<String> getInventory() {
        return inventory;
    }

    // Method to load user data from the database (after authentication)
    public static User loadUserData(int userId) {
        DatabaseConnection dbConnection = new DatabaseConnection();
        return dbConnection.getUserById(userId);
    }
}

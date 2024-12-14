package services;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import models.Pet;
import models.User;
import models.specific.Bear;
import models.specific.Cat;
import models.specific.Dog;
import models.specific.Dragon;
import models.specific.Pig;
import models.specific.Rabbit;
import utilities.DatabaseConnection;

public class ShopService {
    private User user;
    private Map<String, Integer> items; // Store item names and their costs
    private Scanner scanner;
    private CoinPointsManager coinPointsManager; // Reference to CoinPointsManager
    private DatabaseConnection databaseConnection; // Database connection reference

    // Constructor
    public ShopService(User user) {
        this.user = user;
        this.scanner = new Scanner(System.in);
        this.coinPointsManager = new CoinPointsManager(user); // Pass the user to CoinPointsManager
        this.databaseConnection = new DatabaseConnection(); // Initialize database connection
        initializeShopItems();
    }

    // Initialize available items in the shop
    private void initializeShopItems() {
        items = new HashMap<>();
        items.put("Pet Food", 10);
        items.put("Toy", 15);
        items.put("Cat (Pet)", 20);
        items.put("Dog (Pet)", 40);
        items.put("Pig (Pet)", 70);
        items.put("Rabbit (Pet)", 100);
        items.put("Bear (Pet)", 150);
        items.put("Lion (Pet)", 200);
        items.put("Dragon (Pet)", 350);
    }

    // Display available items in the shop with improved formatting
    public void displayItems() {
        System.out.println("\n=== Available Items ===");
        System.out.println("Here are the items you can purchase:");
        items.forEach((itemName, cost) -> {
            System.out.printf("%-20s : %d coins\n", itemName, cost);
        });
    }

    // Method to purchase a specific pet with clearer prompts
    public void purchasePet(String petName) {
        if (!items.containsKey(petName)) {
            System.out.println("Pet not found in the shop.");
            return;
        }

        // Ensure that the user can afford the pet
        int petCost = items.get(petName);
        if (coinPointsManager.canAfford(0, petCost)) {  // Check if user can afford the pet
            coinPointsManager.decreaseCoins(petCost);  // Decrease coins for pet purchase
            Pet newPet = createPet(petName);
            if (newPet != null) {
                user.addPet(newPet); // Add the pet to the user's collection
                savePetToDatabase(newPet); // Save the pet to the database
                System.out.println(petName + " has been added to your pet collection!");
            }
        } else {
            System.out.println("Insufficient coins to purchase " + petName + ".");
        }
    }

    // Method to create a pet based on its name (factory method)
    private Pet createPet(String petName) {
        switch (petName) {
            case "Cat (Pet)": return new Cat("Cat");
            case "Dog (Pet)": return new Dog("Dog");
            case "Pig (Pet)": return new Pig("Pig");
            case "Rabbit (Pet)": return new Rabbit("Rabbit");
            case "Bear (Pet)": return new Bear("Bear");
            case "Dragon (Pet)": return new Dragon("Dragon");
            default:
                System.out.println("Unknown pet type!");
                return null;
        }
    }

    // Save the purchased pet to the database
    private void savePetToDatabase(Pet pet) {
        String query = "INSERT INTO Pets (user_id, name, type, hunger, happiness, health) VALUES (?, ?, ?, ?, ?, ?)";
        
        try (var connection = databaseConnection.getConnection();
             var preparedStatement = connection.prepareStatement(query)) {
            
            preparedStatement.setInt(1, user.getId()); // Set user ID
            preparedStatement.setString(2, pet.getName()); // Set pet name
            preparedStatement.setString(3, pet.getClass().getSimpleName()); // Set pet type (class name as type)
            preparedStatement.setInt(4, pet.getHunger()); // Set pet hunger
            preparedStatement.setInt(5, pet.getHappiness()); // Set pet happiness
            preparedStatement.setInt(6, pet.getHealth()); // Set pet health

            preparedStatement.executeUpdate(); // Execute the query to insert the pet
            System.out.println("Pet saved to the database successfully.");
        } catch (Exception e) {
            System.out.println("Error saving pet to the database: " + e.getMessage());
        }
    }

    // Method to add new items to the shop dynamically
    public void addShopItem(String itemName, int cost) {
        if (items.containsKey(itemName)) {
            System.out.println(itemName + " is already available in the shop.");
        } else {
            items.put(itemName, cost);
            System.out.println(itemName + " has been added to the shop for " + cost + " coins.");
        }
    }

    // Method to remove an item from the shop
    public void removeShopItem(String itemName) {
        if (items.containsKey(itemName)) {
            items.remove(itemName);
            System.out.println(itemName + " has been removed from the shop.");
        } else {
            System.out.println(itemName + " is not available in the shop.");
        }
    }

    // Method to check user inventory
    public void displayUserInventory() {
        System.out.println("\n=== Your Inventory ===");
        if (user.getInventory().isEmpty()) {
            System.out.println("Your inventory is empty.");
        } else {
            user.getInventory().forEach(item -> System.out.println("- " + item));
        }
    }

    // Method to enter the shop and interact with the UI
    public void enterShop() {
        boolean shopping = true;

        while (shopping) {
            System.out.println("\n");
            System.out.println("   ██████████████████████");
            System.out.println("   █─▄▄▄▄█─█─█─▄▄─█▄─▄▄─█");
            System.out.println("   █▄▄▄▄─█─▄─█─██─██─▄▄▄█");
            System.out.println("   ▀▄▄▄▄▄▀▄▀▄▀▄▄▄▄▀▄▄▄▀▀▀");

            System.out.println("\n=== Welcome to the Shop ===");
            System.out.println("Total Coins: " + user.getCoins());
            System.out.println("1. View Available Items");
            System.out.println("2. View Your Inventory");
            System.out.println("3. Purchase an Item");
            System.out.println("4. Purchase a Pet");
            System.out.println("5. Exit Shop");

            int choice = getValidMenuChoice();  // Ensure valid input

            switch (choice) {
                case 1:
                    displayItems();
                    break;
                case 2:
                    displayUserInventory();
                    break;
                case 3:
                    purchaseItemPrompt();
                    break;
                case 4:
                    purchasePetPrompt();
                    break;
                case 5:
                    System.out.println("Exiting the shop...");
                    shopping = false;
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    // Helper method to ensure the user enters a valid menu choice
    private int getValidMenuChoice() {
        int choice = -1;
        while (choice < 1 || choice > 5) {
            System.out.print("Please choose an option (1-5): ");
            if (scanner.hasNextInt()) {
                choice = scanner.nextInt();
                scanner.nextLine();  // Clear the buffer
                if (choice < 1 || choice > 5) {
                    System.out.println("Invalid choice. Please try again.");
                }
            } else {
                System.out.println("Invalid input. Please enter a number between 1 and 5.");
                scanner.nextLine(); // Clear invalid input
            }
        }
        return choice;
    }

    // Prompt the user to purchase an item
    private void purchaseItemPrompt() {
        System.out.print("Enter the name of the item you wish to purchase: ");
        String itemName = scanner.nextLine();
        purchaseItem(itemName);
    }

    // Prompt the user to purchase a pet
    private void purchasePetPrompt() {
        System.out.print("Enter the name of the pet you wish to purchase (e.g., 'Cat', 'Dragon'): ");
        String petName = scanner.nextLine();
        purchasePet(petName); // Allow user to purchase pets
    }

    // Method to purchase an item
    private void purchaseItem(String itemName) {
        if (!items.containsKey(itemName)) {
            System.out.println(itemName + " is not available in the shop.");
            return;
        }

        int itemCost = items.get(itemName);
        if (coinPointsManager.canAfford(itemCost, 0)) { // Check if user can afford the item
            coinPointsManager.decreaseCoins(itemCost); // Decrease coins for item purchase
            user.addItem(itemName); // Add item to user's inventory
            System.out.println(itemName + " has been added to your inventory!");
        } else {
            System.out.println("Insufficient coins to purchase " + itemName + ".");
        }
    }
}

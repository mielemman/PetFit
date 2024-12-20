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
    private Map<String, Integer> items; 
    private Scanner scanner;
    private CoinPointsManager coinPointsManager; 
    private DatabaseConnection databaseConnection; 

    
    public ShopService(User user) {
        this.user = user;
        this.scanner = new Scanner(System.in);
        this.coinPointsManager = new CoinPointsManager(user); 
        this.databaseConnection = new DatabaseConnection(); 
        initializeShopItems();
    }

    
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


    public void displayItems() {
        System.out.println("+------------------------------+");
        System.out.println("|        Available Items       |");
        System.out.println("+------------------------------+");
        System.out.println("Here are the items you can purchase:");
        items.forEach((itemName, cost) -> {
            System.out.printf("%-20s : %d coins\n", itemName, cost);
        });
        System.out.println("+------------------------------+");
    }


    public void purchasePet(String petName) {
        if (!items.containsKey(petName)) {
            System.out.println("Pet not found in the shop.");
            return;
        }
       
        int petCost = items.get(petName);
        if (coinPointsManager.canAfford(0, petCost)) {  
            coinPointsManager.decreaseCoins(petCost);  
            Pet newPet = createPet(petName);
            if (newPet != null) {
                user.addPet(newPet); 
                savePetToDatabase(newPet); 
                System.out.println(petName + " has been added to your pet collection!");
            }
        } else {
            System.out.println("Insufficient coins to purchase " + petName + ".");
        }
    }


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

    
    private void savePetToDatabase(Pet pet) {
        String query = "INSERT INTO Pets (user_id, name, type, hunger, happiness, health) VALUES (?, ?, ?, ?, ?, ?)";
        
        try (var connection = databaseConnection.getConnection();
             var preparedStatement = connection.prepareStatement(query)) {
            
            preparedStatement.setInt(1, user.getId()); 
            preparedStatement.setString(2, pet.getName()); 
            preparedStatement.setString(3, pet.getClass().getSimpleName()); 
            preparedStatement.setInt(4, pet.getHunger()); 
            preparedStatement.setInt(5, pet.getHappiness()); 
            preparedStatement.setInt(6, pet.getHealth()); 

            preparedStatement.executeUpdate(); 
            System.out.println("Pet saved to the database successfully.");
        } catch (Exception e) {
            System.out.println("Error saving pet to the database: " + e.getMessage());
        }
    }


    public void addShopItem(String itemName, int cost) {
        if (items.containsKey(itemName)) {
            System.out.println(itemName + " is already available in the shop.");
        } else {
            items.put(itemName, cost);
            System.out.println(itemName + " has been added to the shop for " + cost + " coins.");
        }
    }


    public void removeShopItem(String itemName) {
        if (items.containsKey(itemName)) {
            items.remove(itemName);
            System.out.println(itemName + " has been removed from the shop.");
        } else {
            System.out.println(itemName + " is not available in the shop.");
        }
    }

 
    public void displayUserInventory() {
        System.out.println("+------------------------------+");
        System.out.println("|         Your Inventory       |");
        System.out.println("+------------------------------+");
        if (user.getInventory().isEmpty()) {
            System.out.println("Your inventory is empty.");
        } else {
            user.getInventory().forEach(item -> System.out.println("- " + item));
        }
        System.out.println("+------------------------------+");
    }


    public void enterShop() {
        boolean shopping = true;

        while (shopping) {
            System.out.println("\n");
            System.out.println("   ██████████████████████");
            System.out.println("   █─▄▄▄▄█─█─█─▄▄─█▄─▄▄─█");
            System.out.println("   █▄▄▄▄─█─▄─█─██─██─▄▄▄█");
            System.out.println("   ▀▄▄▄▄▄▀▄▀▄▀▄▄▄▄▀▄▄▄▀▀▀");

            System.out.println("+------------------------------+");
            System.out.println("|      Welcome to the Shop     |");
            System.out.println("+------------------------------+");
            System.out.println("Total Coins: " + user.getCoins());
            System.out.println("+------------------------------+");
            System.out.println("1. View Available Items");
            System.out.println("2. View Your Inventory");
            System.out.println("3. Purchase an Item");
            System.out.println("4. Purchase a Pet");
            System.out.println("5. Exit Shop");
            System.out.println("+------------------------------+");

            int choice = getValidMenuChoice();  

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

 
    private int getValidMenuChoice() {
        int choice = -1;
        while (choice < 1 || choice > 5) {
            System.out.print("Please choose an option (1-5): ");
            if (scanner.hasNextInt()) {
                choice = scanner.nextInt();
                scanner.nextLine(); 
                if (choice < 1 || choice > 5) {
                    System.out.println("Invalid choice. Please try again.");
                }
            } else {
                System.out.println("Invalid input. Please enter a number between 1 and 5.");
                scanner.nextLine(); 
            }
        }
        return choice;
    }


    private void purchaseItemPrompt() {
        System.out.print("Enter the name of the item you wish to purchase [enter to exit]: ");
        String itemName = scanner.nextLine();
        purchaseItem(itemName);
    }


    private void purchasePetPrompt() {
        System.out.print("Enter the name of the pet you wish to purchase (e.g., 'Cat', 'Dragon') [enter to exit]: ");
        String petName = scanner.nextLine();
        purchasePet(petName); 
    }


    private void purchaseItem(String itemName) {
        if (!items.containsKey(itemName)) {
            System.out.println(itemName + " is not available in the shop.");
            return;
        }

        int itemCost = items.get(itemName);
        if (coinPointsManager.canAfford(itemCost, 0)) { 
            coinPointsManager.decreaseCoins(itemCost); 
            user.addItem(itemName);
            System.out.println(itemName + " has been added to your inventory!");
        } else {
            System.out.println("Insufficient coins to purchase " + itemName + ".");
        }
    }
}

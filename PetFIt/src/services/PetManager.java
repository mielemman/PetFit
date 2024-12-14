package services;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
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
import utilities.TimerUtils;

public class PetManager {
    private User user;
    private DatabaseConnection databaseConnection;
    private TimerUtils timerUtils;

    public PetManager(User user) {
        this.user = user;
        this.databaseConnection = new DatabaseConnection();
        this.timerUtils = new TimerUtils(); // Initialize TimerUtils for stat decay
    }

    // Fetch pets from the database and update the user's pets list
    private void fetchPetsFromDatabase() {
        List<Pet> pets = new ArrayList<>();
        String query = "SELECT * FROM Pets WHERE user_id = ?";

        try (Connection connection = databaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setInt(1, user.getId());
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                String petName = resultSet.getString("name");
                String petType = resultSet.getString("type");

                Pet pet = createPet(petName, petType);
                if (pet != null) {
                    pets.add(pet);
                }
            }

            user.setPets(pets);
        } catch (SQLException e) {
            System.out.println("Error fetching pets from the database: " + e.getMessage());
        }
    }

    // Manage pets, fetch them, and show options to interact with them
    public void managePets() {
        fetchPetsFromDatabase();

        if (user.getPets().isEmpty()) {
            System.out.println("You have no pets to manage. Please adopt one first.");
            return;
        }

        Scanner scanner = new Scanner(System.in);
        boolean running = true;

        while (running) {
            

            System.out.println("   ███████████████████");
            System.out.println("   █▄─▄▄─█▄─▄▄─█─▄─▄─█");
            System.out.println("   ██─▄▄▄██─▄█▀███─███");
            System.out.println("   ▀▄▄▄▀▀▀▄▄▄▄▄▀▀▄▄▄▀▀");
            System.out.println("\n--- Pet Management ---");
            System.out.println("Choose a pet to manage:");
            for (int i = 0; i < user.getPets().size(); i++) {
                System.out.println((i + 1) + ". " + user.getPets().get(i).getName());
            }
            System.out.println("0. Exit");

            int choice = getValidInput(scanner, user.getPets().size());
            if (choice == 0) {
                running = false;
                System.out.println("Exiting pet management.");
            } else {
                Pet selectedPet = user.getPets().get(choice - 1);
                managePetActions(selectedPet);
            }
        }
    }

    // Method to create a pet based on its type
    private Pet createPet(String petName, String petType) {
        switch (petType) {
            case "Cat": return new Cat(petName);
            case "Dog": return new Dog(petName);
            case "Pig": return new Pig(petName);
            case "Rabbit": return new Rabbit(petName);
            case "Bear": return new Bear(petName);
            case "Dragon": return new Dragon(petName);
            default:
                System.out.println("Unknown pet type: " + petType);
                return null;
        }
    }

    // Display pet actions and handle user input
    private void managePetActions(Pet pet) {
        Scanner scanner = new Scanner(System.in);
        boolean petRunning = true;

        while (petRunning) {
            System.out.println("\n--- Manage Pet: " + pet.getName() + " ---");
            System.out.println("1. Feed " + pet.getName());
            System.out.println("2. Play with " + pet.getName());
            System.out.println("3. View " + pet.getName() + "'s Stats");
            System.out.println("4. Back to Pet List");
            System.out.print("Choose an action: ");

            int action = getValidInput(scanner, 4);

            switch (action) {
                case 1:
                    pet.feed();
                    System.out.println(pet.getName() + " has been fed.");
                    break;
                case 2:
                    pet.play();
                    System.out.println(pet.getName() + " is playing!");
                    break;
                case 3:
                    pet.viewStats();
                    break;
                case 4:
                    petRunning = false;
                    break;
                default:
                    System.out.println("Invalid option! Please try again.");
            }
        }

    }

    // Apply stat decay to all pets
    public void decayAllPetStats() {
        if (timerUtils.isTimeForStatDecay()) { // Check if it's time for stat decay
            for (Pet pet : user.getPets()) {
                int hunger = pet.getHunger();
                int happiness = pet.getHappiness();

                hunger = Math.max(0, hunger - 10);  // Decrease hunger
                happiness = Math.max(0, happiness - 10);  // Decrease happiness

                pet.setHunger(hunger);
                pet.setHappiness(happiness);

                if (hunger == 0 && happiness == 0) {
                    int health = Math.max(0, pet.getHealth() - 10);  // Decrease health if both hunger and happiness are 0
                    pet.setHealth(health);
                }

                System.out.println("\nPet stats after decay:");
                pet.viewStats();
            }

            // Update decay timestamp
            timerUtils.updateDecayTimestamp();
        } else {
            System.out.println("Pet stats are not due for decay yet.");
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

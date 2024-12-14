package models;

import java.util.HashMap;
import java.util.Map;

public abstract class Pet {
    protected String name;
    protected int hunger;
    protected int happiness;
    protected int health;
    protected Map<String, Integer> stats;
    protected String petType;
    protected String asciiArt;

    public Pet(String name, String asciiArt) {
        this.name = name;
        this.hunger = 100;
        this.happiness = 100;
        this.health = 100;
        this.stats = new HashMap<>();
        this.petType = this.getClass().getSimpleName();
        this.asciiArt = asciiArt;

        updateStats();
    }

    private void updateStats() {
        stats.put("hunger", hunger);
        stats.put("happiness", happiness);
        stats.put("health", health);
    }

    // Getters and Setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getHunger() {
        return hunger;
    }

    public void setHunger(int hunger) {
        this.hunger = Math.max(0, Math.min(100, hunger));
        updateStats();
    }

    public int getHappiness() {
        return happiness;
    }

    public void setHappiness(int happiness) {
        this.happiness = Math.max(0, Math.min(100, happiness));
        updateStats();
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = Math.max(0, Math.min(100, health));
        updateStats();
    }

    public String getPetType() {
        return petType;
    }


    public void feed() {
        setHunger(hunger + 20);
        System.out.println(name + " has been fed and feels more full!");
    }

    public void play() {
        setHappiness(happiness + 15); 
        System.out.println(name + " is having fun playing with you!");
    }

    public boolean isHealthy() {
        return health > 50;
    }

    public String healthStatus() {
        if (health > 75) {
            return "Healthy";
        } else if (health > 50) {
            return "Needs Attention";
        } else {
            return "Unwell";
        }
    }

    public void viewStats() {
        System.out.println("\n--- " + name + " - " + petType + " Stats ---");
        System.out.println(asciiArt);
        System.out.println("Hunger: " + hunger + "/100 " + getHungerStatus());
        System.out.println("Happiness: " + happiness + "/100 " + getHappinessStatus());
        System.out.println("Health: " + health + "/100 " + healthStatus());
    }

    private String getHungerStatus() {
        if (hunger > 75) {
            return "Well-fed :)";
        } else if (hunger > 50) {
            return "Hungry :/";
        } else {
            return "Starving :(";
        }
    }

    private String getHappinessStatus() {
        if (happiness > 75) {
            return "Happy :)";
        } else if (happiness > 50) {
            return "Neutral :|";
        } else {
            return "Sad :(";
        }
    }

 
    public abstract void petAction();

    public void managePet() {
        try (java.util.Scanner scanner = new java.util.Scanner(System.in)) {
            boolean running = true;

            while (running) {
                System.out.println("\n--- Pet Management Menu ---");
                System.out.println("1. View Stats");
                System.out.println("2. Feed");
                System.out.println("3. Play");
                System.out.println("4. Exit");

                System.out.print("Choose an option: ");
                int choice = getValidInput(scanner, 4);

                switch (choice) {
                    case 1:
                        viewStats();
                        break;
                    case 2:
                        feed();
                        break;
                    case 3:
                        play();
                        break;
                    case 4:
                        running = false;
                        System.out.println("Exiting Pet Management.");
                        break;
                    default:
                        System.out.println("Invalid option! Please try again.");
                }
            }
        }
    }

    private int getValidInput(java.util.Scanner scanner, int max) {
        while (true) {
            if (scanner.hasNextInt()) {
                int choice = scanner.nextInt();
                if (choice >= 1 && choice <= max) {
                    return choice;
                }
                System.out.println("Invalid choice. Please select between 1 and " + max + ".");
            } else {
                System.out.println("Invalid input. Please enter a number.");
                scanner.next();
            }
        }
    }
}

package main;

import java.util.Scanner;
import models.User;
import services.*;
import utilities.*;

public class PetFitApp {
    private static User currentUser;
    private static PetManager petManager;
    private static WorkoutManager workoutManager;
    private static WorkoutMenu workoutMenu;
    private static ShopService shopService;
    private static ProgressTracking progressTracking;
    private static DatabaseConnection databaseConnection = new DatabaseConnection();
    private static TimerUtils timerUtils = new TimerUtils();
    private static CoinPointsManager coinPointsManager;

    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        initializeTimers();
        loginMenu();
    }

    private static void loginMenu() {
        System.out.println("\n██████╗░███████╗████████╗███████╗██╗████████╗");
        System.out.println("██╔══██╗██╔════╝╚══██╔══╝██╔════╝██║╚══██╔══╝");
        System.out.println("██████╔╝█████╗░░░░░██║░░░█████╗░░██║░░░██║░░░");
        System.out.println("██╔═══╝░██╔══╝░░░░░██║░░░██╔══╝░░██║░░░██║░░░");
        System.out.println("██║░░░░░███████╗░░░██║░░░██║░░░░░██║░░░██║░░░");
        System.out.println("╚═╝░░░░░╚══════╝░░░╚═╝░░░╚═╝░░░░░╚═╝░░░╚═╝░░░");
        System.out.println("+-------------------------------------------+");
        System.out.println("|                                           |");
        System.out.println("|            WELCOME TO PETFIT!             |");
        System.out.println("|                                           |");
        System.out.println("+-------------------------------------------+");
        System.out.println("1. Login");
        System.out.println("2. Register");
        System.out.println("3. Exit");
        System.out.println("+-------------------------------------------+");
        System.out.print("Choose an option: ");

        int choice = getValidChoice(1, 3);
        switch (choice) {
            case 1 -> loginUser();
            case 2 -> registerUser();
            case 3 -> exitApplication();
        }
    }

    private static void loginUser() {
        System.out.println("+-------------------------------------------+");
        System.out.print("Enter username: ");
        String username = scanner.nextLine();
        System.out.print("Enter password: ");
        String password = scanner.nextLine();

        currentUser = databaseConnection.authenticateUser(username, password);
        if (currentUser != null) {
            System.out.println("+-------------------------------------------+");
            System.out.println("\nLogin successful! Welcome, " + username + " !");

            workoutManager = new WorkoutManager(currentUser);

            initializeServices();
            mainMenu();
        } else {
            System.out.println("\nInvalid credentials. Try again.");
            loginMenu();
        }
    }

    private static void registerUser() {
        System.out.print("\nEnter username: ");
        String username = scanner.nextLine();
        System.out.print("Enter password: ");
        String password = scanner.nextLine();

        if (databaseConnection.registerUser(username, password)) {
            System.out.println("\nRegistration successful! You can now log in.");
            loginMenu();
        } else {
            System.out.println("\nRegistration failed. Try again.");
            registerUser();
        }
    }

    private static void mainMenu() {
        boolean running = true;

        while (running) {
            handleTimers();
            System.out.println("\n ██████████████████████████████");
            System.out.println(" █▄─▀█▀─▄█▄─▄▄─█▄─▀█▄─▄█▄─██─▄█");
            System.out.println(" ██─█▄█─███─▄█▀██─█▄▀─███─██─██");
            System.out.println(" ▀▄▄▄▀▄▄▄▀▄▄▄▄▄▀▄▄▄▀▀▄▄▀▀▄▄▄▄▀▀");
            System.out.println("+------------------------------+");
            System.out.println("|          Main Menu           |");
            System.out.println("+------------------------------+");
            System.out.println("1. View Progress");
            System.out.println("2. Start Workout");
            System.out.println("3. Manage Pets");
            System.out.println("4. Shop");
            System.out.println("5. Logout");
            System.out.println("+------------------------------+");
            System.out.print("Choose an option: ");

            int choice = getValidChoice(1, 5);
            switch (choice) {
                case 1 -> progressTracking.displayProgress();
                case 2 -> {
                    workoutMenu.displayMenu(currentUser);
                    MotivationalMessages.getRandomMessage();
                }
                case 3 -> petManager.managePets();
                case 4 -> shopService.enterShop();
                case 5 -> {
                    System.out.println("\nLogging out...");
                    running = false;
                    loginMenu();
                }
            }
        }
    }

    private static void initializeServices() {
        petManager = new PetManager(currentUser);
        shopService = new ShopService(currentUser);
        progressTracking = new ProgressTracking(currentUser);
        coinPointsManager = new CoinPointsManager(currentUser);
        workoutMenu = new WorkoutMenu(workoutManager, coinPointsManager);
    }

    private static void initializeTimers() {
        timerUtils.updateDecayTimestamp();
        timerUtils.updateDailyResetTimestamp();
    }

    private static void handleTimers() {
        if (timerUtils.isTimeForStatDecay()) {
            petManager.decayAllPetStats();
            timerUtils.updateDecayTimestamp();
        }
    }

    private static int getValidChoice(int min, int max) {
        while (true) {
            try {
                int choice = Integer.parseInt(scanner.nextLine());
                if (choice >= min && choice <= max) return choice;
            } catch (NumberFormatException ignored) {
            }
            System.out.print("Invalid input. Choose a valid option: ");
        }
    }

    private static void exitApplication() {
        System.out.println("\nExiting... Goodbye! 👋");
        System.exit(0);
    }
}

package services;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import models.User;

public class WorkoutMenu {
    private WorkoutManager workoutManager;
    private CoinPointsManager coinPointsManager;
    private Scanner scanner;

    // Constructor
    public WorkoutMenu(WorkoutManager workoutManager, CoinPointsManager coinPointsManager) {
        this.workoutManager = workoutManager;
        this.coinPointsManager = coinPointsManager;
        this.scanner = new Scanner(System.in);
    }

    // Method to display the workout menu
    public void displayMenu(User user) {
        boolean exit = false;
        while (!exit) {
            System.out.println("\n██████████████████████████████████████████████");
            System.out.println("█▄─█▀▀▀█─▄█─▄▄─█▄─▄▄▀█▄─█─▄█─▄▄─█▄─██─▄█─▄─▄─█");
            System.out.println("██─█─█─█─██─██─██─▄─▄██─▄▀██─██─██─██─████─███");
            System.out.println("▀▀▄▄▄▀▄▄▄▀▀▄▄▄▄▀▄▄▀▄▄▀▄▄▀▄▄▀▄▄▄▄▀▀▄▄▄▄▀▀▀▄▄▄▀▀");

            System.out.println("==============================================");
            System.out.println("                 Workout Menu                 ");
            System.out.println("==============================================");
            System.out.println("Total Points: " + user.getPoints() + " | Total Coins: " + user.getCoins());
            System.out.println("\n1. Predefined Workouts");
            System.out.println("2. User-Defined Workouts");
            System.out.println("3. View Workout Progress");
            System.out.println("4. Add or Remove User-Defined Workout");
            System.out.println("5. Exit");
            System.out.print("\nPlease enter your choice: ");
            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    showPredefinedWorkouts(user);
                    break;
                case 2:
                    showUserDefinedWorkouts(user);
                    break;
                case 3:
                    workoutManager.viewProgress();
                    break;
                case 4:
                    addOrRemoveUserDefinedWorkout(user);
                    break;
                case 5:
                    exit = true;
                    System.out.println("\nExiting Workout Menu... Thank you for using PetFit!");
                    break;
                default:
                    System.out.println("\nInvalid choice. Please try again.");
            }
        }
    }

    // Show predefined workouts and handle workout selection
    private void showPredefinedWorkouts(User user) {
        List<String> workouts = workoutManager.getPredefinedWorkouts();
        System.out.println("\n=== Predefined Workouts ===");
        for (int i = 0; i < workouts.size(); i++) {
            System.out.println((i + 1) + ". " + workouts.get(i));
        }

        System.out.print("\nSelect a workout program (1-" + workouts.size() + "), or 0 to return: ");
        int workoutChoice = scanner.nextInt();
        if (workoutChoice == 0) {
            return;  // Return to the workout menu
        }
        if (workoutChoice > 0 && workoutChoice <= workouts.size()) {
            showExercisesForWorkout(workoutChoice - 1, user);
        } else {
            System.out.println("Invalid selection.");
        }
    }

    // Show exercises for the selected workout program
    private void showExercisesForWorkout(int workoutIndex, User user) {
        List<String> exercises = workoutManager.getExercisesForWorkout(workoutIndex);
        boolean allCompleted = true;

        System.out.println("\n=== " + workoutManager.getPredefinedWorkouts().get(workoutIndex) + " Exercises ===");
        for (int i = 0; i < exercises.size(); i++) {
            String status = workoutManager.getExerciseCompletionStatus(workoutIndex, i) ? "(DONE)" : "(NOT DONE)";
            System.out.println((i + 1) + ". " + exercises.get(i) + " " + status);
            if (!workoutManager.getExerciseCompletionStatus(workoutIndex, i)) {
                allCompleted = false;
            }
        }

        // Show "Return" option
        System.out.println("\n0. Return to Workout Menu");

        if (allCompleted) {
            System.out.println("All exercises completed for this workout.");
            return;
        }

        System.out.print("\nSelect an exercise to complete (1-" + exercises.size() + "), or 0 to return: ");
        int exerciseChoice = scanner.nextInt();
        if (exerciseChoice == 0) {
            return;  // Return to the workout menu
        }

        if (exerciseChoice > 0 && exerciseChoice <= exercises.size()) {
            startExercise(workoutIndex, exerciseChoice - 1, user);
            // Return to the exercises menu after completing or skipping an exercise
            showExercisesForWorkout(workoutIndex, user);
        } else {
            System.out.println("Invalid selection.");
        }
    }

    // Start exercise and ask for completion
    private void startExercise(int workoutIndex, int exerciseIndex, User user) {
        System.out.println("Starting exercise: " + workoutManager.getExercisesForWorkout(workoutIndex).get(exerciseIndex));

        // Ask if the user completed the exercise
        System.out.print("Have you completed this exercise? (Y/N): ");
        String completion = scanner.next();

        if (completion.equalsIgnoreCase("Y")) {
            workoutManager.completeExercise(workoutIndex, exerciseIndex);
            // Award points and coins
            coinPointsManager.increasePoints(10);  // Example reward system for points
            coinPointsManager.increaseCoins(10);  // Example reward system for coins
            workoutManager.updateStreak();
            System.out.println("---------------------------------------------------");
            System.out.println("5 points and 5 coins earned! Keep up the good work.");
        } else {
            workoutManager.resetStreak();
            System.out.println("Try again tomorrow.");
        }
    }

    // Show and handle user-defined workouts
    private void showUserDefinedWorkouts(User user) {
        List<String> userWorkouts = workoutManager.getUserDefinedWorkouts();

        if (userWorkouts.isEmpty()) {
            System.out.println("\nNo user-defined workouts found. You can add new workouts.");
            return;
        }

        System.out.println("\n=== User-Defined Workouts ===");
        for (int i = 0; i < userWorkouts.size(); i++) {
            System.out.println((i + 1) + ". " + userWorkouts.get(i));
        }

        System.out.print("\nSelect a workout to view exercises (1-" + userWorkouts.size() + "), or 0 to return: ");
        int workoutChoice = scanner.nextInt();

        if (workoutChoice == 0) {
            return;  // Return to the workout menu
        }

        if (workoutChoice > 0 && workoutChoice <= userWorkouts.size()) {
            List<String> exercises = workoutManager.getExercisesForUserDefinedWorkout(workoutChoice - 1);
            boolean allCompleted = true;

            System.out.println("\n=== Exercises for " + userWorkouts.get(workoutChoice - 1) + " ===");
            for (int i = 0; i < exercises.size(); i++) {
                String status = workoutManager.getUserDefinedExerciseCompletionStatus(workoutChoice - 1, i) ? "(DONE)" : "(NOT DONE)";
                System.out.println((i + 1) + ". " + exercises.get(i) + " " + status);
                if (!workoutManager.getUserDefinedExerciseCompletionStatus(workoutChoice - 1, i)) {
                    allCompleted = false;
                }
            }

            if (allCompleted) {
                System.out.println("All exercises completed for this workout.");
            }

            System.out.print("\nSelect an exercise to complete (1-" + exercises.size() + "), or 0 to return: ");
            int exerciseChoice = scanner.nextInt();
            if (exerciseChoice == 0) {
                return;  // Return to the workout menu
            }

            if (exerciseChoice > 0 && exerciseChoice <= exercises.size()) {
                startUserDefinedExercise(workoutChoice - 1, exerciseChoice - 1, user);
                // Return to the exercises menu after completing or skipping an exercise
                showUserDefinedWorkouts(user);
            } else {
                System.out.println("Invalid selection.");
            }
        } else {
            System.out.println("Invalid selection.");
        }
    }

    // Start a user-defined exercise
    private void startUserDefinedExercise(int workoutIndex, int exerciseIndex, User user) {
        System.out.println("Starting exercise: " + workoutManager.getExercisesForUserDefinedWorkout(workoutIndex).get(exerciseIndex));

        // Ask if the user completed the exercise
        System.out.print("Have you completed this exercise? (Y/N): ");
        String completion = scanner.next();

        if (completion.equalsIgnoreCase("Y")) {
            workoutManager.setUserDefinedExerciseCompletionStatus(workoutIndex, exerciseIndex, true);
            // Award points and coins
            coinPointsManager.increasePoints(5);  // Example reward system for points
            coinPointsManager.increaseCoins(5);  // Example reward system for coins
            workoutManager.updateStreak();
            System.out.println("5 points and 5 coins earned! Keep up the good work.");
        } else {
            workoutManager.resetStreak();
            System.out.println("Try again tomorrow.");
        }
    }

    // Add or remove a user-defined workout
    private void addOrRemoveUserDefinedWorkout(User user) {
        System.out.println("\nWould you like to add or remove a user-defined workout?");
        System.out.println("1. Add");
        System.out.println("2. Remove");
        System.out.print("Enter your choice: ");
        int choice = scanner.nextInt();

        if (choice == 1) {
            addUserDefinedWorkout();
        } else if (choice == 2) {
            removeUserDefinedWorkout();
        } else {
            System.out.println("Invalid choice.");
        }
    }

    // Add a user-defined workout
    private void addUserDefinedWorkout() {
        System.out.print("\nEnter workout name: ");
        scanner.nextLine();  // Consume newline left-over
        String workoutName = scanner.nextLine();

        System.out.println("Enter exercises for the workout (e.g., Exercise 1, Exercise 2) or type 'done' to finish:");
        List<String> exercises = new ArrayList<>();
        String exercise;
        while (true) {
            exercise = scanner.nextLine();
            if (exercise.equalsIgnoreCase("done")) {
                break;
            }
            exercises.add(exercise);
        }

        workoutManager.addUserDefinedWorkout(workoutName, exercises);
        System.out.println("User-defined workout added successfully.");
    }

    // Remove a user-defined workout
    private void removeUserDefinedWorkout() {
        List<String> userWorkouts = workoutManager.getUserDefinedWorkouts();

        if (userWorkouts.isEmpty()) {
            System.out.println("No user-defined workouts to remove.");
            return;
        }

        System.out.println("\nSelect a workout to remove:");
        for (int i = 0; i < userWorkouts.size(); i++) {
            System.out.println((i + 1) + ". " + userWorkouts.get(i));
        }

        System.out.print("\nEnter the number of the workout to remove, or 0 to cancel: ");
        int choice = scanner.nextInt();

        if (choice == 0) {
            return;  // Cancel operation
        }

        if (choice > 0 && choice <= userWorkouts.size()) {
            workoutManager.removeUserDefinedWorkout(choice - 1);
            System.out.println("User-defined workout removed.");
        } else {
            System.out.println("Invalid selection.");
        }
    }
}

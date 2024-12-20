package services;

import java.util.ArrayList;
import java.util.List;
import models.User;
import utilities.DatabaseConnection;

public class WorkoutManager {
    private User user;
    private List<String> predefinedWorkouts;
    private List<List<String>> exercises;
    private boolean[][] workoutCompletionStatus;

    private List<String> userDefinedWorkouts;
    private List<List<String>> userDefinedExercises;
    private boolean[][] userDefinedCompletionStatus;


    public WorkoutManager(User user) {
        this.user = user;
        predefinedWorkouts = new ArrayList<>();
        exercises = new ArrayList<>();

        loadPredefinedWorkouts();

        workoutCompletionStatus = new boolean[predefinedWorkouts.size()][]; 
        for (int i = 0; i < exercises.size(); i++) {
            workoutCompletionStatus[i] = new boolean[exercises.get(i).size()];
        }

        userDefinedWorkouts = new ArrayList<>();
        userDefinedExercises = new ArrayList<>();
        userDefinedCompletionStatus = new boolean[10][]; // Assuming a max of 10 custom workouts
    }


    private void loadPredefinedWorkouts() {
        predefinedWorkouts.add("Strength Training");
        List<String> strengthExercises = new ArrayList<>();
        strengthExercises.add("Squat (3 sets of 10 reps)");
        strengthExercises.add("Push-up (3 sets of 15 reps)");
        strengthExercises.add("Deadlift (3 sets of 8 reps)");
        exercises.add(strengthExercises);

        predefinedWorkouts.add("Cardio");
        List<String> cardioExercises = new ArrayList<>();
        cardioExercises.add("Running (30 mins)");
        cardioExercises.add("Cycling (30 mins)");
        exercises.add(cardioExercises);

        predefinedWorkouts.add("Yoga");
        List<String> yogaExercises = new ArrayList<>();
        yogaExercises.add("Sun Salutation (5 mins)");
        yogaExercises.add("Downward Dog (3 mins)");
        exercises.add(yogaExercises);
    }


    public List<String> getPredefinedWorkouts() {
        return predefinedWorkouts;
    }


    public List<String> getExercisesForWorkout(int workoutIndex) {
        return exercises.get(workoutIndex);
    }


    public void completeExercise(int workoutIndex, int exerciseIndex) {
        if (!workoutCompletionStatus[workoutIndex][exerciseIndex]) {
            workoutCompletionStatus[workoutIndex][exerciseIndex] = true;
            user.setWorkoutsCompleted(user.getWorkoutsCompleted() + 1); // Update completed workouts in User
            System.out.println("Exercise completed! Total completed workouts: " + user.getWorkoutsCompleted());

            updateStreak();
        } else {
            System.out.println("This exercise is already completed.");
        }
    }


    public void updateStreak() {
        user.setStreak(user.getStreak() + 1); // Increment streak in User
        System.out.println("Streak updated! Current streak: " + user.getStreak());

        if (!DatabaseConnection.updateUser(user)) {
            System.out.println("Failed to update streak in database.");
        }
    }


    public void resetStreak() {
        user.setStreak(0); 
        System.out.println("Streak reset!");

        // Reset the streak in the database
        if (!DatabaseConnection.updateUser(user)) {
            System.out.println("Failed to reset streak in database.");
        }
    }


    public void viewProgress() {
        System.out.println("+------------------------------+");
        System.out.println("|        Workout Progress      |");
        System.out.println("+------------------------------+");
        System.out.println("Total Workouts Completed: " + user.getWorkoutsCompleted());
        System.out.println("Workout Streak: " + user.getStreak() + " streak");
        System.out.println("+------------------------------+");
    }


    public boolean getExerciseCompletionStatus(int workoutIndex, int exerciseIndex) {
        return workoutCompletionStatus[workoutIndex][exerciseIndex];
    }


    public void setExerciseCompletionStatus(int workoutIndex, int exerciseIndex, boolean status) {
        workoutCompletionStatus[workoutIndex][exerciseIndex] = status;
    }


    public void addUserDefinedWorkout(String workoutName, List<String> exercisesList) {
        userDefinedWorkouts.add(workoutName);
        userDefinedExercises.add(exercisesList);

        userDefinedCompletionStatus[userDefinedWorkouts.size() - 1] = new boolean[exercisesList.size()];
    }


    public void removeUserDefinedWorkout(int workoutIndex) {
        userDefinedWorkouts.remove(workoutIndex);
        userDefinedExercises.remove(workoutIndex);
        for (int i = workoutIndex; i < userDefinedWorkouts.size(); i++) {
            userDefinedCompletionStatus[i] = userDefinedCompletionStatus[i + 1];
        }
        userDefinedCompletionStatus[userDefinedWorkouts.size()] = null;
    }


    public List<String> getUserDefinedWorkouts() {
        return userDefinedWorkouts;
    }


    public List<String> getExercisesForUserDefinedWorkout(int workoutIndex) {
        return userDefinedExercises.get(workoutIndex);
    }


    public boolean getUserDefinedExerciseCompletionStatus(int workoutIndex, int exerciseIndex) {
        return userDefinedCompletionStatus[workoutIndex][exerciseIndex];
    }

    
    public void setUserDefinedExerciseCompletionStatus(int workoutIndex, int exerciseIndex, boolean status) {
        userDefinedCompletionStatus[workoutIndex][exerciseIndex] = status;
    }
}

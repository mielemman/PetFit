package services;

import java.util.ArrayList;
import java.util.List;

public class WorkoutManager {
    private List<String> predefinedWorkouts;
    private List<List<String>> exercises;
    private boolean[][] workoutCompletionStatus;
    private int completedWorkouts;
    private int streakCount; 

    private List<String> userDefinedWorkouts;  
    private List<List<String>> userDefinedExercises;  
    private boolean[][] userDefinedCompletionStatus;  

    
    public WorkoutManager() {
        predefinedWorkouts = new ArrayList<>();
        exercises = new ArrayList<>();
        completedWorkouts = 0;
        streakCount = 0;

        
        loadPredefinedWorkouts();

        
        workoutCompletionStatus = new boolean[predefinedWorkouts.size()][]; 
        for (int i = 0; i < exercises.size(); i++) {
            workoutCompletionStatus[i] = new boolean[exercises.get(i).size()];
        }

        
        userDefinedWorkouts = new ArrayList<>();
        userDefinedExercises = new ArrayList<>();
        userDefinedCompletionStatus = new boolean[10][];
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
        workoutCompletionStatus[workoutIndex][exerciseIndex] = true;
        completedWorkouts++;
    }


    public void updateStreak() {
        streakCount++;
    }

    public void resetStreak() {
        streakCount = 0;
    }


    public int getStreakCount() {
        return streakCount;
    }


    public void viewProgress() {
        System.out.println("=== Workout Progress ===");
        System.out.println("Workout Streak: " + streakCount + " days");
        System.out.println("Total Workouts Completed: " + completedWorkouts);
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




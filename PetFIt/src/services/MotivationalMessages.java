package services;

import java.util.Random;

public class MotivationalMessages {

    // Array of predefined motivational messages
    private static final String[] MESSAGES = {
        "Great job on completing your workout! Keep pushing forward!",
        "Consistency is key. You're doing amazing!",
        "Every step you take gets you closer to your goal. Keep it up!",
        "Your hard work is paying off. Stay strong!",
        "You’ve got this! Believe in yourself and keep going!",
        "One day at a time, one workout at a time. Excellent work!",
        "You're building habits that will last a lifetime. Fantastic effort!",
        "Sweat now, shine later. Keep up the awesome work!",
        "Your streak is impressive! Keep the momentum going!",
        "You're unstoppable! Another milestone crushed!"
    };

    public static String getRandomMessage() {
        Random random = new Random();
        int index = random.nextInt(MESSAGES.length);
        return MESSAGES[index];
    }

    public static String getStreakMessage(int streak) {
        if (streak == 0) {
            return "No worries! Every day is a fresh start. Get back on track today!";
        } else if (streak > 0 && streak <= 5) {
            return "You're on a roll! Keep building that streak!";
        } else if (streak > 5 && streak <= 10) {
            return "Amazing! You're unstoppable with this streak!";
        } else {
            return "Legendary streak! Keep inspiring yourself and others!";
        }
    }
}

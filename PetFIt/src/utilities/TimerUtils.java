package utilities;

import java.time.Duration;
import java.time.LocalDateTime;

public class TimerUtils {

    private static final int HOURS_BEFORE_DECAY = 3;
    private static final int HOURS_BEFORE_RESET = 24;

    private LocalDateTime lastDecayCheck;
    private LocalDateTime lastDailyReset;

    public TimerUtils() {
        this.lastDecayCheck = LocalDateTime.now();
        this.lastDailyReset = LocalDateTime.now();
    }

    public boolean isTimeForStatDecay() {
        Duration durationSinceLastDecay = Duration.between(lastDecayCheck, LocalDateTime.now());
        return durationSinceLastDecay.toHours() >= HOURS_BEFORE_DECAY;
    }

    public void updateDecayTimestamp() {
        this.lastDecayCheck = LocalDateTime.now();
    }

    public boolean isTimeForDailyReset() {
        Duration durationSinceLastReset = Duration.between(lastDailyReset, LocalDateTime.now());
        return durationSinceLastReset.toHours() >= HOURS_BEFORE_RESET;
    }

    public void updateDailyResetTimestamp() {
        this.lastDailyReset = LocalDateTime.now();
    }
    public long hoursSince(LocalDateTime timestamp) {
        Duration duration = Duration.between(timestamp, LocalDateTime.now());
        return duration.toHours();
    }

    public long minutesSince(LocalDateTime timestamp) {
        Duration duration = Duration.between(timestamp, LocalDateTime.now());
        return duration.toMinutes();
    }
}

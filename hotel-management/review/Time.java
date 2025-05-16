
import java.time.Duration;
import java.time.LocalDateTime;

public class Time {
    private LocalDateTime startTime;
    private LocalDateTime endTime;

    public void start() {
        startTime = LocalDateTime.now();
    }

    public void stop() {
        endTime = LocalDateTime.now();
    }

    public long getElapsedSeconds() {
        if (startTime != null && endTime != null) {
            return Duration.between(startTime, endTime).getSeconds();
        }
        return 0;
    }

    public void printElapsedTime() {
        System.out.println("Elapsed time (s): " + getElapsedSeconds());
    }
}


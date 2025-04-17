// Timer.java
import java.util.Timer;
import java.util.TimerTask;

public class BoiledEggTimer {
    private Timer timer;

    public void start(int timeInSeconds) {
        if (timer != null) {
            timer.cancel(); // Cancel any existing timer
        }

        timer = new Timer();
        System.out.println("Timer started for " + timeInSeconds / 60 + " minutes...");

        TimerTask countdown = new TimerTask() {
            int secondsLeft = timeInSeconds;

            @Override
            public void run() {
                if (secondsLeft > 0) {
                    int minutes = secondsLeft / 60;
                    int seconds = secondsLeft % 60;
                    System.out.printf("Time left: %02d:%02d\r", minutes, seconds);
                    secondsLeft--;
                } else {
                    System.out.println("\nDing! Your egg is ready!");
                    timer.cancel();
                }
            }
        };

        timer.scheduleAtFixedRate(countdown, 0, 1000);
    }
}

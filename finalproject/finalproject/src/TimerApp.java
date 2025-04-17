// console version
import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;

public class TimerApp {
    private static final int SOFT_BOIL_TIME = 300;   // 5 minutes
    private static final int MEDIUM_BOIL_TIME = 420; // 7 minutes
    private static final int HARD_BOIL_TIME = 600;   // 10 minutes

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("=== Boiled Egg Timer ===");
        System.out.println("Choose your egg preference:");
        System.out.println("1. Soft Boiled (5 minutes)");
        System.out.println("2. Medium Boiled (7 minutes)");
        System.out.println("3. Hard Boiled (10 minutes)");
        System.out.print("Enter choice (1-3): ");

        int choice = scanner.nextInt();
        int timeInSeconds;

        switch (choice) {
            case 1:
                timeInSeconds = SOFT_BOIL_TIME;
                break;
            case 2:
                timeInSeconds = MEDIUM_BOIL_TIME;
                break;
            case 3:
                timeInSeconds = HARD_BOIL_TIME;
                break;
            default:
                System.out.println("Invalid choice. Defaulting to Medium Boiled.");
                timeInSeconds = MEDIUM_BOIL_TIME;
        }

        System.out.println("Timer started for " + timeInSeconds / 60 + " minutes...");

        Timer timer = new Timer();
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

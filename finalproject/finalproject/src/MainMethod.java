import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import java.util.Timer;
import java.util.TimerTask;

public class App extends Application {
    private Label timerLabel;
    private Button startButton;
    private Button stopButton;
    private ComboBox<String> eggTypeComboBox;
    private ProgressBar progressBar;
    private Timer timer;
    private boolean timerRunning = false;

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Boiled Egg Timer");
        
        // Create GUI components
        Label titleLabel = new Label("Boiled Egg Timer");
        titleLabel.setFont(Font.font("Arial", FontWeight.BOLD, 20));
        
        Label selectLabel = new Label("Select egg type:");
        
        eggTypeComboBox = new ComboBox<>();
        eggTypeComboBox.getItems().addAll("soft", "medium", "hard");
        eggTypeComboBox.setValue("medium");
        
        timerLabel = new Label("00:00");
        timerLabel.setFont(Font.font("Arial", FontWeight.BOLD, 36));
        
        progressBar = new ProgressBar(0);
        progressBar.setPrefWidth(300);
        
        // Create buttons
        startButton = new Button("Start Timer");
        startButton.setPrefWidth(120);
        startButton.setOnAction(e -> startTimer());
        
        stopButton = new Button("Stop Timer");
        stopButton.setPrefWidth(120);
        stopButton.setDisable(true);
        stopButton.setOnAction(e -> stopTimer());
        
        // Create button layout
        HBox buttonBox = new HBox(20);
        buttonBox.setAlignment(Pos.CENTER);
        buttonBox.getChildren().addAll(startButton, stopButton);
        
        // Create main layout
        VBox root = new VBox(15);
        root.setPadding(new Insets(20));
        root.setAlignment(Pos.CENTER);
        root.getChildren().addAll(
            titleLabel,
            selectLabel,
            eggTypeComboBox,
            timerLabel,
            progressBar,
            buttonBox
        );
        
        Scene scene = new Scene(root, 350, 300);
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();
        
        // Handle application close
        primaryStage.setOnCloseRequest(e -> {
            if (timer != null) {
                timer.cancel();
            }
        });
    }
    
    private void startTimer() {
        if (timerRunning) {
            return;
        }
        
        // Get selected egg type
        String eggType = eggTypeComboBox.getValue();
        BoiledEgg egg = new BoiledEgg(eggType);
        
        // Convert minutes to seconds
        int timeInSeconds = egg.getBoilTime() * 60;
        int totalSeconds = timeInSeconds;
        
        // Update UI
        startButton.setDisable(true);
        stopButton.setDisable(false);
        eggTypeComboBox.setDisable(true);
        timerRunning = true;
        
        // Start timer
        timer = new Timer();
        TimerTask countdown = new TimerTask() {
            int secondsLeft = timeInSeconds;
            
            @Override
            public void run() {
                if (secondsLeft >= 0) {
                    int minutes = secondsLeft / 60;
                    int seconds = secondsLeft % 60;
                    double progress = 1.0 - ((double) secondsLeft / totalSeconds);
                    
                    Platform.runLater(() -> {
                        timerLabel.setText(String.format("%02d:%02d", minutes, seconds));
                        progressBar.setProgress(progress);
                    });
                    
                    secondsLeft--;
                } else {
                    Platform.runLater(() -> {
                        timerLabel.setText("Done!");
                        resetUI();
                        showNotification(primaryStage -> {
                            Stage alert = new Stage();
                            alert.initOwner(primaryStage);
                            
                            Label alertLabel = new Label("Ding! Your " + eggType + " boiled egg is ready!");
                            alertLabel.setFont(Font.font("Arial", FontWeight.BOLD, 14));
                            
                            Button okButton = new Button("OK");
                            okButton.setOnAction(evt -> alert.close());
                            
                            VBox alertBox = new VBox(20);
                            alertBox.setAlignment(Pos.CENTER);
                            alertBox.setPadding(new Insets(20));
                            alertBox.getChildren().addAll(alertLabel, okButton);
                            
                            Scene alertScene = new Scene(alertBox, 300, 150);
                            alert.setScene(alertScene);
                            alert.setTitle("Egg Timer");
                            alert.show();
                        });
                    });
                    
                    timer.cancel();
                }
            }
        };
        
        timer.scheduleAtFixedRate(countdown, 0, 1000);
    }
    
    private void stopTimer() {
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
        resetUI();
    }
    
    private void resetUI() {
        timerRunning = false;
        startButton.setDisable(false);
        stopButton.setDisable(true);
        eggTypeComboBox.setDisable(false);
        progressBar.setProgress(0);
    }
    
    private interface NotificationCallback {
        void call(Stage primaryStage);
    }
    
    private void showNotification(NotificationCallback callback) {
        callback.call(this.getStage());
    }
    
    private Stage getStage() {
        return (Stage) timerLabel.getScene().getWindow();
    }
    
    public static void main(String[] args) {
        launch(args);
    }
}

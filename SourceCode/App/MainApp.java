import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import View.MainMenuView;
import Controller.MainController;

public class MainApp extends Application {
    @Override
    public void start(Stage primaryStage) {
        MainMenuView mainMenuView = new MainMenuView();
        MainController mainController = new MainController(mainMenuView, primaryStage);

        Scene scene = new Scene(mainMenuView.getRoot(), 800, 600);
        primaryStage.setTitle("CPU Scheduling Simulator");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}

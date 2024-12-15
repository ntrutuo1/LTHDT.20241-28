package App;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import Controller.MainController;
import View.MainMenuView;
import Model.Scheduler;

public class MainApp extends Application {
    @SuppressWarnings("exports")
	@Override
    public void start(Stage primaryStage) {
        // Use the default process list
        var defaultProcessList = Scheduler.createDefaultProcessList();

        MainMenuView mainMenuView = new MainMenuView();
        new MainController(mainMenuView, primaryStage, defaultProcessList);

        Scene scene = new Scene(mainMenuView.getRoot(), 800, 600);
        primaryStage.setTitle("CPU Scheduling Simulator");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}

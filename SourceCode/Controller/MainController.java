package Controller;

import View.MainMenuView;
import View.SimulationView;
import View.HelpView;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import Model.Process;

import java.util.List;

public class MainController {
    private Stage primaryStage;
    private List<Process> defaultProcessList;

    @SuppressWarnings("exports")
	public MainController(MainMenuView menuView, Stage primaryStage, List<Process> defaultProcessList) {
        this.primaryStage = primaryStage;
        this.defaultProcessList = defaultProcessList;

        // Set up event handlers
        menuView.getFCFSButton().setOnAction(e -> openSimulationView("FCFS"));
        menuView.getSJNButton().setOnAction(e -> openSimulationView("SJN"));
        menuView.getRRButton().setOnAction(e -> openSimulationView("RR"));
        menuView.getHelpButton().setOnAction(e -> showHelp());
        menuView.getExitButton().setOnAction(e -> exitApplication());
    }

    private void openSimulationView(String algorithm) {
        SimulationView simulationView = new SimulationView(defaultProcessList, algorithm);
        new SchedulerController(simulationView, algorithm, primaryStage);  // Attach the scheduler controller
        Scene simulationScene = new Scene(simulationView.getRoot(), 800, 600);
        primaryStage.setScene(simulationScene);
    }

    private void showHelp() {
        HelpView helpView = new HelpView();
        helpView.showHelpInfo();
    }

    private void exitApplication() {
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Exit Confirmation");
        alert.setHeaderText("Are you sure you want to exit?");
        alert.setContentText("Press OK to exit.");

        alert.showAndWait().ifPresent(response -> {
            if (response == javafx.scene.control.ButtonType.OK) {
                Platform.exit();
            }
        });
    }
}

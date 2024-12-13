package Controller;

import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.GridPane;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;

import Model.Process;
import Model.Scheduler;
import Model.FCFSScheduler;
import Model.SJNScheduler;
import Model.RRScheduler;
import Model.GanttEntry;
import View.SimulationView;
import View.HelpView;
import View.MainMenuView;
import Controller.MainController;

import java.util.ArrayList;
import java.util.List;

public class SchedulerController {
    private SimulationView simulationView;
    private Scheduler scheduler;
    private String algorithm;
    private Stage primaryStage;

    public SchedulerController(SimulationView simulationView, String algorithm, Stage primaryStage) {
        this.simulationView = simulationView;
        this.algorithm = algorithm;
        this.primaryStage = primaryStage;

        setupEventHandlers();
    }

    private void setupEventHandlers() {
        simulationView.getAddProcessButton().setOnAction(e -> addProcessDialog());
        simulationView.getRunSimulationButton().setOnAction(e -> runSimulation());
        simulationView.getBackButton().setOnAction(e -> goBackToMenu());
    }

    private void addProcessDialog() {
        Dialog<Process> dialog = new Dialog<>();
        dialog.setTitle("Add Process");
        dialog.setHeaderText("Enter Process Details");

        ButtonType addButtonType = new ButtonType("Add", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(addButtonType, ButtonType.CANCEL);

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));

        TextField idField = new TextField();
        idField.setPromptText("Process ID");
        TextField arrivalField = new TextField();
        arrivalField.setPromptText("Arrival Time");
        TextField burstField = new TextField();
        burstField.setPromptText("Burst Time");
        TextField priorityField = new TextField();
        priorityField.setPromptText("Priority (optional)");

        grid.add(new Label("Process ID:"), 0, 0);
        grid.add(idField, 1, 0);
        grid.add(new Label("Arrival Time:"), 0, 1);
        grid.add(arrivalField, 1, 1);
        grid.add(new Label("Burst Time:"), 0, 2);
        grid.add(burstField, 1, 2);
        if (algorithm.equals("SJN")) {
            grid.add(new Label("Priority:"), 0, 3);
            grid.add(priorityField, 1, 3);
        }

        dialog.getDialogPane().setContent(grid);

        // Enable/Disable add button depending on input
        Node addButton = dialog.getDialogPane().lookupButton(addButtonType);
        addButton.setDisable(true);

        // Validate inputs
        idField.textProperty().addListener((observable, oldValue, newValue) -> {
            validateInputs(idField, arrivalField, burstField, addButton);
        });

        arrivalField.textProperty().addListener((observable, oldValue, newValue) -> {
            validateInputs(idField, arrivalField, burstField, addButton);
        });

        burstField.textProperty().addListener((observable, oldValue, newValue) -> {
            validateInputs(idField, arrivalField, burstField, addButton);
        });

        if (algorithm.equals("SJN")) {
            priorityField.textProperty().addListener((observable, oldValue, newValue) -> {
                // Priority is optional, so don't affect validation
            });
        }

        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == addButtonType) {
                int id = Integer.parseInt(idField.getText());
                int arrival = Integer.parseInt(arrivalField.getText());
                int burst = Integer.parseInt(burstField.getText());
                int priority = 0;
                if (algorithm.equals("SJN") && !priorityField.getText().isEmpty()) {
                    priority = Integer.parseInt(priorityField.getText());
                }
                return new Process(id, arrival, burst, priority);
            }
            return null;
        });

        dialog.showAndWait().ifPresent(process -> {
            simulationView.getProcessData().add(process);
        });
    }

    private void validateInputs(TextField idField, TextField arrivalField, TextField burstField, Node addButton) {
        boolean disable = !isValidInteger(idField.getText()) ||
                          !isValidInteger(arrivalField.getText()) ||
                          !isValidInteger(burstField.getText());
        addButton.setDisable(disable);
    }

    private boolean isValidInteger(String text) {
        if (text == null || text.isEmpty()) return false;
        try {
            Integer.parseInt(text);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private void runSimulation() {
        ObservableList<Process> processList = simulationView.getProcessData();
        if (processList.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("No Processes");
            alert.setHeaderText("No processes to simulate.");
            alert.setContentText("Please add at least one process.");
            alert.showAndWait();
            return;
        }

        // Convert ObservableList to List
        List<Process> processes = new ArrayList<>();
        for (Process p : processList) {
            // Clone to avoid modifying original
            processes.add(new Process(p.getProcessId(), p.getArrivalTime(), p.getBurstTime(), p.getPriority()));
        }

        // Initialize scheduler
        switch (algorithm) {
            case "FCFS":
                scheduler = new FCFSScheduler(processes);
                break;
            case "SJN":
                scheduler = new SJNScheduler(processes);
                break;
            case "RR":
                // For RR, need to get quantum from user
                int quantum = getQuantumFromUser();
                if (quantum <= 0) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Invalid Quantum");
                    alert.setHeaderText("Quantum must be a positive integer.");
                    alert.showAndWait();
                    return;
                }
                scheduler = new RRScheduler(processes, quantum);
                break;
            default:
                scheduler = new FCFSScheduler(processes);
        }

        // Calculate metrics and generate Gantt chart
        scheduler.calculateMetrics();
        List<GanttEntry> ganttChart = scheduler.generateGanttChart();

        // Update view
        simulationView.displayProcesses(scheduler.getProcessList());
        simulationView.updateGanttChart(ganttChart);

        // Calculate metrics
        double totalWaiting = 0;
        double totalTurnaround = 0;
        for (Process p : scheduler.getProcessList()) {
            totalWaiting += p.getWaitingTime();
            totalTurnaround += p.getTurnaroundTime();
        }
        double avgWaiting = totalWaiting / scheduler.getProcessList().size();
        double avgTurnaround = totalTurnaround / scheduler.getProcessList().size();

        // CPU Utilization: total burst time / (last end time)
        int totalBurst = 0;
        int lastEndTime = 0;
        for (GanttEntry entry : ganttChart) {
            if (entry.getProcessId() != -1) {
                totalBurst += (entry.getEndTime() - entry.getStartTime());
            }
            if (entry.getEndTime() > lastEndTime) {
                lastEndTime = entry.getEndTime();
            }
        }
        double cpuUtil = ((double) totalBurst / lastEndTime) * 100;

        simulationView.displayMetrics(avgWaiting, avgTurnaround, cpuUtil);
    }

    private int getQuantumFromUser() {
        TextInputDialog dialog = new TextInputDialog("4");
        dialog.setTitle("Round Robin Quantum");
        dialog.setHeaderText("Enter the time quantum for Round Robin scheduling:");
        dialog.setContentText("Quantum:");

        dialog.showAndWait();

        String input = dialog.getResult();
        if (input == null || input.isEmpty()) return -1;
        try {
            int quantum = Integer.parseInt(input);
            return quantum;
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    private void goBackToMenu() {
        MainMenuView mainMenuView = new MainMenuView();
        MainController mainController = new MainController(mainMenuView, primaryStage);
        Scene menuScene = new Scene(mainMenuView.getRoot(), 800, 600);
        primaryStage.setScene(menuScene);
    }
}

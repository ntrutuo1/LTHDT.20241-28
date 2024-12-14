package View;

import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import Model.Process;
import Model.GanttEntry;

public class SimulationView {
    private BorderPane root;
    private TableView<Process> processTable;
    private ObservableList<Process> processData;

    private Button addProcessButton;
    private Button runSimulationButton;
    private Button backButton;

    private HBox ganttChartBox;
    private Label metricsLabel;

    public SimulationView(List<Process> processList, String algorithm) {
        root = new BorderPane();

        // Top: Title
        Label title = new Label("Simulation - " + algorithm + " Scheduler");
        title.setFont(Font.font(24));
        title.setPadding(new Insets(10));
        title.setTextAlignment(TextAlignment.CENTER);
        root.setTop(title);
        BorderPane.setAlignment(title, Pos.CENTER);

        // Left: Process Table
        processTable = new TableView<>();
        processData = FXCollections.observableArrayList(processList);

        TableColumn<Process, Integer> idCol = new TableColumn<>("Process ID");
        idCol.setCellValueFactory(new PropertyValueFactory<>("processId"));

        TableColumn<Process, Number> arrivalCol = new TableColumn<>("Arrival Time");
        arrivalCol.setCellValueFactory(new PropertyValueFactory<>("arrivalTime"));

        TableColumn<Process, Number> burstCol = new TableColumn<>("Burst Time");
        burstCol.setCellValueFactory(new PropertyValueFactory<>("burstTime"));

        TableColumn<Process, Number> priorityCol = new TableColumn<>("Priority");
        priorityCol.setCellValueFactory(new PropertyValueFactory<>("priority"));

        processTable.getColumns().addAll(idCol, arrivalCol, burstCol, priorityCol);
        processTable.setItems(processData);
        processTable.setPrefWidth(400);

        addProcessButton = new Button("Add Process");
        runSimulationButton = new Button("Run Simulation");
        backButton = new Button("Back to Menu");

        HBox buttonBox = new HBox(10, addProcessButton, runSimulationButton, backButton);
        buttonBox.setPadding(new Insets(10));
        buttonBox.setAlignment(Pos.CENTER);

        VBox leftBox = new VBox(10, processTable, buttonBox);
        leftBox.setPadding(new Insets(10));

        root.setLeft(leftBox);

        // Center: Gantt Chart
        ganttChartBox = new HBox(5);
        ScrollPane ganttScroll = new ScrollPane(ganttChartBox);
        ganttScroll.setFitToHeight(true);
        ganttScroll.setPrefHeight(200);
        ganttChartBox.setPadding(new Insets(10));
        ganttChartBox.setStyle("-fx-border-color: black; -fx-background-color: white;");

        root.setCenter(ganttScroll);

        // Bottom: Metrics
        metricsLabel = new Label("Metrics will be displayed here.");
        metricsLabel.setFont(Font.font(14));
        metricsLabel.setPadding(new Insets(10));
        root.setBottom(metricsLabel);
    }

    public BorderPane getRoot() {
        return root;
    }

    public ObservableList<Process> getProcessData() {
        return processData;
    }

    public TableView<Process> getProcessTable() {
        return processTable;
    }

    public Button getAddProcessButton() {
        return addProcessButton;
    }

    public Button getRunSimulationButton() {
        return runSimulationButton;
    }

    public Button getBackButton() {
        return backButton;
    }

    public HBox getGanttChartBox() {
        return ganttChartBox;
    }

    public Label getMetricsLabel() {
        return metricsLabel;
    }

    public void displayProcesses(List<Process> processes) {
        processData.clear();
        processData.addAll(processes);
    }

    public void updateGanttChart(List<GanttEntry> ganttEntries) {
        ganttChartBox.getChildren().clear();
        for (GanttEntry entry : ganttEntries) {
            Label lbl;
            if (entry.getProcessId() == -1) {
                // Idle time
                lbl = new Label("Idle");
                lbl.setStyle("-fx-border-color: black; -fx-padding: 5px; -fx-background-color: lightgray;");
            } else {
                lbl = new Label("P" + entry.getProcessId());
                lbl.setStyle("-fx-border-color: black; -fx-padding: 5px; -fx-background-color: lightblue;");
            }
            ganttChartBox.getChildren().add(lbl);
        }
    }

    public void displayMetrics(double avgWait, double avgTurn, double cpuUtil) {
        metricsLabel.setText(String.format(
            "Average Waiting Time: %.2f\nAverage Turnaround Time: %.2f\nCPU Utilization: %.2f%%",
            avgWait, avgTurn, cpuUtil
        ));
    }
}

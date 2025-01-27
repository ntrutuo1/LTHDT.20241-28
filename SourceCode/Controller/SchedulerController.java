package Controller;

import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.geometry.Insets;
import javafx.scene.layout.GridPane;
import javafx.collections.ObservableList;
import javafx.scene.Node;

import Model.Process;
import Model.Scheduler;
import Model.FCFSScheduler;
import Model.SJNScheduler;
import Model.RRScheduler;
import Model.GanttEntry;
import View.SimulationView;
import View.MainMenuView;

import java.util.ArrayList;
import java.util.List;

public class SchedulerController {
    private SimulationView simulationView;
    private Scheduler scheduler;
    private String algorithm;
    private Stage primaryStage;

    /**
     * Constructor cho SchedulerController.
     * @param simulationView View mô phỏng lịch trình.
     * @param algorithm Thuật toán lập lịch (FCFS, SJN, RR).
     * @param primaryStage Stage chính của ứng dụng.
     */
    @SuppressWarnings("exports")
    public SchedulerController(SimulationView simulationView, String algorithm, Stage primaryStage) {
        this.simulationView = simulationView;
        this.algorithm = algorithm;
        this.primaryStage = primaryStage;

        setupEventHandlers();
    }

    /**
     * Thiết lập các sự kiện cho các nút trong SimulationView.
     */
    private void setupEventHandlers() {
        simulationView.getAddProcessButton().setOnAction(e -> addProcessDialog());
        simulationView.getRunSimulationButton().setOnAction(e -> runSimulation());
        simulationView.getBackButton().setOnAction(e -> goBackToMenu());
    }

    /**
     * Hiển thị hộp thoại để thêm một tiến trình mới.
     */
    /**
     * Kiểm tra đầu vào và bật/tắt nút Add.
     */
    private void validateInputs(TextField idField, TextField arrivalField, TextField burstField, Node addButton) {
        boolean disable = !isValidDouble(idField.getText()) ||
                          !isValidDouble(arrivalField.getText()) ||
                          !isValidDouble(burstField.getText());
        addButton.setDisable(disable);
    }

    private void addProcessDialog() {
        // Tạo hộp thoại
        Dialog<Process> dialog = new Dialog<>();
        dialog.setTitle("Add Process");
        dialog.setHeaderText("Enter Process Details");
    
        // Thêm các nút "Add" và "Cancel"
        ButtonType addButtonType = new ButtonType("Add", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(addButtonType, ButtonType.CANCEL);
    
        // Tạo giao diện nhập liệu
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));
    
        // Các trường nhập liệu cho Process ID, Arrival Time, Burst Time và Priority
        TextField idField = new TextField();
        idField.setPromptText("Process ID");
        TextField arrivalField = new TextField();
        arrivalField.setPromptText("Arrival Time");
        TextField burstField = new TextField();
        burstField.setPromptText("Burst Time");
        TextField priorityField = new TextField();
        priorityField.setPromptText("Priority (optional)");
    
        // Thêm các trường vào grid
        grid.add(new Label("Process ID:"), 0, 0);
        grid.add(idField, 1, 0);
        grid.add(new Label("Arrival Time:"), 0, 1);
        grid.add(arrivalField, 1, 1);
        grid.add(new Label("Burst Time:"), 0, 2);
        grid.add(burstField, 1, 2);
    
        // Nếu đang ở thuật toán SJN, hiển thị field Priority
        if (algorithm.equals("SJN")) {
            grid.add(new Label("Priority:"), 0, 3);
            grid.add(priorityField, 1, 3);
        }
    
        dialog.getDialogPane().setContent(grid);
    
        // Vô hiệu hóa nút Add khi dữ liệu chưa hợp lệ
        Node addButton = dialog.getDialogPane().lookupButton(addButtonType);
        addButton.setDisable(true);
    
        // Thêm bộ lắng nghe để kiểm tra dữ liệu đầu vào
        idField.textProperty().addListener((observable, oldValue, newValue) -> {
            validateInputs(idField, arrivalField, burstField, addButton);
        });
        arrivalField.textProperty().addListener((observable, oldValue, newValue) -> {
            validateInputs(idField, arrivalField, burstField, addButton);
        });
        burstField.textProperty().addListener((observable, oldValue, newValue) -> {
            validateInputs(idField, arrivalField, burstField, addButton);
        });
    
        // Xử lý kết quả khi nhấn "Add"
        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == addButtonType) {
                try {
                    int id = Integer.parseInt(idField.getText());
    
                    // Kiểm tra trùng Process ID
                    for (Process existingProcess : simulationView.getProcessData()) {
                        if (existingProcess.getProcessId() == id) {
                            Alert alert = new Alert(Alert.AlertType.ERROR);
                            alert.setTitle("Duplicate ID");
                            alert.setHeaderText("Process ID " + id + " already exists.");
                            alert.showAndWait();
                            // Nếu trùng ID thì không tạo Process mới
                            return null;
                        }
                    }
    
                    // Cho phép nhập số thực cho arrival và burst
                    double arrival = Double.parseDouble(arrivalField.getText());
                    double burst = Double.parseDouble(burstField.getText());
    
                    int priority = 0;
                    if (algorithm.equals("SJN") && !priorityField.getText().isEmpty()) {
                        priority = Integer.parseInt(priorityField.getText());
                    }
    
                    return new Process(id, arrival, burst, priority);
    
                } catch (NumberFormatException ex) {
                    // Nếu parse thất bại vì dữ liệu không hợp lệ
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Invalid Input");
                    alert.setHeaderText("Please enter valid numbers.");
                    alert.showAndWait();
                    return null;
                }
            }
            return null;
        });
    
        // Sau khi show, nếu người dùng nhấn Add thì process (nếu khác null) sẽ được thêm
        dialog.showAndWait().ifPresent(process -> {
            simulationView.getProcessData().add(process);
        });
    }
    

    /**
     * Xác định một chuỗi có phải là số hợp lệ hay không.
     */
    private boolean isValidDouble(String text) {
        if (text == null || text.isEmpty()) return false;
        try {
            Double.parseDouble(text);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    /**
     * Chạy mô phỏng thuật toán lập lịch.
     */
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

        List<Process> processes = new ArrayList<>();
        for (Process p : processList) {
            processes.add(new Process(p.getProcessId(), p.getArrivalTime(), p.getBurstTime(), p.getPriority()));
        }

        // Xác định thuật toán lập lịch
        switch (algorithm) {
            case "FCFS":
                scheduler = new FCFSScheduler(processes);
                break;
            case "SJN":
                scheduler = new SJNScheduler(processes);
                break;
            case "RR":
                double quantum = getQuantumFromUser();
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

        scheduler.calculateMetrics();
        List<GanttEntry> ganttChart = scheduler.generateGanttChart();

        simulationView.displayProcesses(scheduler.getProcessList());
        simulationView.updateGanttChart(ganttChart);

        double totalWaiting = 0, totalTurnaround = 0, totalBurst = 0, lastEndTime = 0;
        for (Process p : scheduler.getProcessList()) {
            totalWaiting += p.getWaitingTime();
            totalTurnaround += p.getTurnaroundTime();
        }

        for (GanttEntry entry : ganttChart) {
            if (entry.getProcessId() != -1) totalBurst += (entry.getEndTime() - entry.getStartTime());
            if (entry.getEndTime() > lastEndTime) lastEndTime = entry.getEndTime();
        }

        simulationView.displayMetrics(totalWaiting / scheduler.getProcessList().size(),
                                      totalTurnaround / scheduler.getProcessList().size(),
                                      (totalBurst / lastEndTime) * 100);
    }

    /**
     * Lấy giá trị quantum từ người dùng cho thuật toán Round Robin.
     */
    private double getQuantumFromUser() {
        TextInputDialog dialog = new TextInputDialog("4");
        dialog.setTitle("Round Robin Quantum");
        dialog.setHeaderText("Enter the time quantum for Round Robin scheduling:");
        dialog.setContentText("Quantum:");

        dialog.showAndWait();

        String input = dialog.getResult();
        if (input == null || input.isEmpty()) return -1;
        try {
            return Double.parseDouble(input);
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    /**
     * Quay trở lại màn hình menu chính.
     */
    private void goBackToMenu() {
        var defaultProcessList = Scheduler.createDefaultProcessList();

        MainMenuView mainMenuView = new MainMenuView();
        new MainController(mainMenuView, primaryStage, defaultProcessList);
        Scene menuScene = new Scene(mainMenuView.getRoot(), 800, 600);
        primaryStage.setScene(menuScene);
    }
}

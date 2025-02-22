package View;

import java.util.List;

import javafx.application.Platform;
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

/**
 * Lớp SimulationView tạo giao diện cho mô phỏng các thuật toán lập lịch CPU.
 * Giao diện bao gồm bảng tiến trình, biểu đồ Gantt và khu vực hiển thị các chỉ số.
 */
public class SimulationView {
    private BorderPane root;                   // Layout chính sử dụng BorderPane.
    private TableView<Process> processTable;   // Bảng hiển thị danh sách tiến trình.
    private ObservableList<Process> processData; // Dữ liệu cho bảng tiến trình.

    private Button addProcessButton;           // Nút để thêm tiến trình mới.
    private Button runSimulationButton;        // Nút để chạy mô phỏng.
    private Button backButton;                 // Nút quay lại menu chính.

    private FlowPane ganttChartPane;           // Biểu đồ Gantt sử dụng FlowPane.
    private Label metricsLabel;                // Nhãn hiển thị các chỉ số (thời gian chờ, turnaround, CPU sử dụng).

    /**
     * Constructor khởi tạo giao diện mô phỏng.
     * Thiết lập bố cục, bảng tiến trình, biểu đồ Gantt và các chỉ số.
     *
     * @param processList Danh sách tiến trình ban đầu.
     * @param algorithm   Tên của thuật toán lập lịch đang được mô phỏng.
     */
    @SuppressWarnings("unchecked")
    public SimulationView(List<Process> processList, String algorithm) {
        root = new BorderPane(); // Sử dụng BorderPane làm layout chính.

        // ======= Phần Tiêu Đề (Top) =======
        Label title = new Label("Simulation - " + algorithm + " Scheduler");
        title.setFont(Font.font(24));               // Cỡ chữ tiêu đề.
        title.setPadding(new Insets(10));           // Khoảng cách xung quanh tiêu đề.
        title.setTextAlignment(TextAlignment.CENTER); // Căn giữa văn bản.
        root.setTop(title);                         // Đặt tiêu đề ở phần Top.
        BorderPane.setAlignment(title, Pos.CENTER); // Canh giữa tiêu đề.

        // ======= Phần Bảng Tiến Trình (Left) =======
        processTable = new TableView<>();                // Tạo bảng để hiển thị tiến trình.
        processData = FXCollections.observableArrayList(processList);

        // Tạo các cột cho bảng.
        TableColumn<Process, Integer> idCol = new TableColumn<>("Process ID");
        idCol.setCellValueFactory(new PropertyValueFactory<>("processId"));

        TableColumn<Process, Number> arrivalCol = new TableColumn<>("Arrival Time");
        arrivalCol.setCellValueFactory(new PropertyValueFactory<>("arrivalTime"));

        TableColumn<Process, Number> burstCol = new TableColumn<>("Burst Time");
        burstCol.setCellValueFactory(new PropertyValueFactory<>("burstTime"));

        processTable.getColumns().addAll(idCol, arrivalCol, burstCol); // Thêm cột vào bảng.
        processTable.setItems(processData);               // Gán dữ liệu cho bảng.
        processTable.setPrefWidth(400);                  // Đặt chiều rộng cho bảng.

        // Tạo các nút điều khiển bên dưới bảng.
        addProcessButton = new Button("Add Process");
        runSimulationButton = new Button("Run Simulation");
        backButton = new Button("Back to Menu");

        HBox buttonBox = new HBox(10, addProcessButton, runSimulationButton, backButton);
        buttonBox.setPadding(new Insets(10));       // Khoảng cách xung quanh HBox.
        buttonBox.setAlignment(Pos.CENTER);         // Canh giữa các nút.

        VBox leftBox = new VBox(10, processTable, buttonBox); // Gộp bảng và nút vào VBox.
        leftBox.setPadding(new Insets(10));         // Khoảng cách xung quanh VBox.
        root.setLeft(leftBox);                      // Đặt vào phần Left của BorderPane.

        // ======= Phần Biểu Đồ Gantt (Center) =======
        ganttChartPane = new FlowPane();            // Tạo FlowPane để biểu diễn Gantt Chart.
        ganttChartPane.setHgap(0);                  // Khoảng cách ngang giữa các thành phần.
        ganttChartPane.setVgap(0);                  // Khoảng cách dọc giữa các thành phần.
        ganttChartPane.setPadding(new Insets(10));  // Khoảng cách xung quanh FlowPane.
        ganttChartPane.setStyle("-fx-border-color: black; -fx-background-color: white;");
        ganttChartPane.setPrefWidth(400);           // Đặt chiều rộng cho biểu đồ.

        ScrollPane ganttScroll = new ScrollPane(ganttChartPane); // Cho phép cuộn biểu đồ Gantt.
        ganttScroll.setFitToWidth(true);
        ganttScroll.setFitToHeight(true);
        ganttScroll.setPrefHeight(200);

        root.setCenter(ganttScroll);                // Đặt biểu đồ Gantt ở phần Center.

        // ======= Phần Chỉ Số (Bottom) =======
        metricsLabel = new Label("Metrics will be displayed here."); // Nhãn hiển thị các chỉ số.
        metricsLabel.setFont(Font.font(14));        // Đặt cỡ chữ cho nhãn.
        metricsLabel.setPadding(new Insets(10));    // Khoảng cách xung quanh nhãn.
        root.setBottom(metricsLabel);               // Đặt nhãn ở phần Bottom.
    }

    /**
     * Trả về layout gốc của giao diện mô phỏng.
     *
     * @return BorderPane chứa toàn bộ giao diện.
     */
    @SuppressWarnings("exports")
    public BorderPane getRoot() {
        return root;
    }

    public ObservableList<Process> getProcessData() {
        return processData;
    }

    public TableView<Process> getProcessTable() {
        return processTable;
    }

    @SuppressWarnings("exports")
    public Button getAddProcessButton() {
        return addProcessButton;
    }

    @SuppressWarnings("exports")
    public Button getRunSimulationButton() {
        return runSimulationButton;
    }

    @SuppressWarnings("exports")
    public Button getBackButton() {
        return backButton;
    }

    @SuppressWarnings("exports")
    public FlowPane getGanttChartPane() {
        return ganttChartPane;
    }

    @SuppressWarnings("exports")
    public Label getMetricsLabel() {
        return metricsLabel;
    }

    /**
     * Hiển thị danh sách tiến trình trong bảng.
     *
     * @param processes Danh sách tiến trình.
     */
    public void displayProcesses(List<Process> processes) {
        processData.clear();
        processData.addAll(processes);
    }

    
    /**
 * Cập nhật biểu đồ Gantt để hiển thị thông tin chi tiết về các tiến trình hoặc thời gian rỗi.
 * 
 * - Đối với thời gian rỗi (Idle): Hiển thị thời gian bắt đầu và kết thúc.
 * - Đối với tiến trình (Active Process): Hiển thị ID tiến trình, thời gian đến (AT), thời gian chờ (WT), 
 *   và thời gian kết thúc (ET).
 *
 * @param ganttEntries Danh sách GanttEntry chứa thông tin về các khoảng thời gian thực thi tiến trình.
 */
public void updateGanttChart(List<GanttEntry> ganttEntries) {
    // 1) Xóa mọi ô cũ
    ganttChartPane.getChildren().clear();

    // 2) Chạy vòng lặp trên 1 Thread phụ
    new Thread(() -> {
        for (GanttEntry entry : ganttEntries) {
            try {
                // Mỗi ô dừng 500ms (tuỳ bạn sửa)
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            // 3) Gọi runLater để thêm ô vào UI (JavaFX thread)
            Platform.runLater(() -> {
                // Tạo Label cho ô
                Label ganttLabel;
                if (entry.getProcessId() == -1) {
                    ganttLabel = new Label(
                        "Idle\n" +
                        "ST: " + String.format("%.2f", entry.getStartTime()) + "\n" +
                        "ET: " + String.format("%.2f", entry.getEndTime())
                    );
                    ganttLabel.setStyle("-fx-border-color: black; -fx-padding: 0px; -fx-background-color: lightgray;");
                } else {
                    ganttLabel = new Label(
                        "P" + entry.getProcessId() + "\n" +
                        "ST: " + String.format("%.2f", entry.getStartTime()) + "\n" +
                        "ET: " + String.format("%.2f", entry.getEndTime())
                    );
                    ganttLabel.setStyle("-fx-border-color: black; -fx-padding: 5px; -fx-background-color: lightblue;");
                }

                ganttLabel.setMinWidth(100);
                ganttLabel.setAlignment(Pos.CENTER);

                // Thêm ô vào FlowPane
                ganttChartPane.getChildren().add(ganttLabel);
            });
        }
    }).start(); // Bắt đầu Thread
}



    /**
     * Hiển thị các chỉ số như thời gian chờ trung bình, thời gian hoàn thành trung bình và CPU sử dụng.
     *
     * @param avgWait Thời gian chờ trung bình.
     * @param avgTurn Thời gian hoàn thành trung bình.
     * @param cpuUtil Hiệu suất sử dụng CPU.
     */
    public void displayMetrics(double avgWait, double avgTurn, double cpuUtil) {
        metricsLabel.setText(String.format(
            "Average Waiting Time: %.2f\nAverage Turnaround Time: %.2f\nCPU Utilization: %.2f%%",
            avgWait, avgTurn, cpuUtil
        ));
    }
}   

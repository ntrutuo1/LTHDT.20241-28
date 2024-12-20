package Controller;

import View.MainMenuView;  // Import View chính của Menu.
import View.SimulationView;  // Import View hiển thị mô phỏng.
import View.HelpView;  // Import View hiển thị phần trợ giúp.
import javafx.scene.Scene;  // Import Scene để chuyển đổi giữa các màn hình.
import javafx.stage.Stage;  // Import Stage là cửa sổ chính.
import javafx.application.Platform;  // Import Platform để thoát ứng dụng.
import javafx.scene.control.Alert;  // Import Alert để hiển thị thông báo.
import javafx.scene.control.Alert.AlertType;  // Import loại Alert.
import Model.Process;  // Import lớp Process đại diện cho tiến trình.

import java.util.List;  // Import List để lưu danh sách tiến trình.

/**
 * MainController điều khiển các chức năng chính của ứng dụng.
 * Nó xử lý các sự kiện từ giao diện chính và thực hiện các hành động tương ứng.
 */
public class MainController {
    private Stage primaryStage;  // Cửa sổ chính của ứng dụng.
    private List<Process> defaultProcessList;  // Danh sách tiến trình mặc định.

    /**
     * Constructor của MainController.
     * Thiết lập các sự kiện cho các nút trong MainMenuView.
     *
     * @param menuView          - View của menu chính.
     * @param primaryStage      - Cửa sổ chính (Stage) để hiển thị các màn hình.
     * @param defaultProcessList - Danh sách tiến trình mặc định được truyền vào.
     */
    @SuppressWarnings("exports")  // Loại bỏ cảnh báo liên quan đến xuất dữ liệu trong JavaFX.
    public MainController(MainMenuView menuView, Stage primaryStage, List<Process> defaultProcessList) {
        this.primaryStage = primaryStage;
        this.defaultProcessList = defaultProcessList;

        // Thiết lập các sự kiện cho các nút của MainMenuView.
        menuView.getFCFSButton().setOnAction(e -> openSimulationView("FCFS"));  // Nút FCFS.
        menuView.getSJNButton().setOnAction(e -> openSimulationView("SJN"));    // Nút SJN.
        menuView.getRRButton().setOnAction(e -> openSimulationView("RR"));      // Nút RR.
        menuView.getHelpButton().setOnAction(e -> showHelp());                  // Nút Help.
        menuView.getExitButton().setOnAction(e -> exitApplication());           // Nút Exit.
    }

    /**
     * Mở SimulationView để thực hiện mô phỏng với thuật toán đã chọn.
     *
     * @param algorithm - Tên của thuật toán lập lịch cần mô phỏng (FCFS, SJN, RR).
     */
    private void openSimulationView(String algorithm) {
        // Tạo View mô phỏng mới với danh sách tiến trình và thuật toán.
        SimulationView simulationView = new SimulationView(defaultProcessList, algorithm);
        
        // Tạo SchedulerController để điều khiển quá trình mô phỏng.
        new SchedulerController(simulationView, algorithm, primaryStage);
        
        // Tạo Scene mới từ SimulationView và đặt vào Stage.
        Scene simulationScene = new Scene(simulationView.getRoot(), 800, 600);
        primaryStage.setScene(simulationScene);  // Chuyển sang màn hình mô phỏng.
    }

    /**
     * Hiển thị cửa sổ trợ giúp bằng cách gọi HelpView.
     */
    private void showHelp() {
        HelpView helpView = new HelpView();  // Tạo HelpView.
        helpView.showHelpInfo();  // Hiển thị thông tin trợ giúp.
    }

    /**
     * Hiển thị hộp thoại xác nhận thoát ứng dụng và xử lý thoát khi người dùng xác nhận.
     */
    private void exitApplication() {
        // Tạo hộp thoại xác nhận với kiểu CONFIRMATION.
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Exit Confirmation");  // Tiêu đề của hộp thoại.
        alert.setHeaderText("Are you sure you want to exit?");  // Nội dung chính.
        alert.setContentText("Press OK to exit.");  // Nội dung bổ sung.

        // Hiển thị hộp thoại và xử lý khi người dùng nhấn OK.
        alert.showAndWait().ifPresent(response -> {
            if (response == javafx.scene.control.ButtonType.OK) {  // Nếu nhấn OK.
                Platform.exit();  // Thoát khỏi ứng dụng.
            }
        });
    }
}

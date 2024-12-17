package App;

import javafx.application.Application;  // Import lớp cơ bản của JavaFX để tạo ứng dụng.
import javafx.scene.Scene;  // Import lớp Scene để tạo giao diện người dùng.
import javafx.stage.Stage;  // Import lớp Stage để tạo cửa sổ chính của ứng dụng.
import Controller.MainController;  // Import Controller để quản lý logic điều khiển.
import View.MainMenuView;  // Import View để hiển thị giao diện chính.
import Model.Scheduler;  // Import Scheduler để xử lý logic tạo danh sách tiến trình.

/**
 * MainApp là lớp khởi chạy ứng dụng JavaFX cho chương trình "CPU Scheduling Simulator".
 * Lớp này kế thừa từ Application của JavaFX để triển khai phương thức khởi chạy giao diện người dùng.
 */
public class MainApp extends Application {
    
    /**
     * Phương thức start là điểm khởi đầu khi ứng dụng được chạy.
     * Nó khởi tạo cửa sổ chính và giao diện của chương trình.
     *
     * @param primaryStage - cửa sổ chính của ứng dụng.
     */
    @SuppressWarnings("exports") // Suppress cảnh báo liên quan đến việc export lớp JavaFX.
    @Override
    public void start(Stage primaryStage) {
        // Tạo danh sách tiến trình mặc định sử dụng hàm createDefaultProcessList từ Scheduler.
        var defaultProcessList = Scheduler.createDefaultProcessList();

        // Khởi tạo View (giao diện chính của ứng dụng).
        MainMenuView mainMenuView = new MainMenuView();

        // Khởi tạo Controller để kết nối View, Stage và danh sách tiến trình.
        new MainController(mainMenuView, primaryStage, defaultProcessList);

        // Tạo Scene (cảnh) với kích thước 800x600 và gắn vào root của MainMenuView.
        Scene scene = new Scene(mainMenuView.getRoot(), 800, 600);

        // Đặt tiêu đề cho cửa sổ chính của ứng dụng.
        primaryStage.setTitle("CPU Scheduling Simulator");

        // Gắn Scene vào Stage (cửa sổ chính).
        primaryStage.setScene(scene);

        // Hiển thị cửa sổ chính của ứng dụng.
        primaryStage.show();
    }

    /**
     * Phương thức main là điểm bắt đầu của ứng dụng Java.
     * Nó gọi phương thức launch() để khởi chạy ứng dụng JavaFX.
     *
     * @param args - đối số dòng lệnh (nếu có).
     */
    public static void main(String[] args) {
        launch(args); // Gọi launch để bắt đầu vòng đời JavaFX.
    }
}

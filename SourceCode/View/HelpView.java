package View;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.text.Font;
import javafx.geometry.Pos;

/**
 * Lớp HelpView cung cấp giao diện trợ giúp cho người dùng.
 * Hiển thị thông tin về các thuật toán lập lịch CPU như FCFS, SJN và Round Robin.
 */
public class HelpView {
    private Stage stage;        // Cửa sổ hiển thị màn hình trợ giúp.
    private VBox root;          // Layout chính chứa các thành phần giao diện.
    private Button closeButton; // Nút để đóng cửa sổ trợ giúp.

    /**
     * Constructor khởi tạo giao diện trợ giúp.
     * Thiết lập bố cục và các thành phần như tiêu đề, nội dung và nút đóng.
     */
    public HelpView() {
        // Tạo cửa sổ mới cho màn hình trợ giúp.
        stage = new Stage();

        // Tạo VBox làm layout chính với khoảng cách giữa các phần tử là 10px.
        root = new VBox(10);
        root.setPadding(new Insets(10));    // Thiết lập khoảng cách bên trong VBox.
        root.setAlignment(Pos.CENTER);      // Canh giữa các thành phần trong VBox.

        // Tiêu đề màn hình trợ giúp.
        Label title = new Label("Help");
        title.setFont(Font.font(20));       // Đặt cỡ chữ cho tiêu đề.

        // Nội dung hướng dẫn cho người dùng.
        Label helpText = new Label(
            "CPU Scheduling Algorithms:\n\n" +
            "1. First-Come, First-Serve (FCFS):\n" +
            "   Processes are executed in the order they arrive.\n" +
            "   Simple but can lead to long waiting times.\n\n" +
            "2. Shortest Job Next (SJN):\n" +
            "   Processes with the shortest burst time are executed first.\n" +
            "   Minimizes average waiting time.\n\n" +
            "3. Round Robin (RR):\n" +
            "   Each process is given a fixed time slot (quantum) in a cyclic order.\n" +
            "   Ensures fairness but may introduce context switching overhead."
        );
        helpText.setWrapText(true); // Cho phép xuống dòng tự động khi nội dung vượt quá độ rộng.

        // Nút đóng cửa sổ trợ giúp.
        closeButton = new Button("Close");
        closeButton.setOnAction(e -> stage.close()); // Đóng cửa sổ khi nhấn nút.

        // Thêm các thành phần vào layout VBox.
        root.getChildren().addAll(title, helpText, closeButton);

        // Tạo Scene và đặt vào Stage.
        Scene scene = new Scene(root, 500, 400); // Scene có kích thước 500x400.
        stage.setTitle("Help");                 // Đặt tiêu đề cho cửa sổ.
        stage.setScene(scene);                  // Gán Scene vào Stage.
    }

    /**
     * Hiển thị cửa sổ trợ giúp.
     */
    public void showHelpInfo() {
        stage.show(); // Hiển thị cửa sổ trợ giúp.
    }
}

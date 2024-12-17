package View;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Font;

/**
 * Lớp MainMenuView tạo giao diện menu chính cho ứng dụng "CPU Scheduling Simulator".
 * Giao diện bao gồm các nút để lựa chọn các thuật toán lập lịch và các tùy chọn khác như trợ giúp và thoát ứng dụng.
 */
public class MainMenuView {
    private BorderPane root;         // Layout chính sử dụng BorderPane để sắp xếp các thành phần.
    private Button fcfsButton;       // Nút để chọn thuật toán First-Come, First-Serve (FCFS).
    private Button sjnButton;        // Nút để chọn thuật toán Shortest Job Next (SJN).
    private Button rrButton;         // Nút để chọn thuật toán Round Robin (RR).
    private Button helpButton;       // Nút để mở màn hình trợ giúp.
    private Button exitButton;       // Nút để thoát khỏi ứng dụng.

    /**
     * Constructor khởi tạo giao diện menu chính.
     * Thiết lập tiêu đề, các nút điều khiển và sắp xếp chúng vào layout BorderPane.
     */
    public MainMenuView() {
        // Tạo layout BorderPane để quản lý bố cục của màn hình.
        root = new BorderPane();

        // Tạo tiêu đề cho menu chính.
        Label title = new Label("CPU Scheduling Simulator"); // Tiêu đề của giao diện.
        title.setFont(Font.font(24));                       // Đặt kích cỡ font chữ cho tiêu đề.

        // Khởi tạo các nút bấm điều khiển.
        fcfsButton = new Button("First-Come, First-Serve (FCFS)"); // Nút chọn FCFS.
        sjnButton = new Button("Shortest Job Next (SJN)");         // Nút chọn SJN.
        rrButton = new Button("Round Robin (RR)");                 // Nút chọn RR.
        helpButton = new Button("Help");                           // Nút trợ giúp.
        exitButton = new Button("Exit");                           // Nút thoát ứng dụng.

        // Tạo VBox để sắp xếp các nút theo chiều dọc với khoảng cách 15px.
        VBox centerBox = new VBox(15, fcfsButton, sjnButton, rrButton, helpButton, exitButton);
        centerBox.setAlignment(Pos.CENTER); // Canh giữa các nút trong VBox.

        // Đặt tiêu đề vào phần trên cùng của BorderPane.
        root.setTop(title);
        BorderPane.setAlignment(title, Pos.CENTER); // Căn giữa tiêu đề.

        // Đặt VBox chứa các nút bấm vào phần trung tâm của BorderPane.
        root.setCenter(centerBox);
    }

    /**
     * Trả về layout gốc của giao diện menu chính.
     * 
     * @return Layout BorderPane chứa tiêu đề và các nút điều khiển.
     */
    @SuppressWarnings("exports")
    public BorderPane getRoot() {
        return root;
    }

    /**
     * Trả về nút chọn thuật toán First-Come, First-Serve (FCFS).
     * 
     * @return Nút FCFS.
     */
    @SuppressWarnings("exports")
    public Button getFCFSButton() {
        return fcfsButton;
    }

    /**
     * Trả về nút chọn thuật toán Shortest Job Next (SJN).
     * 
     * @return Nút SJN.
     */
    @SuppressWarnings("exports")
    public Button getSJNButton() {
        return sjnButton;
    }

    /**
     * Trả về nút chọn thuật toán Round Robin (RR).
     * 
     * @return Nút RR.
     */
    @SuppressWarnings("exports")
    public Button getRRButton() {
        return rrButton;
    }

    /**
     * Trả về nút mở màn hình trợ giúp.
     * 
     * @return Nút Help.
     */
    @SuppressWarnings("exports")
    public Button getHelpButton() {
        return helpButton;
    }

    /**
     * Trả về nút thoát khỏi ứng dụng.
     * 
     * @return Nút Exit.
     */
    @SuppressWarnings("exports")
    public Button getExitButton() {
        return exitButton;
    }
}

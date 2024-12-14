package View;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.text.Font;
import javafx.geometry.Pos;

public class HelpView {
    private Stage stage;
    private VBox root;
    private Button closeButton;

    public HelpView() {
        stage = new Stage();
        root = new VBox(10);
        root.setPadding(new Insets(10));
        root.setAlignment(Pos.CENTER);

        Label title = new Label("Help");
        title.setFont(Font.font(20));

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
        helpText.setWrapText(true);

        closeButton = new Button("Close");
        closeButton.setOnAction(e -> stage.close());

        root.getChildren().addAll(title, helpText, closeButton);

        Scene scene = new Scene(root, 500, 400);
        stage.setTitle("Help");
        stage.setScene(scene);
    }

    public void showHelpInfo() {
        stage.show();
    }
}

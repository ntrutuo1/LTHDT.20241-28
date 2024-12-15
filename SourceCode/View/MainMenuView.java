package View;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Font;

public class MainMenuView {
    private BorderPane root;
    private Button fcfsButton;
    private Button sjnButton;
    private Button rrButton;
    private Button helpButton;
    private Button exitButton;

    public MainMenuView() {
        root = new BorderPane();
        Label title = new Label("CPU Scheduling Simulator");
        title.setFont(Font.font(24));

        fcfsButton = new Button("First-Come, First-Serve (FCFS)");
        sjnButton = new Button("Shortest Job Next (SJN)");
        rrButton = new Button("Round Robin (RR)");
        helpButton = new Button("Help");
        exitButton = new Button("Exit");

        VBox centerBox = new VBox(15, fcfsButton, sjnButton, rrButton, helpButton, exitButton);
        centerBox.setAlignment(Pos.CENTER);

        root.setTop(title);
        BorderPane.setAlignment(title, Pos.CENTER);
        root.setCenter(centerBox);
    }

    @SuppressWarnings("exports")
	public BorderPane getRoot() {
        return root;
    }

    @SuppressWarnings("exports")
	public Button getFCFSButton() {
        return fcfsButton;
    }

    @SuppressWarnings("exports")
	public Button getSJNButton() {
        return sjnButton;
    }

    @SuppressWarnings("exports")
	public Button getRRButton() {
        return rrButton;
    }

    @SuppressWarnings("exports")
	public Button getHelpButton() {
        return helpButton;
    }

    @SuppressWarnings("exports")
	public Button getExitButton() {
        return exitButton;
    }
}

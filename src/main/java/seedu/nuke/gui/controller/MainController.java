package seedu.nuke.gui.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import seedu.nuke.gui.io.Executor;
import seedu.nuke.gui.ui.TextUI;

import java.net.URL;
import java.util.ResourceBundle;

import static seedu.nuke.util.Message.*;

public class MainController implements Initializable {
    @FXML
    private TextField console;

    @FXML
    private TextFlow consoleScreen;

    @FXML
    private ScrollPane consoleScreenScrollPane;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        consoleScreen.setStyle("-fx-font-family: Consolas; -fx-font-size: 12pt");
        // Auto scroll-down
        consoleScreenScrollPane.vvalueProperty().bind(consoleScreen.heightProperty());

        Text logo = TextUI.createText(MESSAGE_LOGO, Color.MAGENTA);
        Text welcomeMessage = TextUI.createText(String.format("%s\n%s\n\n", MESSAGE_WELCOME_1, MESSAGE_WELCOME_2), Color.BLUE);
        Text divider = TextUI.createText("-".repeat(80) + "\n");

        consoleScreen.getChildren().addAll(logo, divider, welcomeMessage);
    }

    public void submitInput() {
        String userInput = console.getText().trim();
        new Executor(console, consoleScreen).executeAction(userInput);
        console.clear();
    }
}

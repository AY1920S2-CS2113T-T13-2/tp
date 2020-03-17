package seedu.nuke.gui.ui;

import javafx.scene.paint.Color;
import javafx.scene.text.Text;

public class TextUI {

    public static Text createText(String input) {
        return new Text(input);
    }

    public static Text createText(String input, Color color) {
        Text text = new Text(input);
        text.setFill(color);
        return text;
    }
}

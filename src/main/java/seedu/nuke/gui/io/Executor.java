package seedu.nuke.gui.io;

import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;
import seedu.nuke.command.Command;
import seedu.nuke.command.CommandResult;
import seedu.nuke.command.ExitCommand;
import seedu.nuke.command.prompt.PromptType;
import seedu.nuke.common.DataType;
import seedu.nuke.data.category.Category;
import seedu.nuke.data.module.Module;
import seedu.nuke.data.task.Task;
import seedu.nuke.format.ListCreator;
import seedu.nuke.gui.controller.MainController;
import seedu.nuke.gui.ui.TextUI;
import seedu.nuke.parser.Parser;

import java.util.ArrayList;

import static seedu.nuke.util.Message.MESSAGE_PROMPT_FORMAT;

public class Executor {
    /* Variables to transit between parsing regular commands and prompts */
    private static PromptType promptType = PromptType.NONE;
    private static String userMessage;
    private static DataType dataType = DataType.NONE;
    private static ArrayList<?> filteredList;
    private static ArrayList<Integer> indices;

    private TextField console;
    private TextFlow consoleScreen;

    public Executor(TextField console, TextFlow consoleScreen) {
        this.console = console;
        this.consoleScreen = consoleScreen;
    }

    public static void preparePromptIndices() {
        promptType = PromptType.INDICES;
    }

    public static void preparePromptConfirmation() {
        promptType = PromptType.CONFIRMATION;
    }

    public static void terminatePrompt() {
        promptType = PromptType.NONE;
    }

    public static ArrayList<Integer> getIndices() {
        return indices;
    }

    public static void setIndices(ArrayList<Integer> indices) {
        Executor.indices = indices;
    }

    public static DataType getDataType() {
        return dataType;
    }

    public static ArrayList<?> getFilteredList() {
        return filteredList;
    }

    public static void setFilteredList(ArrayList<?> filteredList, DataType dataType) {
        Executor.filteredList = filteredList;
        Executor.dataType = dataType;
    }

    public void executeAction(String userInput) {
        Command command;
        switch (promptType) {
            case CONFIRMATION:
                command = new Parser().parseInputAsConfirmation(userInput.toLowerCase());
                break;

            case INDICES:
                command = new Parser().parseInputAsIndices(userInput);
                break;

            default:
                command = new Parser().parseInput(userInput);
                break;
        }

        CommandResult result = command.execute();
        displayResult(result);

        if (ExitCommand.isExit()) {
            Stage window = (Stage) console.getScene().getWindow();
            window.close();
        }
    }

    @SuppressWarnings("unchecked")
    private void displayResult(CommandResult result) {
        Text feedbackToUser = TextUI.createText(String.format("%s\n\n", result.getFeedbackToUser()), Color.BLUE);
        consoleScreen.getChildren().add(feedbackToUser);

        DataType dataType = result.getDataType();
        String listTableToShow;
        switch (dataType) {
            case MODULE:
                ArrayList<Module> moduleList = (ArrayList<Module>) result.getList();
                listTableToShow = ListCreator.createModuleListTable(moduleList);
                break;
            case CATEGORY:
                ArrayList<Category> categoryList = (ArrayList<Category>) result.getList();
                listTableToShow = ListCreator.createCategoryListTable(categoryList);
                break;
            case TASK:
                ArrayList<Task> taskList = (ArrayList<Task>) result.getList();
                listTableToShow = ListCreator.createTaskListTable(taskList);
                break;
            default:
                return;
        }

        Text taskListTable = TextUI.createText(String.format("%s\n\n", listTableToShow), Color.BROWN);
        consoleScreen.getChildren().add(taskListTable);
    }
}

package seedu.nuke.gui.io;

import javafx.scene.control.TableView;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import seedu.nuke.Executor;
import seedu.nuke.command.Command;
import seedu.nuke.command.CommandResult;
import seedu.nuke.command.ExitCommand;
import seedu.nuke.data.ScreenShotManager;
import seedu.nuke.data.storage.StorageManager;
import seedu.nuke.data.storage.StoragePath;
import seedu.nuke.directory.Category;
import seedu.nuke.directory.DirectoryLevel;
import seedu.nuke.directory.Module;
import seedu.nuke.directory.Task;
import seedu.nuke.directory.TaskFile;
import seedu.nuke.gui.util.tablecreator.CategoryTableCreator;
import seedu.nuke.gui.util.tablecreator.FileTableCreator;
import seedu.nuke.gui.util.tablecreator.ModuleTableCreator;
import seedu.nuke.gui.util.TextUtil;
import seedu.nuke.gui.util.tablecreator.TaskTableCreator;
import seedu.nuke.gui.util.tablecreator.basicdirectory.BasicCategory;
import seedu.nuke.gui.util.tablecreator.basicdirectory.BasicFile;
import seedu.nuke.gui.util.tablecreator.basicdirectory.BasicModule;
import seedu.nuke.gui.util.tablecreator.basicdirectory.BasicTask;
import seedu.nuke.util.ListCreator;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.stream.Collectors;

import static seedu.nuke.util.Message.DIVIDER;

public class GuiExecutor {
    private static TextFlow consoleScreen;

    public GuiExecutor(TextFlow consoleScreen) {
        GuiExecutor.consoleScreen = consoleScreen;
    }

    /**
     * Executes the corresponding action based on the command given by the user in the console.
     *
     * @param userInput
     *  The input given by the user
     */
    public void executeAction(String userInput) {
        CommandResult result = Executor.executeCommand(userInput);
        displayResult(result);

        // Save list
        if (StorageManager.isToSave()) {
            try {
                new StorageManager(StoragePath.SAVE_PATH).saveList();
            } catch (IOException e) {
                showMessage(e.getMessage());
            }
            ScreenShotManager.saveScreenShot();
        }

        if (ExitCommand.isExit()) {
            Stage window = (Stage) consoleScreen.getScene().getWindow();
            window.close();
        }
    }

    /**
     * Executes the command given.
     *
     * @param command
     *  The command to be executed
     */
    public void executeCommand(Command command) {
        CommandResult result = Executor.execute(command);
        displayResult(result);

        if (ExitCommand.isExit()) {
            Stage window = (Stage) consoleScreen.getScene().getWindow();
            window.close();
        }
    }

    /**
     * Shows a message on the console screen to the user.
     *
     * @param message
     *  The message to show
     */
    public void showMessage(String message) {
        if (!message.isEmpty()) {
            Text feedbackToUser = TextUtil.createText(String.format("%s", message), Color.NAVY);
            consoleScreen.getChildren().addAll(feedbackToUser);
        }
    }

    /**
     * Displays the result on the console screen to the user.
     *
     * @param result
     *  The result from executing the command
     */
    private void displayResult(CommandResult result) {
        if (result.getFeedbackToUser() == null) {
            return;
        }

        if (!result.getFeedbackToUser().isEmpty()) {
            showMessage(result.getFeedbackToUser());
        }

        DirectoryLevel dataType = result.getDirectoryLevel();
        switch (dataType) {
        case MODULE:
            ArrayList<Module> moduleList = result.getShownList().stream()
                    .map(Module.class::cast)
                    .collect(Collectors.toCollection(ArrayList::new));
            TableView<BasicModule> modules = new ModuleTableCreator(moduleList, consoleScreen).createModuleListTable();
            consoleScreen.getChildren().add(modules);
            break;

        case CATEGORY:
            ArrayList<Category> categoryList = result.getShownList().stream()
                    .map(Category.class::cast)
                    .collect(Collectors.toCollection(ArrayList::new));
            TableView<BasicCategory> categories =
                    new CategoryTableCreator(categoryList, consoleScreen).createCategoryListTable();
            consoleScreen.getChildren().add(categories);
            break;

        case TASK:
            ArrayList<Task> taskList = result.getShownList().stream()
                    .map(Task.class::cast)
                    .collect(Collectors.toCollection(ArrayList::new));
            TableView<BasicTask> tasks = new TaskTableCreator(taskList, consoleScreen).createTaskListTable();
            consoleScreen.getChildren().add(tasks);
            break;

        case FILE:
            ArrayList<TaskFile> fileList = result.getShownList().stream()
                    .map(TaskFile.class::cast)
                    .collect(Collectors.toCollection(ArrayList::new));
            TableView<BasicFile> files = new FileTableCreator(fileList, consoleScreen).createFileListTable();
            consoleScreen.getChildren().add(files);
            break;

        case NONE:
            if (result.getHelpGuide() == null) {
                break;
            }
            ArrayList<String> helpList = result.getHelpGuide();
            String helpGuide = ListCreator.createGeneralListTable(helpList);
            Text helpGuideText = TextUtil.createText(String.format("%s", helpGuide), Color.MIDNIGHTBLUE);
            consoleScreen.getChildren().add(helpGuideText);
            break;

        default:
            break;
        }

        Text divider = TextUtil.createText(String.format("\n%s\n\n", DIVIDER), Color.DARKKHAKI);
        consoleScreen.getChildren().add(divider);
    }

    /**
     * Opens up the file chooser for the user to select a file to add to the File List.
     *
     * @return
     *  A String array containing the path and name of the selected file
     */
    public static String[] executeFileChooser() {
        Stage stage = (Stage) consoleScreen.getScene().getWindow();
        String[] chosenFileInformation = new String[2];

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select File To Add");
        File chosenFile = fileChooser.showOpenDialog(stage);

        if (chosenFile != null) {
            chosenFileInformation[0] = chosenFile.getAbsolutePath();
            chosenFileInformation[1] = chosenFile.getName();
        } else {
            return null;
        }
        return chosenFileInformation;
    }

    public static void clearScreen() {
        consoleScreen.getChildren().clear();
    }
}

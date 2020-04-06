package seedu.nuke;

import seedu.nuke.command.CommandResult;
import seedu.nuke.command.ExitCommand;
import seedu.nuke.data.ModuleLoader;
import seedu.nuke.data.ModuleManager;
import seedu.nuke.data.ScreenShotManager;
import seedu.nuke.data.storage.StorageManager;
import seedu.nuke.data.storage.StoragePath;
import seedu.nuke.ui.TextUi;
import seedu.nuke.ui.Ui;
import seedu.nuke.util.Message;

import java.io.IOException;
import java.util.HashMap;

public class Nuke {
    private CommandResult commandResult;
    private HashMap<String, String> modulesMap;
    private Ui ui;
    private StorageManager storageManager;

    /**
     * constructor of nuke.
     *
     */
    public Nuke() {
        ui = new Ui();
        modulesMap = ModuleLoader.load(StoragePath.NUS_MODULE_LIST_PATH);
        storageManager = new StorageManager(StoragePath.SAVE_PATH);
        ModuleManager.initialise(modulesMap);
        storageManager.loadList();
        ScreenShotManager.initialise();
    }

    /**
     * ScreenShot entry-point for the java.duke.Duke application.
     *
     * @param args arguments passed to the programme.
     */
    public static void main(String[] args) {
        new Nuke().run();
    }

    /**
     * run method for Nuke class.
     */
    public void run() {
        welcomeUser();
        runCommandLoopUntilExitCommand();
        exit();
    }

    /**
     * Method to print the welcome message to the user.
     */
    public void welcomeUser() {
        TextUi.clearScreen();
        TextUi.displayLogo();
        TextUi.showWelcomeMessage();
    }

    /**
     * Method to print the exit message to the user.
     */
    public void exit() {
        try {
            storageManager.cleanUp();
        } catch (IOException e) {
            ui.showMessage(e.getMessage());
        }
        ui.showMessage(Message.DIVIDER);

        try {
            storageManager.saveList();
        } catch (IOException e) {
            ui.showMessage(e.getMessage());
        }
    }

    /**
     * Method to run the command from the user's input until exit command is received.
     */
    private void runCommandLoopUntilExitCommand() {
        do {
            String userInput = ui.getInput();

            commandResult = Executor.executeCommand(userInput);
            ui.showResult(commandResult);

            // Save list
            if (StorageManager.isToSave()) {
                try {
                    storageManager.saveList();
                } catch (IOException e) {
                    ui.showMessage(e.getMessage());
                }
                ScreenShotManager.saveScreenShot();
            }
        } while (!ExitCommand.isExit());
    }

    public CommandResult getCommandResult() {
        return commandResult;
    }
}

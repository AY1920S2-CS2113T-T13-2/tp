package seedu.nuke.command;

import org.junit.jupiter.api.Test;
import seedu.nuke.Executor;
import seedu.nuke.command.misc.RedoCommand;
import seedu.nuke.command.misc.UndoCommand;
import seedu.nuke.data.ModuleLoader;
import seedu.nuke.data.ModuleManager;
import seedu.nuke.data.ScreenShotManager;
import seedu.nuke.data.storage.StoragePath;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.nuke.command.misc.RedoCommand.MESSAGE_REDO_AT_END;

class RedoCommandTest {

    @Test
    void execute() {
        HashMap<String, String> modulesMap = ModuleLoader.load(StoragePath.NUS_MODULE_LIST_PATH);
        ModuleManager.initialise(modulesMap);
        ScreenShotManager.saveScreenShot();

        CommandResult result = Executor.executeCommand(RedoCommand.COMMAND_WORD);
        ScreenShotManager.saveScreenShot();
        assertEquals(result.getFeedbackToUser(), MESSAGE_REDO_AT_END);

        CommandResult result1 = Executor.executeCommand("addm cs3235");
        ScreenShotManager.saveScreenShot();
        assertEquals(ModuleManager.getModuleList().size(), 1);

        CommandResult result2 = Executor.executeCommand(UndoCommand.COMMAND_WORD);
        ScreenShotManager.saveScreenShot();
        assertEquals(ModuleManager.getModuleList().size(), 0);

        CommandResult result3 = Executor.executeCommand(RedoCommand.COMMAND_WORD);
        ScreenShotManager.saveScreenShot();
        assertEquals(ModuleManager.getModuleList().size(), 1);
    }
}
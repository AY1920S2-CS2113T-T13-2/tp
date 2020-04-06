package seedu.nuke.command;

import org.junit.jupiter.api.Test;
import seedu.nuke.Executor;
import seedu.nuke.command.misc.UndoCommand;
import seedu.nuke.data.ModuleLoader;
import seedu.nuke.data.ModuleManager;
import seedu.nuke.data.ScreenShotManager;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertEquals;

class UndoCommandTest {

    @Test
    void execute() {
        HashMap<String, String> modulesMap = ModuleLoader.load("");
        ModuleManager.initialise(modulesMap);
        ScreenShotManager.saveScreenShot();

        CommandResult result = Executor.executeCommand(UndoCommand.COMMAND_WORD);
        ScreenShotManager.saveScreenShot();

        CommandResult result1 = Executor.executeCommand("addm cs3235");
        ScreenShotManager.saveScreenShot();
        assertEquals(ModuleManager.getModuleList().size(), 1);

        CommandResult result2 = Executor.executeCommand("addm cs3230");
        ScreenShotManager.saveScreenShot();
        assertEquals(ModuleManager.getModuleList().size(), 2);

        CommandResult result3 = Executor.executeCommand(UndoCommand.COMMAND_WORD);
        ScreenShotManager.saveScreenShot();
        assertEquals(ModuleManager.getModuleList().size(), 1);
    }
}
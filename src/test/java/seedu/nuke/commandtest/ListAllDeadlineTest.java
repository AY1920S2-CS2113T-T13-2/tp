package seedu.nuke.commandtest;

import org.junit.jupiter.api.Test;
import seedu.nuke.Executor;
import seedu.nuke.command.Command;
import seedu.nuke.command.CommandResult;
import seedu.nuke.command.filtercommand.listcommand.ListAllTasksDeadlineCommand;
import seedu.nuke.data.ModuleManager;
import seedu.nuke.directory.DirectoryLevel;
import seedu.nuke.parser.Parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.nuke.util.Message.MESSAGE_NO_TASKS_TO_SHOW;

/**
 * Junit test class to test ListAllTasksDeadlineCommand.
 */
public class ListAllDeadlineTest {

    @Test
    public void testTasksCounting() {
        ModuleManager.initialise();
        Command command = new ListAllTasksDeadlineCommand();
        CommandResult result = Executor.execute(command);
        if (result.getDirectoryLevel() == DirectoryLevel.TASK) {
            assertEquals(ModuleManager.countAllTasks(), result.getShownList().size());
        } else {
            assertEquals(0, ModuleManager.countAllTasks());
        }

    }

    @Test
    public void testEmptyTaskList() {
        ModuleManager.initialise();
        Command command = new Parser().parseCommand(ListAllTasksDeadlineCommand.COMMAND_WORD);
        CommandResult result = Executor.execute(command);
        if (ModuleManager.countAllTasks() == 0) {
            assertEquals(MESSAGE_NO_TASKS_TO_SHOW, result.getFeedbackToUser());
        }
    }
}

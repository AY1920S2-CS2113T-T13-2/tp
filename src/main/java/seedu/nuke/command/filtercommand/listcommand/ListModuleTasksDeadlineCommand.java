package seedu.nuke.command.filtercommand.listcommand;

import seedu.nuke.command.CommandResult;
import seedu.nuke.data.ModuleManager;
import seedu.nuke.directory.DirectoryLevel;
import seedu.nuke.directory.DirectoryTraverser;
import seedu.nuke.directory.Module;
import seedu.nuke.directory.Task;
import seedu.nuke.exception.IncorrectDirectoryLevelException;
import seedu.nuke.util.ExceptionMessage;

import java.util.ArrayList;

import static seedu.nuke.util.Message.MESSAGE_NO_TASKS_TO_SHOW;
import static seedu.nuke.util.Message.messageTaskSuccessfullyList;


/**
 * sort all tasks of one module according to deadline of task and print it out to the user.
 */
public class ListModuleTasksDeadlineCommand extends ListCommand {

    public static final String COMMAND_WORD = "lsmtd";
    public static final String FORMAT = COMMAND_WORD + " <module code>";
    public static final String MESSAGE_USAGE = COMMAND_WORD + System.lineSeparator()
            + "List tasks of module in ascending order of deadline" + System.lineSeparator()
            + FORMAT + System.lineSeparator();

    private String moduleCode;

    public ListModuleTasksDeadlineCommand(String moduleCode) {
        this.moduleCode = moduleCode;
    }

    @Override
    public CommandResult execute() {
        try {
            final String noKeyword = "";
            Module module = DirectoryTraverser.getModuleDirectory(moduleCode);
            ArrayList<Task> filteredTaskList = ModuleManager.filterExact(module.getModuleCode(), noKeyword, noKeyword);
            sortTaskList(filteredTaskList, true, false);
            if (filteredTaskList.isEmpty()) {
                return new CommandResult(MESSAGE_NO_TASKS_TO_SHOW);
            }
            assert filteredTaskList.isEmpty() : "make sure there are some tasks in the list";
            return new CommandResult(messageTaskSuccessfullyList(filteredTaskList.size()),
                    DirectoryLevel.TASK, new ArrayList<>(filteredTaskList));
        } catch (IncorrectDirectoryLevelException e) {
            return new CommandResult(ExceptionMessage.MESSAGE_INCORRECT_DIRECTORY_LEVEL);
        } catch (ModuleManager.ModuleNotFoundException e) {
            return new CommandResult(ExceptionMessage.MESSAGE_MODULE_NOT_FOUND);
        }
    }
}

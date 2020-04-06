package seedu.nuke.command.filtercommand.listcommand;

import seedu.nuke.command.CommandResult;
import seedu.nuke.data.ModuleManager;
import seedu.nuke.directory.DirectoryLevel;
import seedu.nuke.directory.Task;

import java.util.ArrayList;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static seedu.nuke.parser.Parser.DEADLINE_PREFIX;
import static seedu.nuke.parser.Parser.PRIORITY_PREFIX;
import static seedu.nuke.util.Message.MESSAGE_NO_TASKS_TO_SHOW;
import static seedu.nuke.util.Message.messageTaskSuccessfullyList;

/**
 * Sorts all undone tasks across all modules by either deadline or priority and display the sorted tasks to the user.
 * By default, this command sorts all undone tasks by deadline, unless specified by the user to sort by priority.
 */
public class ListTaskSortedCommand extends ListCommand {
    public static final String COMMAND_WORD = "lsts";
    public static final String FORMAT = COMMAND_WORD + " [ -d -p (choose 1; default -d) ]";
    public static final String MESSAGE_USAGE = COMMAND_WORD + System.lineSeparator()
            + "List all undone tasks from earliest to latest deadline or "
            + "from largest to smallest priority"
            + System.lineSeparator() + FORMAT + System.lineSeparator();
    public static final Pattern REGEX_FORMAT = Pattern.compile(
            "(?<priority>(?:\\s+" + PRIORITY_PREFIX + ")?)"
            + "(?<deadline>(?:\\s+" + DEADLINE_PREFIX + ")?)"
            + "(?<prioritySecond>(?:\\s+" + PRIORITY_PREFIX + ")?)"
            + "(?<invalid>.*)"
    );

    private boolean isByPriority;

    public ListTaskSortedCommand(boolean isByPriority) {
        this.isByPriority = isByPriority;
    }

    @Override
    public CommandResult execute() {
        // Get all tasks
        ArrayList<Task> filteredTaskList = ModuleManager.getAllTasks().stream()
                .filter(task -> !task.isDone()).collect(Collectors.toCollection(ArrayList::new));

        if (filteredTaskList.isEmpty()) {
            return new CommandResult(MESSAGE_NO_TASKS_TO_SHOW);
        }

        if (isByPriority) {
            sortTaskList(filteredTaskList, false, true);
        } else {
            sortTaskList(filteredTaskList, true, false);
        }

        assert filteredTaskList.isEmpty() : "make sure there are some tasks in the list";

        return new CommandResult(messageTaskSuccessfullyList(filteredTaskList.size()),
                DirectoryLevel.TASK, new ArrayList<>(filteredTaskList));
    }
}

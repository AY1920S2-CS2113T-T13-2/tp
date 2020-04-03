package seedu.nuke.command.filtercommand.listcommand;

import seedu.nuke.command.Command;
import seedu.nuke.command.CommandResult;
import seedu.nuke.data.ModuleManager;
import seedu.nuke.directory.DirectoryLevel;
import seedu.nuke.directory.Task;
import seedu.nuke.util.DateTime;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.regex.Pattern;

import static seedu.nuke.parser.Parser.ALL_FLAG;
import static seedu.nuke.util.Message.MESSAGE_INVALID_TIME_SPECIFIER;
import static seedu.nuke.util.Message.MESSAGE_NO_TASKS_TO_SHOW;
import static seedu.nuke.util.Message.MESSAGE_SHOW_LIST;

/**
 * <h3>Due Command</h3>
 * A <b>Command</b> to show a filtered <b>Task List</b> to the user based on the <i>keyword</i> provided. The
 * <i>filtered list</i> contains tasks that have their <i>dates</i> <i>on</i>, <i>before</i> or <i>after</i> the
 * specified <i>date</i> provided by the user.
 * @see Command
 */
public class DueCommand extends ListCommand {
    public static final String COMMAND_WORD = "due";
    public static final String FORMAT = "due [ on/before/after ] <date> [ -a ]  OR  due over [ -a ]";
    public static final Pattern REGEX_FORMAT = Pattern.compile(
            "(?<identifier>(?:\\s+\\w\\S*)+)"
            + "(?<all>(?:\\s+" + ALL_FLAG + ")?)"
            + "(?<invalid>.*)"
    );

    private LocalDate searchDate;
    private String timeSpecifier;
    private boolean isAll;

    /**
     * Constructs the <b>Due Command</b>.
     * <br> If the <code>specifier</code> given is <code>NULL</code>, the <code>timeSpecifier</code> is set to
     * <i>on</i>.
     * @param searchDate
     *  The <i>date</i> to filter the <b>Task List</b> by
     * @param timeSpecifier
     *  The <i>time specifier</i> to filter the <b>Task List</b> by
     * @param isAll
     *  To toggle between showing done tasks in the filtered list or not
     */
    public DueCommand(LocalDate searchDate, String timeSpecifier, boolean isAll) {
        this.searchDate = searchDate;
        this.timeSpecifier = (timeSpecifier != null) ? timeSpecifier.toLowerCase() : "";
        this.isAll = isAll;
    }

    /**
     * Constructs the <b>Due Command</b> to show Overdue tasks.
     * @param timeSpecifier
     *  The <i>time specifier</i> that should be only the "over" string
     * @param isAll
     *  To toggle between showing done tasks in the filtered list or not
     */
    public DueCommand(String timeSpecifier, boolean isAll) {
        this.timeSpecifier = timeSpecifier;
        this.isAll = isAll;
        assert timeSpecifier.equals("over") : "Incorrect use of the due over command!";
    }

    /**
     * Filters the <i>tasks</i> for <i>deadlines</i> on the <u>same</u> <i>date</i> as the specified search date.
     * <br> Returns an <code>ArrayList</code> containing the filtered <i>tasks</i>.
     *
     * @param tasks
     *  The initial task list to filter from
     * @return
     *  The <code>ArrayList</code> of filtered tasks
     */
    private ArrayList<Task> filterDate(ArrayList<Task> tasks) {
        ArrayList<Task> filteredTasks = new ArrayList<>();
        for (Task task : tasks) {
            // Ignore done tasks if not displaying all the tasks
            if (!isAll && task.isDone()) {
                continue;
            }
            DateTime deadline = task.getDeadline();
            if (deadline.isPresent() && deadline.isOn(searchDate)) {
                filteredTasks.add(task);
            }
        }
        return filteredTasks;
    }

    /**
     * Filters the <i>tasks</i> for <i>deadlines</i> on an <u>earlier</u> <i>date</i> compared to the specified
     * search date.
     * <br> Returns an <code>ArrayList</code> containing the filtered <i>tasks</i>.
     *
     * @param tasks
     *  The initial task list to filter from
     * @return
     *  The <code>ArrayList</code> of filtered tasks
     */
    private ArrayList<Task> filterDateBefore(ArrayList<Task> tasks) {
        ArrayList<Task> filteredTasks = new ArrayList<>();
        for (Task task : tasks) {
            // Ignore done tasks if not displaying all the tasks
            if (!isAll && task.isDone()) {
                continue;
            }
            DateTime deadline = task.getDeadline();
            if (deadline.isPresent() && deadline.isBefore(searchDate)) {
                filteredTasks.add(task);
            }
        }
        return filteredTasks;
    }

    /**
     * Filters the <i>tasks</i> for <i>deadlines</i> on a <u>later</u> <i>date</i> compared to the specified
     * search date.
     * <br> Returns an <code>ArrayList</code> containing the filtered <i>tasks</i>.
     *
     * @param tasks
     *  The initial task list to filter from
     * @return
     *  The <code>ArrayList</code> of filtered tasks
     */
    private ArrayList<Task> filterDateAfter(ArrayList<Task> tasks) {
        ArrayList<Task> filteredTasks = new ArrayList<>();
        for (Task task : tasks) {
            // Ignore done tasks if not displaying all the tasks
            if (!isAll && task.isDone()) {
                continue;
            }
            DateTime deadline = task.getDeadline();
            if (deadline.isPresent() && deadline.isAfter(searchDate)) {
                filteredTasks.add(task);
            }
        }
        return filteredTasks;
    }

    private ArrayList<Task> filterOverdue(ArrayList<Task> tasks) {
        ArrayList<Task> filteredTasks = new ArrayList<>();
        for (Task task : tasks) {
            // Ignore done tasks if not displaying all the tasks
            if (!isAll && task.isDone()) {
                continue;
            }
            DateTime deadline = task.getDeadline();
            if (deadline.isPresent() && deadline.isDue()) {
                filteredTasks.add(task);
            }
        }
        return filteredTasks;
    }

    /**
     * Executes the <b>Due Command</b> to show the filtered <b>Task List</b> to the user based on the
     * <code>searchDate</code> and <code>timeSpecifier</code>.
     *
     * @return
     *  The <b>Command Result</b> of the execution
     * @see CommandResult
     */
    @Override
    public CommandResult execute() {
        ArrayList<Task> allTasks = ModuleManager.getAllTasks();
        ArrayList<Task> filteredTasks;

        switch (timeSpecifier) {

        case "":
        case "on":
            filteredTasks = filterDate(allTasks);
            break;

        case "before":
        case "b":
            filteredTasks = filterDateBefore(allTasks);
            break;

        case "after":
        case "a":
            filteredTasks = filterDateAfter(allTasks);
            break;

        case "over":
            filteredTasks = filterOverdue(allTasks);
            break;

        default:
            return new CommandResult(MESSAGE_INVALID_TIME_SPECIFIER);
        }

        if (filteredTasks.isEmpty()) {
            return new CommandResult(MESSAGE_NO_TASKS_TO_SHOW);
        }

        sortTaskList(filteredTasks, true, false);
        return new CommandResult(MESSAGE_SHOW_LIST, DirectoryLevel.TASK, new ArrayList<>(filteredTasks));
    }
}

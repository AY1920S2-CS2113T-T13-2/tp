package seedu.nuke.command.task;

import seedu.nuke.command.Command;
import seedu.nuke.command.CommandResult;
import seedu.nuke.data.category.Category;
import seedu.nuke.data.category.CategoryList;
import seedu.nuke.data.module.ModuleList;
import seedu.nuke.data.task.Task;
import seedu.nuke.data.task.TaskList;
import seedu.nuke.format.DateTime;

import java.util.regex.Pattern;

import static seedu.nuke.parser.Parser.*;
import static seedu.nuke.util.ExceptionMessage.*;
import static seedu.nuke.util.Message.MESSAGE_ADD_TASK_SUCCESS;

/**
 * <h3>Add Task Command</h3>
 * A <b>Command</b> to add a <b>Task</b> to the <b>Task List</b>.
 *
 * @see Command
 * @see Task
 */
public class AddTaskCommand extends Command {
    public static final String COMMAND_WORD = "addt";
    public static final String FORMAT = COMMAND_WORD +
            " <task description> -m <module code> -c <category name> -d <deadline> -p <priority>";
    public static final Pattern[] REGEX_FORMATS = {
            Pattern.compile("(?<identifier>^\\s*([^-]+))"),
            Pattern.compile("(?<moduleCode>(?:" + MODULE_CODE_PREFIX + " [^-]+))"),
            Pattern.compile("(?<categoryName>(?:" + CATEGORY_NAME_PREFIX + " [^-]+))"),
            Pattern.compile("(?<deadline>(?:" + DEADLINE_PREFIX + " [^-]+)?)"),
            Pattern.compile("(?<priority>(?:" + PRIORITY_PREFIX + " [^-]+)?)"),
            Pattern.compile("(?<invalid>(?:-(?:[^mcdp].*|[mcdp]\\S+)))")
    };

    private String moduleCode;
    private String categoryName;
    private String description;
    private DateTime deadline;
    private Integer priority;

    public AddTaskCommand(String moduleCode, String categoryName, String description, DateTime deadline, Integer priority) {
        this.moduleCode = moduleCode;
        this.categoryName = categoryName;
        this.description = description;
        this.deadline = deadline;
        this.priority = priority;
    }

    @Override
    public CommandResult execute() {
        try {
            if (priority == null) {
                priority = ModuleList.getCategory(moduleCode, categoryName).getCategoryPriority();
            }
            Category parentCategory = ModuleList.getCategory(moduleCode, categoryName);
            Task toAdd = new Task(parentCategory, description, deadline, priority);
            ModuleList.retrieve(moduleCode, categoryName).add(toAdd);
            return new CommandResult(MESSAGE_ADD_TASK_SUCCESS(description));
        } catch (ModuleList.ModuleNotFoundException e) {
            return new CommandResult(MESSAGE_MODULE_NOT_FOUND);
        } catch (CategoryList.CategoryNotFoundException e) {
            return new CommandResult(MESSAGE_CATEGORY_NOT_FOUND);
        } catch (TaskList.DuplicateTaskException e) {
            return new CommandResult(MESSAGE_DUPLICATE_TASK);
        }
    }
}

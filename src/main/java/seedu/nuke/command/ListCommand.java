package seedu.nuke.command;

import seedu.nuke.common.DataType;
import seedu.nuke.data.module.ModuleList;
import seedu.nuke.data.task.Task;

import java.util.ArrayList;
import java.util.regex.Pattern;

import static seedu.nuke.parser.Parser.*;
import static seedu.nuke.util.Message.MESSAGE_SHOW_LIST;

public class ListCommand extends Command {
    public static final String COMMAND_WORD = "list";
    public static final String FORMAT = COMMAND_WORD + " ( -m <module code> -c <category name> -t <task description> )";
    public static final Pattern[] REGEX_FORMATS = {
            Pattern.compile("(?<moduleCode>(?:" + MODULE_CODE_PREFIX + " [^-]+)?)"),
            Pattern.compile("(?<categoryName>(?:" + CATEGORY_NAME_PREFIX + " [^-]+)?)"),
            Pattern.compile("(?<taskDescription>(?:" + TASK_DESCRIPTION_PREFIX + " [^-]+)?)"),
            Pattern.compile("(?<invalid>(?:-(?:[^mct].*|[mct]\\S+)))")
    };

    private String moduleKeyword;
    private String categoryKeyword;
    private String taskKeyword;

    public ListCommand(String moduleKeyword, String categoryKeyword, String taskKeyword) {
        this.moduleKeyword = moduleKeyword;
        this.categoryKeyword = categoryKeyword;
        this.taskKeyword = taskKeyword;
    }

    @Override
    @SuppressWarnings("unchecked")
    public CommandResult execute() {
        ArrayList<Task> filteredTaskList = ModuleList.filter(moduleKeyword, categoryKeyword, taskKeyword);
        return new CommandResult(MESSAGE_SHOW_LIST, DataType.TASK, filteredTaskList);
    }
}

package seedu.nuke.command.promptCommand;

import seedu.nuke.Executor;
import seedu.nuke.command.Command;
import seedu.nuke.command.CommandResult;
import seedu.nuke.common.DataType;
import seedu.nuke.directory.Category;
import seedu.nuke.directory.Directory;
import seedu.nuke.directory.Module;
import seedu.nuke.directory.Task;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static seedu.nuke.util.ExceptionMessage.MESSAGE_LIST_NUMBER_NOT_FOUND;
import static seedu.nuke.util.Message.*;

public class ListNumberPrompt extends Command {
    public static final Pattern INDICES_FORMAT = Pattern.compile("(?<indices>(?:\\s*\\d+)+\\s*)");

    private ArrayList<Directory> filteredList;
    private ArrayList<Integer> indices;
    private DataType dataType;

    public ListNumberPrompt(ArrayList<Integer> indices) {
        this.filteredList = Executor.getFilteredList();
        this.indices = indices;
        this.dataType = Executor.getDataType();
    }

    private CommandResult executePromptConfirmation() {
        switch (dataType) {
            case MODULE: {
                // Cast to Array List of modules
                ArrayList<Module> filteredModules = filteredList.stream()
                        .map(Module.class::cast)
                        .collect(Collectors.toCollection(ArrayList::new));
                return new CommandResult(messageConfirmDeleteModule(filteredModules, indices));
            }
            case CATEGORY: {
                // Cast to Array List of categories
                ArrayList<Category> filteredCategories = filteredList.stream()
                        .map(Category.class::cast)
                        .collect(Collectors.toCollection(ArrayList::new));
                return new CommandResult(messageConfirmDeleteCategory(filteredCategories, indices));
            }
            case TASK: {
                // Cast to Array List of tasks
                ArrayList<Task> filteredTasks = filteredList.stream()
                        .map(Task.class::cast)
                        .collect(Collectors.toCollection(ArrayList::new));
                return new CommandResult(messageConfirmDeleteTask(filteredTasks, indices));
            }
            default:
                return new CommandResult("Error in displaying list.");
        }
    }

    @Override
    public CommandResult execute() {
        if (indices == null) {
            Executor.terminatePrompt();
            return new CommandResult(MESSAGE_INVALID_DELETE_INDICES);
        } else {
            Executor.preparePromptConfirmation();
            Executor.setIndices(indices);

            try {
                return executePromptConfirmation();
            } catch (IndexOutOfBoundsException e) {
                Executor.terminatePrompt();
                return new CommandResult(MESSAGE_LIST_NUMBER_NOT_FOUND);
            }
        }
    }
}
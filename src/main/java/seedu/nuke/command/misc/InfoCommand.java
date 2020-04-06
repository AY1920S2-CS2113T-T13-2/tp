package seedu.nuke.command.misc;

import seedu.nuke.command.Command;
import seedu.nuke.command.CommandResult;
import seedu.nuke.data.ModuleManager;
import seedu.nuke.directory.Category;
import seedu.nuke.directory.Directory;
import seedu.nuke.directory.DirectoryTraverser;
import seedu.nuke.directory.Module;
import seedu.nuke.directory.Task;

import java.util.ArrayList;

/**
 * A command to display information about a specified Directory.
 */
public class InfoCommand extends Command {
    public static final String COMMAND_WORD = "info";
    public static final String FORMAT = COMMAND_WORD;
    public static final String MESSAGE_USAGE = COMMAND_WORD + System.lineSeparator()
            + "Display information about the current directory"
            + System.lineSeparator() + FORMAT + System.lineSeparator();

    private Directory directory;

    /**
     * A constructor for the Info Command.
     *
     * @param directory
     *  The directory to display the information of
     */
    public InfoCommand(Directory directory) {
        this.directory = directory;
    }

    /**
     * A constructor for the Info Command. The directory to use will be the current directory.
     */
    public InfoCommand() {
        this(DirectoryTraverser.getCurrentDirectory());
    }

    /**
     * Gets the list of underlying child directories to show to the user.
     *
     * @return
     *  The list of sub directories under the specified directory
     */
    private ArrayList<Directory> getListToShow() {
        switch (directory.getLevel()) {
        case ROOT:
            return new ArrayList<>(ModuleManager.getModuleList());

        case MODULE:
            Module module = (Module) directory;
            return new ArrayList<>(module.getCategories().getCategoryList());

        case CATEGORY:
            Category category = (Category) directory;
            return new ArrayList<>(category.getTasks().getTaskList());
        case TASK:
            Task task = (Task) directory;
            return new ArrayList<>(task.getFiles().getFileList());

        default:
            return null;
        }
    }

    /**
     * Executes the <b>Info Command</b> to display information about the Directory.
     *
     * @return The <b>Command Result</b> of the execution
     * @see CommandResult
     */
    @Override
    public CommandResult execute() {
        ArrayList<Directory> listToShow = getListToShow();
        if (listToShow == null || listToShow.isEmpty()) {
            return new CommandResult(directory.toString());
        } else {
            return new CommandResult(directory.toString(), directory.getLevel().next(), listToShow);
        }
    }
}

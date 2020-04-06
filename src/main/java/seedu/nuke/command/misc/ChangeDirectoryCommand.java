package seedu.nuke.command.misc;

import seedu.nuke.command.Command;
import seedu.nuke.command.CommandResult;
import seedu.nuke.directory.Directory;
import seedu.nuke.directory.DirectoryTraverser;
import seedu.nuke.exception.DataNotFoundException;
import seedu.nuke.exception.DirectoryTraversalOutOfBoundsException;
import seedu.nuke.exception.IncorrectDirectoryLevelException;

import static seedu.nuke.util.ExceptionMessage.MESSAGE_DIRECTORY_NOT_FOUND;
import static seedu.nuke.util.ExceptionMessage.MESSAGE_FAILED_DIRECTORY_TRAVERSAL;
import static seedu.nuke.util.ExceptionMessage.MESSAGE_TRAVERSE_ERROR;

public class ChangeDirectoryCommand extends Command {

    public static final String COMMAND_WORD = "cd";
    public static final String FORMAT = COMMAND_WORD + " <next directory name>";
    public static final String MESSAGE_USAGE = COMMAND_WORD + System.lineSeparator()
            + "traverse to another directory (.. represents parent directory)" + System.lineSeparator()
            + FORMAT + System.lineSeparator();
    private String nextDirectoryName;
    private Directory directory;

    /**
     * Constructs the command to traverse down into the next directory with the given name.
     *
     * @param nextDirectoryName
     *  The name of the next directory
     */
    public ChangeDirectoryCommand(String nextDirectoryName) {
        this.nextDirectoryName = nextDirectoryName;
        this.directory = null;
    }

    /**
     * Constructs the command to traverse up from the current directory.
     */
    public ChangeDirectoryCommand() {
        nextDirectoryName = null;
        directory = null;
    }

    /**
     * Constructs the command to traverse to a specified directory.
     *
     * @param directory
     *  The directory to traverse to
     */
    public ChangeDirectoryCommand(Directory directory) {
        nextDirectoryName = null;
        this.directory = directory;
    }

    /**
     * Executes the Change Directory Command to traverse up or down the current Directory, or to a specified Directory.
     *
     * @return
     *  The Command result of the execution
     */
    @Override
    public CommandResult execute() {
        // Traverse to specified directory
        if (directory != null) {
            try {
                DirectoryTraverser.traverseTo(directory);
                return new CommandResult(null);
            } catch (IncorrectDirectoryLevelException e) {
                return new CommandResult(MESSAGE_TRAVERSE_ERROR);
            }
        }

        // Traverse up or down current directory
        try {
            if ((nextDirectoryName != null)) {
                Directory nextDirectory = DirectoryTraverser.findNextDirectory(nextDirectoryName);
                DirectoryTraverser.traverseDown(nextDirectory);
            } else {
                DirectoryTraverser.traverseUp();
            }
            // No feedback for successful traversal
            return new CommandResult(null);
        } catch (DirectoryTraversalOutOfBoundsException e) {
            return new CommandResult(MESSAGE_FAILED_DIRECTORY_TRAVERSAL);
        } catch (DataNotFoundException e) {
            return new CommandResult(MESSAGE_DIRECTORY_NOT_FOUND);
        }
    }
}

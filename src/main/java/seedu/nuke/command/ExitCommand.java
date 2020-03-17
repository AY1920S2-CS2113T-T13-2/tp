package seedu.nuke.command;

import static seedu.nuke.util.Message.MESSAGE_EXIT;

/**
 * <h3>Exit Command</h3>
 * A <b>Command</b> to exit the <b>Nuke</b> program.
 * @see Command
 */
public class ExitCommand extends Command {
    public static final String COMMAND_WORD = "quit";
    public static final String FORMAT = "quit";
    private static boolean isExit; // To check if user requests to exit the program

    public ExitCommand() {
        isExit = false; // Set to false until user requests to exit the program (after confirmation if necessary)
    }

    /**
     * Executes the <b>Exit Command</b> to exit the <b>Nuke</b> program.
     *
     * @return The <b>Command Result</b> of the execution
     * @see CommandResult
     */
    @Override
    public CommandResult execute() {
        isExit = true;
        return new CommandResult(MESSAGE_EXIT);
    }

    /**
     * Checks if the user has requested to exit the program (after confirmation if necessary).
     *
     * @return <code>TRUE</code> if an exit is requested, and <code>FALSE</code> otherwise
     */
    public static boolean isExit () {
        return isExit; // Returns true if command is exit command
    }
}

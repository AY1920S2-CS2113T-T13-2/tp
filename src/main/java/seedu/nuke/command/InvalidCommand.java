package seedu.nuke.command;

/**
 * <h3>Invalid Command</h3>
 * A <b>Command</b> that is determined to be invalid.
 * @see Command
 */
public class InvalidCommand extends Command {
    private final String message; // Message to be shown to the user

    public InvalidCommand(String message) {
        this.message = message;
    }

    /**
     * Executes the <b>Invalid Command</b> to show the <code>message</code> to the user due to an <i>invalid
     * command</i>.
     *
     * @return The <b>Command Result</b> of the execution
     * @see CommandResult
     */
    @Override
    public CommandResult execute() {
        return new CommandResult(message);
    }
}

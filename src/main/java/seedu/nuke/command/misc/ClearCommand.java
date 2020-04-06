package seedu.nuke.command.misc;

import seedu.nuke.command.Command;
import seedu.nuke.command.CommandResult;
import seedu.nuke.gui.io.GuiExecutor;

/**
 * <h3>Open File Command</h3>
 * A <b>Command</b> to clear the Gui Console Screen.
 *
 * @see Command
 */
public class ClearCommand extends Command {
    public static final String COMMAND_WORD = "clear";
    public static final String FORMAT = COMMAND_WORD;
    public static final String MESSAGE_USAGE = COMMAND_WORD + System.lineSeparator()
            + "Clears the screen"
            + System.lineSeparator() + FORMAT + System.lineSeparator();

    @Override
    public CommandResult execute() {
        GuiExecutor.clearScreen();

        // No feedback to user; simply clears the screen
        return new CommandResult(null);
    }
}


package seedu.nuke.command.misc;

import seedu.nuke.command.Command;
import seedu.nuke.command.CommandResult;
import seedu.nuke.data.ScreenShotManager;
import seedu.nuke.exception.CorruptedFileException;
import seedu.nuke.exception.IncorrectDirectoryLevelException;

import java.io.IOException;
import java.util.EmptyStackException;

import static seedu.nuke.util.ExceptionMessage.MESSAGE_TRAVERSE_ERROR;
import static seedu.nuke.util.Message.MESSAGE_UNDO_SUCCESS;

public class UndoCommand extends Command {
    public static final String COMMAND_WORD = "undo";
    public static final String MESSAGE_UNDO_UNSUCCESSFUL = "Sorry, there was an IO error when undoing the state.\n";
    public static final String MESSAGE_UNDO_AT_BEGINNING = "You are already at the initial state!\n";

    @Override
    public CommandResult execute() {
        try {
            ScreenShotManager.undo();
            return new CommandResult(MESSAGE_UNDO_SUCCESS);
        } catch (IOException | CorruptedFileException e) {
            return new CommandResult(MESSAGE_UNDO_UNSUCCESSFUL);
        } catch (EmptyStackException e) {
            return new CommandResult(MESSAGE_UNDO_AT_BEGINNING);
        } catch (IncorrectDirectoryLevelException e) {
            return new CommandResult(MESSAGE_TRAVERSE_ERROR);
        }
    }
}

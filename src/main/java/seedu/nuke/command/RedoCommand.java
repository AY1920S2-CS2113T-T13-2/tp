package seedu.nuke.command;

import seedu.nuke.data.ScreenShotManager;
import seedu.nuke.exception.CorruptedFileException;

import java.io.IOException;
import java.util.EmptyStackException;

import static seedu.nuke.util.Message.MESSAGE_UNDO_SUCCESS;

public class RedoCommand extends Command {
    public static final String COMMAND_WORD = "redo";
    public static final String MESSAGE_REDO_UNSUCCESSFUL = "Something goes wrong here!\n";
    public static final String MESSAGE_REDO_AT_END = "you are already at the newest state!\n";

    @Override
    public CommandResult execute() {
        try {
            ScreenShotManager.redo();
            return new CommandResult(MESSAGE_UNDO_SUCCESS);
        } catch (IOException | CorruptedFileException e) {
            return new CommandResult(MESSAGE_REDO_UNSUCCESSFUL);
        } catch (EmptyStackException e) {
            return new CommandResult(MESSAGE_REDO_AT_END);
        }
    }
}
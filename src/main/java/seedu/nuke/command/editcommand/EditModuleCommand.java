package seedu.nuke.command.editcommand;

import seedu.nuke.command.Command;
import seedu.nuke.command.CommandResult;
import seedu.nuke.data.ModuleManager;
import seedu.nuke.data.storage.StorageManager;
import seedu.nuke.directory.DirectoryTraverser;
import seedu.nuke.directory.Module;
import seedu.nuke.exception.IncorrectDirectoryLevelException;
import seedu.nuke.exception.ModuleNotProvidedException;

import java.util.regex.Pattern;

import static seedu.nuke.parser.Parser.MODULE_PREFIX;
import static seedu.nuke.util.ExceptionMessage.MESSAGE_DUPLICATE_MODULE;
import static seedu.nuke.util.ExceptionMessage.MESSAGE_INCORRECT_DIRECTORY_LEVEL;
import static seedu.nuke.util.ExceptionMessage.MESSAGE_MODULE_NOT_FOUND;
import static seedu.nuke.util.ExceptionMessage.MESSAGE_MODULE_NOT_PROVIDED;
import static seedu.nuke.util.Message.MESSAGE_EDIT_MODULE_SUCCESS;

/**
 * <h3>Edit Module Command</h3>
 * A <b>Command</b> to edit a <b>Module</b> in the <b>Module List</b>.
 * @see Command
 * @see Module
 */
public class EditModuleCommand extends EditCommand {
    public static final String COMMAND_WORD = "edm";
    public static final String FORMAT = COMMAND_WORD + " <module code> -m <new module code>";
    public static final String MESSAGE_USAGE = COMMAND_WORD + System.lineSeparator() + "Edit module code of module"
            + System.lineSeparator() + FORMAT + System.lineSeparator();
    public static final Pattern REGEX_FORMAT = Pattern.compile(
            "(?<identifier>(?:\\s+\\w\\S*)*)"
            + "(?<moduleCode>(?:\\s+" + MODULE_PREFIX + "(?:\\s+\\w\\S*)+)?)"
            + "(?<invalid>.*)"
    );

    private String oldModuleCode;
    private String newModuleCode;

    /**
     * Constructs the command to edit a module.
     *
     * @param oldModuleCode
     *  The module code of the module to be edited
     * @param newModuleCode
     *  The new module code for the module if any
     */
    public EditModuleCommand(String oldModuleCode, String newModuleCode) {
        this.oldModuleCode = oldModuleCode;
        this.newModuleCode = newModuleCode.toUpperCase();
    }

    /**
     * Executes the <b>Edit Module Command</b> to edit a <b>Module</b> with the <code>module code</code>
     * from the <b>Module List</b>.
     *
     * @return The <b>Command Result</b> of the execution
     * @see Module
     * @see ModuleManager
     * @see CommandResult
     */
    @Override
    public CommandResult execute() {
        try {
            Module toEdit = DirectoryTraverser.getModuleDirectory(oldModuleCode);
            ModuleManager.edit(toEdit, newModuleCode);
            StorageManager.setIsSave();
            return new CommandResult(MESSAGE_EDIT_MODULE_SUCCESS);
        }  catch (ModuleNotProvidedException e) {
            return new CommandResult(MESSAGE_MODULE_NOT_PROVIDED);
        } catch (ModuleManager.ModuleNotFoundException e) {
            return new CommandResult(MESSAGE_MODULE_NOT_FOUND);
        } catch (ModuleManager.DuplicateModuleException e) {
            return new CommandResult(MESSAGE_DUPLICATE_MODULE);
        } catch (IncorrectDirectoryLevelException e) {
            return new CommandResult(MESSAGE_INCORRECT_DIRECTORY_LEVEL);
        }
    }
}

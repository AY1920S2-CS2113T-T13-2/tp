package seedu.nuke.command;

import seedu.nuke.data.ModuleManager;
import seedu.nuke.exception.ModuleNotProvidedException;
import seedu.nuke.module.Module;

import static seedu.nuke.util.ExceptionMessage.MESSAGE_DUPLICATE_MODULE_ADD;
import static seedu.nuke.util.ExceptionMessage.MESSAGE_MODULE_NOT_PROVIDED;
import static seedu.nuke.util.Message.MESSAGE_ADD_MODULE_SUCCESS;

/**
 * <h3>Add Module Command</h3>
 * A <b>Command</b> to add a <b>Module</b> into the <b>Module List</b>.
 * @see Command
 * @see Module
 */
public class AddModuleCommand extends Command {
    public static final String COMMAND_WORD = "addm";
    public static final String MESSAGE_USAGE = COMMAND_WORD+ " <module code>";

    private String moduleCode;

    public AddModuleCommand(String moduleCode) {
        this.moduleCode = moduleCode;
    }

    /**
     * Executes the <b>Add Module Command</b> to add a <b>Module</b> into the <b>Module List</b>.
     *
     * @return The <b>Command Result</b> of the execution
     * @see Module
     * @see CommandResult
     */
    @Override
    public CommandResult execute() {
        try {
            moduleManager.add(moduleCode);
            Module addedModule = moduleManager.getLastAddedModule();
            return new CommandResult(MESSAGE_ADD_MODULE_SUCCESS(addedModule.getModuleCode(), addedModule.getTitle()));
        } catch (ModuleManager.DuplicateModuleException e) {
            return new CommandResult(MESSAGE_DUPLICATE_MODULE_ADD);
        } catch (ModuleNotProvidedException e) {
            return new CommandResult(MESSAGE_MODULE_NOT_PROVIDED);
        }
    }
}

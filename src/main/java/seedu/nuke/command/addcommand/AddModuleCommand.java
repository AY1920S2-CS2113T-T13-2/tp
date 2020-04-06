package seedu.nuke.command.addcommand;

import seedu.nuke.NukeLogger;
import seedu.nuke.command.Command;
import seedu.nuke.command.CommandResult;
import seedu.nuke.data.ModuleManager;
import seedu.nuke.data.storage.StorageManager;
import seedu.nuke.exception.ModuleNotProvidedException;
import seedu.nuke.directory.Module;

import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;

import static seedu.nuke.util.ExceptionMessage.MESSAGE_DUPLICATE_MODULE;
import static seedu.nuke.util.ExceptionMessage.MESSAGE_MODULE_NOT_PROVIDED;
import static seedu.nuke.util.Message.messageAddModuleSuccess;

/**
 * <h3>Add Module Command</h3>
 * A <b>Command</b> to add a <b>Module</b> into the <b>Module List</b>.
 * @see Command
 * @see Module
 */
public class AddModuleCommand extends AddCommand {
    public static final String COMMAND_WORD = "addm";
    public static final String FORMAT = COMMAND_WORD + " <module code>";
    public static final String MESSAGE_USAGE = COMMAND_WORD + System.lineSeparator() + "Add a new module"
            + System.lineSeparator() + FORMAT + System.lineSeparator();
    public static final Pattern REGEX_FORMAT = Pattern.compile(
            "(?<identifier>(?:\\s+\\w\\S*)+)"
            + "(?<invalid>.*)"
    );

    private String moduleCode;

    /**
     * Constructs the command to add a module.
     *
     * @param moduleCode
     *  The module code of the module
     */
    public AddModuleCommand(String moduleCode) {
        this.moduleCode = moduleCode.toUpperCase();
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
        Logger logger = NukeLogger.getLogger();
        //logger.log(Level.INFO, "Add command is being executed.");

        Module toAdd = new Module(moduleCode);
        try {
            ModuleManager.add(toAdd);
            //logger.log(Level.INFO, String.format("Module %s was added into the module list.", moduleCode));
            assert toAdd.getModuleCode().equals(moduleCode) : "Incorrect last added module!";
            StorageManager.setIsSave();
            return new CommandResult(messageAddModuleSuccess(toAdd.getModuleCode(), toAdd.getTitle()));
        } catch (ModuleManager.DuplicateModuleException e) {
            //logger.log(Level.WARNING, String.format("Duplicate module %s attempted to be added.", moduleCode));
            return new CommandResult(MESSAGE_DUPLICATE_MODULE);
        } catch (ModuleNotProvidedException e) {
            //logger.log(Level.WARNING, String.format("Unknown module %s attempted to be added.", moduleCode));
            assert !ModuleManager.getModulesMap().containsKey(moduleCode) :
                    "Incorrect identifying of unprovided module!";
            return new CommandResult(MESSAGE_MODULE_NOT_PROVIDED);
        }
    }
}

package seedu.nuke.command.module;

import seedu.nuke.command.Command;
import seedu.nuke.command.CommandResult;
import seedu.nuke.data.module.Module;
import seedu.nuke.data.module.ModuleList;

import java.util.regex.Pattern;

import static seedu.nuke.parser.Parser.MODULE_CODE_PREFIX;
import static seedu.nuke.util.ExceptionMessage.MESSAGE_DUPLICATE_MODULE;
import static seedu.nuke.util.Message.MESSAGE_ADD_MODULE_SUCCESS;

/**
 * <h3>Add Module Command</h3>
 * A <b>Command</b> to add a <b>Module</b> into the <b>Module List</b>.
 * @see Command
 * @see Module
 * @see ModuleList
 */
public class AddModuleCommand extends Command {
    public static final String COMMAND_WORD = "addm";
    public static final String FORMAT = COMMAND_WORD + " <module code>";
    public static final Pattern[] REGEX_FORMATS = {
            Pattern.compile("(?<identifier>^\\s*([^-]+))"),
            Pattern.compile("(?<invalid>-.+)")
    };

    private String moduleCode;

    public AddModuleCommand(String moduleCode) {
        this.moduleCode = moduleCode;
    }

    /**
     * Executes the <b>Add Module Command</b> to add a <b>Module</b> into the <b>Module List</b>.
     *
     * @return The <b>Command Result</b> of the execution
     * @see Module
     * @see ModuleList
     * @see CommandResult
     */
    @Override
    public CommandResult execute() {
        Module toAdd = new Module(moduleCode, null, null);
        try {
            ModuleList.add(toAdd);
            return new CommandResult(MESSAGE_ADD_MODULE_SUCCESS(toAdd.getModuleCode(), toAdd.getTitle()));
        } catch (ModuleList.DuplicateModuleException e) {
            return new CommandResult(MESSAGE_DUPLICATE_MODULE);
        }
    }
}

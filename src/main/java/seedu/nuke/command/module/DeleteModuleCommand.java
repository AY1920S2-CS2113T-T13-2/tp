package seedu.nuke.command.module;

import seedu.nuke.command.Command;
import seedu.nuke.command.CommandResult;
import seedu.nuke.common.DataType;
import seedu.nuke.data.module.Module;
import seedu.nuke.data.module.ModuleList;
import seedu.nuke.gui.controller.MainController;
import seedu.nuke.gui.io.Executor;

import java.util.ArrayList;
import java.util.regex.Pattern;

import static seedu.nuke.parser.Parser.EXACT_FLAG;
import static seedu.nuke.util.ExceptionMessage.MESSAGE_DELETE_ERROR;
import static seedu.nuke.util.Message.*;

public class DeleteModuleCommand extends Command {
    public static final String COMMAND_WORD = "delm";
    public static final String FORMAT = COMMAND_WORD + " <module code>";
    public static final Pattern[] REGEX_FORMATS = {
            Pattern.compile("(?<identifier>^\\s*([^-]+))"),
            Pattern.compile("(?<exact>(?:" + EXACT_FLAG + ")?)"),
            Pattern.compile("(?<invalid>(?:-(?:[^e].*|[e]\\S+)))")
    };

    private String moduleCode;
    private boolean isExact;

    public DeleteModuleCommand(String moduleCode, boolean isExact) {
        this.moduleCode = moduleCode;
        this.isExact = isExact;
    }

    private CommandResult executeInitialDelete(ArrayList<Module> filteredModules) {
        final int filteredModulesCount = filteredModules.size();
        if (filteredModulesCount == 0) {
            return new CommandResult(MESSAGE_NO_MODULES_FOUND);
        } else if (filteredModulesCount == 1) {
            Executor.preparePromptConfirmation();
            Executor.setFilteredList(filteredModules, DataType.MODULE);
            Module toDelete = filteredModules.get(0);
            return new CommandResult(MESSAGE_CONFIRM_DELETE_MODULE(toDelete));
        } else {
            Executor.preparePromptIndices();
            Executor.setFilteredList(filteredModules, DataType.MODULE);
            return new CommandResult(MESSAGE_PROMPT_DELETE_MODULE_INDICES(filteredModules));
        }
    }


    /**
     * Executes the <b>Delete Module Command</b> to delete a <b>Module</b> with the <code>module code</code> from the
     * <b>Module List</b>.
     *
     * @return The <b>Command Result</b> of the execution
     * @see Module
     * @see ModuleList
     * @see CommandResult
     */
    @Override
    public CommandResult execute() {
        ArrayList<Module> filteredModules =
                isExact ? ModuleList.filterExact(moduleCode) : ModuleList.filter(moduleCode);
        return executeInitialDelete(filteredModules);
    }
}

package seedu.nuke.command.module;

import seedu.nuke.command.Command;
import seedu.nuke.command.CommandResult;
import seedu.nuke.common.DataType;
import seedu.nuke.data.module.Module;
import seedu.nuke.data.module.ModuleList;

import java.util.ArrayList;
import java.util.regex.Pattern;

import static seedu.nuke.parser.Parser.ALL_FLAG;
import static seedu.nuke.parser.Parser.EXACT_FLAG;
import static seedu.nuke.util.Message.MESSAGE_SHOW_LIST;

public class ListModuleCommand extends Command {
    public static final String COMMAND_WORD = "lsm";
    public static final String FORMAT = COMMAND_WORD;
    public static final Pattern[] REGEX_FORMATS = {
            Pattern.compile("(?<identifier>^\\s*([^-]+)?)"),
            Pattern.compile("(?<all>(?:" + ALL_FLAG + ")?)"),
            Pattern.compile("(?<exact>(?:" + EXACT_FLAG + ")?)"),
            Pattern.compile("(?<invalid>(?:-(?:[^ae].*|[ae]\\S+)))")
    };

    private String moduleKeyWord;
    private boolean isExact;

    public ListModuleCommand(String moduleKeyWord, boolean isExact) {
        this.moduleKeyWord = moduleKeyWord;
        this.isExact = isExact;
    }

    @Override
    public CommandResult execute() {
        ArrayList<Module> moduleList =
                isExact ? ModuleList.filterExact(moduleKeyWord) : ModuleList.filter(moduleKeyWord);
        return new CommandResult(MESSAGE_SHOW_LIST, DataType.MODULE, moduleList);
    }
}

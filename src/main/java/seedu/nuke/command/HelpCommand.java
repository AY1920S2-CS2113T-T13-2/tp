package seedu.nuke.command;

import seedu.nuke.command.addcommand.AddCategoryCommand;
import seedu.nuke.command.addcommand.AddFileCommand;
import seedu.nuke.command.addcommand.AddModuleCommand;
import seedu.nuke.command.addcommand.AddTagCommand;
import seedu.nuke.command.addcommand.AddTaskCommand;
import seedu.nuke.command.editcommand.EditCategoryCommand;
import seedu.nuke.command.editcommand.EditModuleCommand;
import seedu.nuke.command.editcommand.EditTaskCommand;
import seedu.nuke.command.editcommand.MarkAsDoneCommand;
import seedu.nuke.command.filtercommand.deletecommand.DeleteCategoryCommand;
import seedu.nuke.command.filtercommand.deletecommand.DeleteModuleCommand;
import seedu.nuke.command.filtercommand.deletecommand.DeleteTagCommand;
import seedu.nuke.command.filtercommand.deletecommand.DeleteTaskCommand;
import seedu.nuke.command.filtercommand.listcommand.ListTaskSortedCommand;
import seedu.nuke.command.filtercommand.listcommand.ListCategoryCommand;
import seedu.nuke.command.filtercommand.listcommand.ListModuleTasksDeadlineCommand;
import seedu.nuke.command.filtercommand.listcommand.ListTaskCommand;
import seedu.nuke.command.misc.ChangeDirectoryCommand;

import java.util.ArrayList;
import java.util.Arrays;

import static seedu.nuke.util.Message.MESSAGE_HELP;

public class HelpCommand extends Command {
    public static final String COMMAND_WORD = "help";
    public static final String FORMAT = COMMAND_WORD;
    public static final String MESSAGE_USAGE =
            String.format("%s\nShow helping guide\n%s\n", COMMAND_WORD, FORMAT);
    private static final ArrayList<String> commands = new ArrayList<>(Arrays.asList(
            ChangeDirectoryCommand.MESSAGE_USAGE,
            AddModuleCommand.MESSAGE_USAGE,
            AddCategoryCommand.MESSAGE_USAGE,
            AddTaskCommand.MESSAGE_USAGE,
            AddTagCommand.MESSAGE_USAGE,
            AddFileCommand.MESSAGE_USAGE,
            EditModuleCommand.MESSAGE_USAGE,
            EditCategoryCommand.MESSAGE_USAGE,
            EditTaskCommand.MESSAGE_USAGE,
            MarkAsDoneCommand.MESSAGE_USAGE,
            DeleteModuleCommand.MESSAGE_USAGE,
            DeleteCategoryCommand.MESSAGE_USAGE,
            DeleteTaskCommand.MESSAGE_USAGE,
            DeleteTagCommand.MESSAGE_USAGE,
            ListCategoryCommand.MESSAGE_USAGE,
            ListTaskCommand.MESSAGE_USAGE,
            ListModuleTasksDeadlineCommand.MESSAGE_USAGE,
            ListTaskSortedCommand.MESSAGE_USAGE,
            HelpCommand.MESSAGE_USAGE,
            ExitCommand.MESSAGE_USAGE
    ));


    public HelpCommand() {
    }

    @Override
    public CommandResult execute() {
        return new CommandResult(MESSAGE_HELP, commands);
    }
}

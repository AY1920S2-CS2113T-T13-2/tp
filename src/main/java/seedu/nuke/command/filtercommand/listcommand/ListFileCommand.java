package seedu.nuke.command.filtercommand.listcommand;

import seedu.nuke.command.CommandResult;
import seedu.nuke.directory.DirectoryLevel;
import seedu.nuke.directory.Task;
import seedu.nuke.directory.TaskFile;

import java.util.ArrayList;

import static seedu.nuke.util.Message.MESSAGE_NO_FILES_TO_SHOW;
import static seedu.nuke.util.Message.MESSAGE_SHOW_LIST;

public class ListFileCommand extends ListCommand {
    public static final String COMMAND_WORD = "lsf";
    public static final String FORMAT = "lsf [ <file keyword> -f <task keyword> -c <category keyword> "
            + "-m <module keyword> -e -a ]";

    private String moduleKeyWord;
    private String categoryKeyword;
    private String taskKeyword;
    private String fileKeyword;
    private boolean isExact;
    private boolean isAll;

    /**
     * Constructs the command to list tasks.
     *
     * @param moduleKeyWord
     *  The keyword to filter the modules
     * @param categoryKeyword
     *  The keyword to filter the categories
     * @param taskKeyword
     *  The keyword to filter the tasks
     * @param fileKeyword
     *  The keyword to filter the files
     * @param isExact
     *  Checks if tasks are to be filtered exactly
     * @param isAll
     *  Checks whether to show <b>all</b> tasks across modules and categories
     */
    public ListFileCommand(String moduleKeyWord, String categoryKeyword, String taskKeyword, String fileKeyword,
                           boolean isExact, boolean isAll) {
        this.moduleKeyWord = moduleKeyWord;
        this.categoryKeyword = categoryKeyword;
        this.taskKeyword = taskKeyword;
        this.fileKeyword = fileKeyword;
        this.isExact = isExact;
        this.isAll = isAll;
    }

    /**
     * Executes the <b>List Task Command</b> to show a filtered list of modules.
     *
     * @return The <b>Command Result</b> of the execution
     * @see Task
     * @see CommandResult
     */
    @Override
    public CommandResult execute() {
        ArrayList<TaskFile> filteredFileList =
                createFilteredFileList(moduleKeyWord, categoryKeyword, taskKeyword, fileKeyword, isExact, isAll);
        if (filteredFileList.isEmpty()) {
            return new CommandResult(MESSAGE_NO_FILES_TO_SHOW);
        }
        sortFileList(filteredFileList);
        return new CommandResult(MESSAGE_SHOW_LIST, DirectoryLevel.FILE, new ArrayList<>(filteredFileList));
    }
}

package seedu.nuke.command.filtercommand.deletecommand;

import seedu.nuke.Executor;
import seedu.nuke.command.Command;
import seedu.nuke.command.CommandResult;
import seedu.nuke.directory.Directory;
import seedu.nuke.directory.DirectoryLevel;
import seedu.nuke.directory.TaskFile;

import java.util.ArrayList;

import static seedu.nuke.util.Message.MESSAGE_NO_FILES_FOUND;
import static seedu.nuke.util.Message.messageConfirmDeleteFile;
import static seedu.nuke.util.Message.messagePromptDeleteFileIndices;

/**
 * <h3>Delete File Command</h3>
 * A <b>Command</b> to delete <b>Files(s)</b> from the <b>File List</b>.
 * @see Command
 * @see TaskFile
 */
public class DeleteFileCommand extends DeleteCommand {
    public static final String COMMAND_WORD = "delf";
    public static final String FORMAT = COMMAND_WORD + " <file name> -m <module code> "
            + "-c <category name> -t <task description> [ -e -a ]";

    private String moduleCode;
    private String categoryName;
    private String taskDescription;
    private String fileName;
    private boolean isExact;
    private boolean isAll;

    /**
     * Constructs the command to delete a file.
     *
     * @param moduleCode
     *  The module code of the module that has the category to delete the task
     * @param categoryName
     *  The name of the category to delete the task
     * @param taskDescription
     *  The description of the task
     * @param fileName
     *  The name of the file
     * @param isExact
     *  Checks if categories are to be filtered exactly
     * @param isAll
     *  Checks if filtering is to be done across all modules and categories
     */
    public DeleteFileCommand(String moduleCode, String categoryName, String taskDescription, String fileName,
             boolean isExact, boolean isAll) {
        this.moduleCode = moduleCode;
        this.categoryName = categoryName;
        this.taskDescription = taskDescription;
        this.fileName = fileName;
        this.isExact = isExact;
        this.isAll = isAll;
    }

    /**
     *  Executes the initial phase of the file deletion process depending on the number of files filtered.
     *  If there are no files in the filtered list, then the deletion ends.
     *  Otherwise, if there is one, there will be a prompt to request confirmation.
     *  If there are multiple files instead, there will be a prompt to choose which files to delete.
     *
     * @param filteredFiles
     *  The filtered list of files containing possibly the files to be deleted
     * @return
     *  The result of the execution
     */
    @Override
    protected CommandResult executeInitialDelete(ArrayList<Directory> filteredFiles) {
        final int fileCount = filteredFiles.size();
        if (fileCount == 0) {
            return new CommandResult(MESSAGE_NO_FILES_FOUND);
        } else if (fileCount == 1) {
            Executor.preparePromptConfirmation();
            Executor.setFilteredList(filteredFiles, DirectoryLevel.FILE);
            TaskFile toDelete = (TaskFile) filteredFiles.get(0);
            return new CommandResult(messageConfirmDeleteFile(toDelete));
        } else {
            Executor.preparePromptIndices();
            Executor.setFilteredList(filteredFiles, DirectoryLevel.FILE);
            return new CommandResult(messagePromptDeleteFileIndices(filteredFiles));
        }
    }

    /**
     * Executes the <b>Delete File Command</b> to delete <b>File<(s)</b> with the <code>file name</code>
     * from the <b>File List</b>.
     *
     * @return The <b>Command Result</b> of the execution
     * @see TaskFile
     * @see CommandResult
     */
    @Override
    public CommandResult execute() {
        ArrayList<TaskFile> filteredFiles =
                createFilteredFileList(moduleCode, categoryName, taskDescription, fileName, isExact, isAll);
        sortFileList(filteredFiles);
        return executeInitialDelete(new ArrayList<>(filteredFiles));
    }
}

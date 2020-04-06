package seedu.nuke.command.editcommand;

import seedu.nuke.command.Command;
import seedu.nuke.command.CommandResult;
import seedu.nuke.data.CategoryManager;
import seedu.nuke.data.ModuleManager;
import seedu.nuke.data.TaskFileManager;
import seedu.nuke.data.TaskManager;
import seedu.nuke.data.storage.StorageManager;
import seedu.nuke.directory.DirectoryTraverser;
import seedu.nuke.directory.TaskFile;
import seedu.nuke.exception.IncorrectDirectoryLevelException;

import java.util.regex.Pattern;

import static seedu.nuke.parser.Parser.CATEGORY_PREFIX;
import static seedu.nuke.parser.Parser.FILE_PREFIX;
import static seedu.nuke.parser.Parser.MODULE_PREFIX;
import static seedu.nuke.parser.Parser.TASK_PREFIX;
import static seedu.nuke.util.ExceptionMessage.MESSAGE_CATEGORY_NOT_FOUND;
import static seedu.nuke.util.ExceptionMessage.MESSAGE_DUPLICATE_TASK_FILE;
import static seedu.nuke.util.ExceptionMessage.MESSAGE_INCORRECT_DIRECTORY_LEVEL;
import static seedu.nuke.util.ExceptionMessage.MESSAGE_MODULE_NOT_FOUND;
import static seedu.nuke.util.ExceptionMessage.MESSAGE_TASK_FILE_NOT_FOUND;
import static seedu.nuke.util.ExceptionMessage.MESSAGE_TASK_NOT_FOUND;
import static seedu.nuke.util.Message.MESSAGE_EDIT_TASK_SUCCESS;
import static seedu.nuke.util.Message.MESSAGE_FILE_EXCEED_LIMIT;

/**
 * <h3>Edit File Command</h3>
 * A <b>Command</b> to edit a <b>File</b> in the <b>File List</b>.
 * @see Command
 * @see TaskFile
 */
public class EditFileCommand extends EditCommand {
    public static final String COMMAND_WORD = "edf";
    public static final String FORMAT = COMMAND_WORD
            + " <file name> -m <module code> -c <category name> -t <task description> -f <new file name>";
    public static final Pattern REGEX_FORMAT = Pattern.compile(
            "(?<identifier>(?:\\s+\\w\\S*)*)"
            + "(?<moduleCode>(?:\\s+" + MODULE_PREFIX + "(?:\\s+\\w\\S*)+)?)"
            + "(?<categoryName>(?:\\s+" + CATEGORY_PREFIX + "(?:\\s+\\w\\S*)+)?)"
            + "(?<taskDescription>(?:\\s+" + TASK_PREFIX + "(?:\\s+\\w\\S*)+)?)"
            + "(?<fileInfo>(?:\\s+" + FILE_PREFIX + "(?:\\s+\\w\\S*)+)?)"
            + "(?<invalid>.*)"
    );


    private String oldFileName;
    private String moduleCode;
    private String categoryName;
    private String taskDescription;
    private String newFileName;

    /**
     * Constructs the command to edit a file.
     *
     * @param oldFileName
     *  The name of the file to be edited
     * @param moduleCode
     *  The module code of the module containing the file to be edited
     * @param categoryName
     *  The name of the category containing the file to be edited
     * @param taskDescription
     *  The description of the task containing the file to be edited
     * @param newFileName
     *  The new name of the file
     */
    public EditFileCommand(String oldFileName, String moduleCode, String categoryName, String taskDescription,
               String newFileName) {
        this.oldFileName = oldFileName;
        this.moduleCode = moduleCode;
        this.categoryName = categoryName;
        this.taskDescription = taskDescription;
        this.newFileName = newFileName;
    }

    private boolean exceedLengthLimit() {
        return newFileName.length() > 30;
    }

    /**
     * Executes the <b>Edit File Command</b> to edit a <b>File</b> with the <code>task description</code>
     * from the <b>File List</b>.
     *
     * @return The <b>Command Result</b> of the execution
     * @see TaskFile
     * @see TaskFileManager
     * @see CommandResult
     */
    @Override
    public CommandResult execute() {
        if (exceedLengthLimit()) {
            return new CommandResult(MESSAGE_FILE_EXCEED_LIMIT);
        }
        try {
            TaskFile toEdit =
                    DirectoryTraverser.getFileDirectory(moduleCode, categoryName, taskDescription, oldFileName);
            toEdit.getParent().getFiles().edit(toEdit, newFileName);
            StorageManager.setIsSave();
            return new CommandResult(MESSAGE_EDIT_TASK_SUCCESS);
        } catch (ModuleManager.ModuleNotFoundException e) {
            return new CommandResult(MESSAGE_MODULE_NOT_FOUND);
        } catch (CategoryManager.CategoryNotFoundException e) {
            return new CommandResult(MESSAGE_CATEGORY_NOT_FOUND);
        } catch (TaskManager.TaskNotFoundException e) {
            return new CommandResult(MESSAGE_TASK_NOT_FOUND);
        } catch (TaskFileManager.TaskFileNotFoundException e) {
            return new CommandResult(MESSAGE_TASK_FILE_NOT_FOUND);
        } catch (TaskFileManager.DuplicateTaskFileException e) {
            return new CommandResult(MESSAGE_DUPLICATE_TASK_FILE);
        } catch (IncorrectDirectoryLevelException e) {
            return new CommandResult(MESSAGE_INCORRECT_DIRECTORY_LEVEL);
        }
    }
}

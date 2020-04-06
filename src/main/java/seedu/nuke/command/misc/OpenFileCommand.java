package seedu.nuke.command.misc;

import seedu.nuke.command.Command;
import seedu.nuke.command.CommandResult;
import seedu.nuke.command.filtercommand.FilterCommand;
import seedu.nuke.data.CategoryManager;
import seedu.nuke.data.ModuleManager;
import seedu.nuke.data.TaskFileManager;
import seedu.nuke.data.TaskManager;
import seedu.nuke.directory.DirectoryTraverser;
import seedu.nuke.directory.Task;
import seedu.nuke.directory.TaskFile;
import seedu.nuke.exception.IncorrectDirectoryLevelException;

import java.awt.Desktop;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.regex.Pattern;

import static seedu.nuke.data.storage.StoragePath.TASK_FILE_DIRECTORY_PATH;
import static seedu.nuke.parser.Parser.CATEGORY_PREFIX;
import static seedu.nuke.parser.Parser.MODULE_PREFIX;
import static seedu.nuke.parser.Parser.TASK_PREFIX;
import static seedu.nuke.util.ExceptionMessage.MESSAGE_CATEGORY_NOT_FOUND;
import static seedu.nuke.util.ExceptionMessage.MESSAGE_FILE_IO_EXCEPTION;
import static seedu.nuke.util.ExceptionMessage.MESSAGE_FILE_NOT_FOUND_OPEN;
import static seedu.nuke.util.ExceptionMessage.MESSAGE_INCORRECT_DIRECTORY_LEVEL;
import static seedu.nuke.util.ExceptionMessage.MESSAGE_MODULE_NOT_FOUND;
import static seedu.nuke.util.ExceptionMessage.MESSAGE_TASK_FILE_NOT_FOUND;
import static seedu.nuke.util.ExceptionMessage.MESSAGE_TASK_NOT_FOUND;
import static seedu.nuke.util.Message.MESSAGE_NO_FILES_FOUND;
import static seedu.nuke.util.Message.MESSAGE_OPEN_FILE_SUCCESS;

/**
 * <h3>Open File Command</h3>
 * A <b>Command</b> to open a <b>File</b> to the <b>File List</b>.
 *
 * @see Command
 * @see TaskFile
 */
public class OpenFileCommand extends FilterCommand {
    public static final String COMMAND_WORD = "open";
    public static final String FORMAT = COMMAND_WORD + " [ <file name> ] -m <module code> "
            + "-c <category name> -t <task description>";
    public static final String MESSAGE_USAGE = COMMAND_WORD + System.lineSeparator()
            + "Open the file(s) of a specified task"
            + System.lineSeparator() + FORMAT + System.lineSeparator();
    public static final Pattern REGEX_FORMAT = Pattern.compile(
            "(?<identifier>(?:\\s+\\w\\S*)*)"
            + "(?<moduleCode>(?:\\s+" + MODULE_PREFIX + "(?:\\s+\\w\\S*)+)?)"
            + "(?<categoryName>(?:\\s+" + CATEGORY_PREFIX + "(?:\\s+\\w\\S*)+)?)"
            + "(?<taskDescription>(?:\\s+" + TASK_PREFIX + "(?:\\s+\\w\\S*)+)?)"
            + "(?<invalid>.*)"
    );

    private String moduleCode;
    private String categoryName;
    private String taskDescription;
    private String fileName;

    /**
     * Constructs the command to open a file.
     *
     * @param moduleCode
     *  The module code of the module that has the category to delete the task
     * @param categoryName
     *  The name of the category to delete the task
     * @param taskDescription
     *  The description of the task
     * @param fileName
     *  The name of the file
     */
    public OpenFileCommand(String moduleCode, String categoryName, String taskDescription, String fileName) {
        this.moduleCode = moduleCode;
        this.categoryName = categoryName;
        this.taskDescription = taskDescription;
        this.fileName = fileName;
    }

    /**
     * Filters for the files to be opened.
     *
     * @return
     *  The filtered list of files to be opened
     */
    private ArrayList<TaskFile> filterFilesToOpen()
            throws ModuleManager.ModuleNotFoundException, CategoryManager.CategoryNotFoundException,
            TaskManager.TaskNotFoundException, TaskFileManager.TaskFileNotFoundException,
            IncorrectDirectoryLevelException {
        Task parentTask =  DirectoryTraverser.getTaskDirectory(moduleCode, categoryName, taskDescription);
        if (!fileName.isEmpty()) {
            // If file name is given, open the specified file
            return new ArrayList<>(Collections.singletonList(parentTask.getFiles().getFile(fileName)));
        } else {
            // Open all files in the task
            return parentTask.getFiles().getFileList();
        }
    }

    /**
     * Opens the files in the filtered list.
     *
     * @param filesToOpen
     *  The list of files to be opened
     * @throws IOException
     *  If there is an error opening the file(s)
     */
    private void openFiles(ArrayList<TaskFile> filesToOpen) throws IOException {
        if (!Desktop.isDesktopSupported()) {
            throw new IOException();
        }
        Desktop desktop = Desktop.getDesktop();

        ArrayList<String> nonExistentFiles = new ArrayList<>();

        for (TaskFile file : filesToOpen) {
            String filePath = String.format("%s/%s",  TASK_FILE_DIRECTORY_PATH, file.getFilePath());
            File[] filesInDirectory = new File(filePath).listFiles();
            if (filesInDirectory == null || filesInDirectory.length == 0) {
                nonExistentFiles.add(file.getFileName());
                continue;
            }
            // Get the first file in the list
            File toOpen = filesInDirectory[0];
            if (!toOpen.exists() || !toOpen.isFile()) {
                nonExistentFiles.add(file.getFileName());
            } else {
                desktop.open(toOpen);
            }
        }

        if (!nonExistentFiles.isEmpty()) {
            String fileNames = String.join(", ", nonExistentFiles);
            throw new FileNotFoundException(fileNames);
        }
    }

    /**
     * Executes the <b>Open File Command</b> to open <b>File<(s)</b> with the <code>file name</code>
     * from the <b>File List</b>.
     *
     * @return The <b>Command Result</b> of the execution
     * @see TaskFile
     * @see CommandResult
     */
    @Override
    public CommandResult execute() {
        try {
            ArrayList<TaskFile> filesToOpen = filterFilesToOpen();
            if (filesToOpen.isEmpty()) {
                return new CommandResult(MESSAGE_NO_FILES_FOUND);
            }
            openFiles(filesToOpen);
            return new CommandResult(MESSAGE_OPEN_FILE_SUCCESS);
        } catch (ModuleManager.ModuleNotFoundException e) {
            return new CommandResult(MESSAGE_MODULE_NOT_FOUND);
        } catch (CategoryManager.CategoryNotFoundException e) {
            return new CommandResult(MESSAGE_CATEGORY_NOT_FOUND);
        } catch (TaskManager.TaskNotFoundException e) {
            return new CommandResult(MESSAGE_TASK_NOT_FOUND);
        } catch (TaskFileManager.TaskFileNotFoundException e) {
            return new CommandResult(MESSAGE_TASK_FILE_NOT_FOUND);
        } catch (IncorrectDirectoryLevelException e) {
            return new CommandResult(MESSAGE_INCORRECT_DIRECTORY_LEVEL);
        } catch (FileNotFoundException e) {
            return new CommandResult(String.format("%s%s\n", MESSAGE_FILE_NOT_FOUND_OPEN, e.getMessage()));
        } catch (IOException e) {
            return new CommandResult(MESSAGE_FILE_IO_EXCEPTION);
        }
    }
}

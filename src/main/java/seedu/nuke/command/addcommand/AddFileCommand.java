package seedu.nuke.command.addcommand;

import seedu.nuke.Executor;
import seedu.nuke.command.Command;
import seedu.nuke.command.CommandResult;
import seedu.nuke.data.CategoryManager;
import seedu.nuke.data.ModuleManager;
import seedu.nuke.data.TaskFileManager;
import seedu.nuke.data.TaskManager;
import seedu.nuke.data.storage.StorageManager;
import seedu.nuke.data.storage.StoragePath;
import seedu.nuke.directory.DirectoryTraverser;
import seedu.nuke.directory.Task;
import seedu.nuke.directory.TaskFile;
import seedu.nuke.exception.ExceedLimitException;
import seedu.nuke.exception.IncorrectDirectoryLevelException;
import seedu.nuke.gui.io.GuiExecutor;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.FileSystemException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.SecureRandom;
import java.util.regex.Pattern;

import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;
import static seedu.nuke.parser.Parser.CATEGORY_PREFIX;
import static seedu.nuke.parser.Parser.FILE_PREFIX;
import static seedu.nuke.parser.Parser.MODULE_PREFIX;
import static seedu.nuke.parser.Parser.TASK_PREFIX;
import static seedu.nuke.util.ExceptionMessage.MESSAGE_ADD_FILE_NOT_FOUND;
import static seedu.nuke.util.ExceptionMessage.MESSAGE_CATEGORY_NOT_FOUND;
import static seedu.nuke.util.ExceptionMessage.MESSAGE_DUPLICATE_TASK_FILE;
import static seedu.nuke.util.ExceptionMessage.MESSAGE_FILE_IO_EXCEPTION;
import static seedu.nuke.util.ExceptionMessage.MESSAGE_FILE_SECURITY_EXCEPTION;
import static seedu.nuke.util.ExceptionMessage.MESSAGE_FILE_SYSTEM_EXCEPTION;
import static seedu.nuke.util.ExceptionMessage.MESSAGE_IMPLICIT_FILE_EXCEED_LIMIT;
import static seedu.nuke.util.ExceptionMessage.MESSAGE_INCORRECT_DIRECTORY_LEVEL;
import static seedu.nuke.util.ExceptionMessage.MESSAGE_INVALID_FILE_PATH;
import static seedu.nuke.util.ExceptionMessage.MESSAGE_MISSING_FILE_PATH;
import static seedu.nuke.util.ExceptionMessage.MESSAGE_MODULE_NOT_FOUND;
import static seedu.nuke.util.ExceptionMessage.MESSAGE_TASK_NOT_FOUND;
import static seedu.nuke.util.Message.MESSAGE_NO_FILE_CHOSEN;
import static seedu.nuke.util.Message.MESSAGE_FILE_EXCEED_LIMIT;
import static seedu.nuke.util.Message.messageAddFileSuccess;

/**
 * <h3>Add File Command</h3>
 * A <b>Command</b> to add a <b>File</b> to the <b>File List</b>.
 *
 * @see Command
 * @see TaskFile
 */
public class AddFileCommand extends AddCommand {
    public static final String COMMAND_WORD = "addf";
    public static final String FORMAT = COMMAND_WORD;
    public static final String MESSAGE_USAGE = COMMAND_WORD + System.lineSeparator() + "Add a file to task"
            + System.lineSeparator() + FORMAT + System.lineSeparator();

    public static final Pattern REGEX_FORMAT = Pattern.compile(
            "(?<identifier>(?:\\s+\\w\\S*)*)"
            + "(?<moduleCode>(?:\\s+" + MODULE_PREFIX + "(?:\\s+\\w\\S*)+)?)"
            + "(?<categoryName>(?:\\s+" + CATEGORY_PREFIX + "(?:\\s+\\w\\S*)+)?)"
            + "(?<taskDescription>(?:\\s+" + TASK_PREFIX + "(?:\\s+\\w\\S*)+)?)"
            + "(?<fileInfo>(?:\\s+" + FILE_PREFIX + "(?:\\s+\\w\\S*)+)?)"
            + "(?<invalid>.*)"
    );

    private String moduleCode;
    private String categoryName;
    private String taskDescription;
    private String fileName;
    private String originalFilePath;
    private String filePath;

    /**
     * Constructs the command to add a file.
     *
     * @param moduleCode
     *  The module code of the module that has the category to add the task
     * @param categoryName
     *  The name of the category to add the task
     * @param taskDescription
     *  The priority of the category
     * @param fileName
     *  The name of the file
     * @param filePath
     *  The path to the file
     */
    public AddFileCommand(String moduleCode, String categoryName, String taskDescription,
                          String fileName, String filePath) {
        this.moduleCode = moduleCode;
        this.categoryName = categoryName;
        this.taskDescription = taskDescription;
        this.fileName = fileName;
        this.originalFilePath = filePath;
    }

    /**
     * Generates a random hash of alphanumeric characters of fixed length.
     *
     * @return
     *  The random hash
     */
    private String generateRandomHash() {
        final String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz1234567890";
        final int hashLength = 16;
        StringBuilder hash = new StringBuilder();

        for (int i = 0; i < hashLength; ++i) {
            hash.append(characters.charAt(new SecureRandom().nextInt(characters.length())));
        }

        return hash.toString();
    }

    /**
     * Removes the extension from a file name if present.
     *
     * @param fileName
     *  The name of the file to remove the extension
     * @return
     *  The file name without its extension
     */
    private String removeExtension(String fileName) {
        if (fileName.indexOf(".") > 0) {
            return fileName.substring(0, fileName.lastIndexOf("."));
        }
        return fileName;
    }

    /**
     * Copies file from user-specified path into a folder in the application.
     *
     * @param task
     *  The parent task of the file
     * @throws IOException
     *  If there is an error copying the file
     */
    private void copyFile(Task task) throws IOException, ExceedLimitException,
            TaskFileManager.DuplicateTaskFileException {
        File sourceFile = new File(originalFilePath);
        if (!sourceFile.exists() || !sourceFile.isFile()) {
            throw new FileNotFoundException();
        }

        // Automatically updates new file name
        if (fileName.isEmpty()) {
            fileName = removeExtension(sourceFile.getName());
            if (exceedLengthLimit()) {
                throw new ExceedLimitException();
            }
        }

        // Checks if name is duplicated
        if (isDuplicateName(task, fileName)) {
            throw new TaskFileManager.DuplicateTaskFileException();
        }

        Path sourcePath = sourceFile.toPath();

        String randomHash = generateRandomHash();
        File destinationFile = new File(String.format("%s/%s/%s",
                StoragePath.TASK_FILE_DIRECTORY_PATH, randomHash, sourceFile.getName()));
        Path destinationPath = destinationFile.toPath();
        Files.createDirectories(destinationPath.getParent());

        Files.copy(sourcePath, destinationPath, REPLACE_EXISTING);
        filePath = randomHash;
    }

    private boolean exceedLengthLimit() {
        return fileName.length() > 30;
    }

    private boolean isDuplicateName(Task task, String fileName) {
        return task.getFiles().contains(fileName);
    }

    private void retrieveFile() throws Exception {
        if (originalFilePath.isEmpty()) {
            assert Executor.isGui() : "Must be using a Gui in order to retrieve file!";

            String[] chosenFileInformation = GuiExecutor.executeFileChooser();
            if (chosenFileInformation == null) {
                throw new Exception(MESSAGE_NO_FILE_CHOSEN);
            }
            originalFilePath = chosenFileInformation[0];
            if (fileName.isEmpty()) {
                fileName = chosenFileInformation[1];
            }
        }
    }


    /**
     * Executes the <b>Add File Command</b> to add a <b>File</b> into the <b>File List</b>.
     *
     * @return The <b>Command Result</b> of the execution
     * @see TaskFile
     * @see CommandResult
     */
    @Override
    public CommandResult execute() {
        if (exceedLengthLimit()) {
            return new CommandResult(MESSAGE_FILE_EXCEED_LIMIT);
        }
        if (originalFilePath.isEmpty() && !Executor.isGui()) {
            return new CommandResult(MESSAGE_MISSING_FILE_PATH);
        }
        try {
            Task parentTask = DirectoryTraverser.getTaskDirectory(moduleCode, categoryName, taskDescription);
            retrieveFile();

            copyFile(parentTask);
            String fullFilePath = new File(originalFilePath).getAbsolutePath();
            TaskFile toAdd = new TaskFile(parentTask, fileName, filePath, fullFilePath);
            parentTask.getFiles().add(toAdd);
            StorageManager.setIsSave();
            return new CommandResult(messageAddFileSuccess(fileName));
        } catch (ModuleManager.ModuleNotFoundException e) {
            return new CommandResult(MESSAGE_MODULE_NOT_FOUND);
        } catch (CategoryManager.CategoryNotFoundException e) {
            return new CommandResult(MESSAGE_CATEGORY_NOT_FOUND);
        } catch (TaskManager.TaskNotFoundException e) {
            return new CommandResult(MESSAGE_TASK_NOT_FOUND);
        } catch (TaskFileManager.DuplicateTaskFileException e) {
            return new CommandResult(MESSAGE_DUPLICATE_TASK_FILE);
        } catch (IncorrectDirectoryLevelException e) {
            return new CommandResult(MESSAGE_INCORRECT_DIRECTORY_LEVEL);
        } catch (FileNotFoundException e) {
            return new CommandResult(MESSAGE_ADD_FILE_NOT_FOUND);
        } catch (FileSystemException e) {
            return new CommandResult(MESSAGE_FILE_SYSTEM_EXCEPTION);
        } catch (IOException e) {
            return new CommandResult(MESSAGE_FILE_IO_EXCEPTION);
        } catch (IllegalArgumentException e) {
            return new CommandResult(MESSAGE_INVALID_FILE_PATH);
        } catch (SecurityException e) {
            return new CommandResult(MESSAGE_FILE_SECURITY_EXCEPTION);
        } catch (ExceedLimitException e) {
            return new CommandResult(MESSAGE_IMPLICIT_FILE_EXCEED_LIMIT);
        } catch (Exception e) {
            return new CommandResult(e.getMessage());
        }
    }
}

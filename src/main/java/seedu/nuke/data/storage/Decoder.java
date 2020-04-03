package seedu.nuke.data.storage;

import seedu.nuke.directory.Category;
import seedu.nuke.directory.Module;
import seedu.nuke.directory.Task;
import seedu.nuke.directory.TaskFile;
import seedu.nuke.exception.CorruptedFileException;
import seedu.nuke.util.DateTime;
import seedu.nuke.util.DateTimeFormat;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.regex.Pattern;

public class Decoder {
    private static final String BEGIN_MODULE_INDICATOR = "--- MODULE ---";
    private static final String END_MODULE_INDICATOR = "--- END MODULE ---";
    private static final String BEGIN_CATEGORY_INDICATOR = "--- CATEGORY ---";
    private static final String END_CATEGORY_INDICATOR = "--- END CATEGORY ---";
    private static final String BEGIN_TASK_INDICATOR = "--- TASK ---";
    private static final String END_TASK_INDICATOR = "--- END TASK ---";
    private static final String BEGIN_FILE_INDICATOR = "--- FILE ---";
    private static final String END_FILE_INDICATOR = "--- END FILE ---";
    private static final String DELIMITER = " -|||- ";
    private static final String LINE_BREAK = System.lineSeparator();

    private BufferedReader reader;

    public Decoder(BufferedReader reader) {
        this.reader = reader;
    }

    public ArrayList<Module> decode() throws CorruptedFileException, IOException {
        return decodeModuleList();
    }

    private ArrayList<Module> decodeModuleList()
            throws CorruptedFileException, IOException {
        ArrayList<Module> decodedModuleList = new ArrayList<>();
        if (!reader.readLine().equals(BEGIN_MODULE_INDICATOR)) {
            throw new CorruptedFileException();
        }

        String currentLine;
        while ((currentLine = reader.readLine()) != null) {
            if (currentLine.equals(END_MODULE_INDICATOR)) {
                break;
            }
            Module decodedModule = decodeModule(currentLine.split(Pattern.quote(DELIMITER)));

            decodedModuleList.add(decodedModule);
        }
        return decodedModuleList;
    }

    private ArrayList<Category> decodeCategoryList(Module decodedModule)
            throws CorruptedFileException, IOException {
        ArrayList<Category> decodedCategoryList = new ArrayList<>();
        if (!reader.readLine().equals(BEGIN_CATEGORY_INDICATOR)) {
            throw new CorruptedFileException();
        }
        String currentLine;
        while ((currentLine = reader.readLine()) != null) {
            if (currentLine.equals(END_CATEGORY_INDICATOR)) {
                break;
            }
            try {
                Category decodedCategory = decodeCategory(decodedModule, currentLine.split(Pattern.quote(DELIMITER)));
                decodedCategoryList.add(decodedCategory);
            } catch (NumberFormatException e) {
                throw new CorruptedFileException();
            }
        }
        return decodedCategoryList;
    }

    private ArrayList<Task> decodeTaskList(Category decodedCategory)
            throws CorruptedFileException, IOException {
        ArrayList<Task> decodedTaskList = new ArrayList<>();
        if (!reader.readLine().equals(BEGIN_TASK_INDICATOR)) {
            throw new CorruptedFileException();
        }
        String currentLine;
        while ((currentLine = reader.readLine()) != null) {
            if (currentLine.equals(END_TASK_INDICATOR)) {
                break;
            }
            try {
                Task decodedTask = decodeTask(decodedCategory, currentLine.split(Pattern.quote(DELIMITER)));
                decodedTaskList.add(decodedTask);
            } catch (NumberFormatException | DateTimeFormat.InvalidDateTimeException e) {
                throw new CorruptedFileException();
            }
        }
        return decodedTaskList;
    }

    private ArrayList<TaskFile> decodeFileList(Task decodedTask) throws CorruptedFileException, IOException {
        ArrayList<TaskFile> decodedTaskFileList = new ArrayList<>();
        if (!reader.readLine().equals(BEGIN_FILE_INDICATOR)) {
            throw new CorruptedFileException();
        }
        String currentLine;
        while ((currentLine = reader.readLine()) != null) {
            if (currentLine.equals(END_FILE_INDICATOR)) {
                break;
            }
            TaskFile decodedFile = decodeFile(decodedTask, currentLine.split(Pattern.quote(DELIMITER)));

            decodedTaskFileList.add(decodedFile);
        }
        return decodedTaskFileList;
    }

    private Module decodeModule(String[] moduleInformation)
            throws CorruptedFileException, IOException {
        String moduleCode = moduleInformation[0];
        String title = moduleInformation[1];
        String description = moduleInformation[2];
        Module decodedModule = new Module(moduleCode, title, description);
        decodedModule.getCategories().setCategoryList(decodeCategoryList(decodedModule));

        return decodedModule;
    }

    private Category decodeCategory(Module decodedModule, String[] categoryInformation)
            throws CorruptedFileException, IOException {
        String categoryName = categoryInformation[0];
        int categoryPriority = Integer.parseInt(categoryInformation[1]);
        Category decodedCategory = new Category(decodedModule, categoryName, categoryPriority);
        decodedCategory.getTasks().setTaskList(decodeTaskList(decodedCategory));

        return decodedCategory;
    }

    private Task decodeTask(Category decodedCategory, String[] taskInformation)
            throws CorruptedFileException, DateTimeFormat.InvalidDateTimeException, IOException {
        String taskDescription = taskInformation[0];
        boolean doneStatus;
        if (taskInformation[1].equals("Y")) {
            doneStatus = true;
        } else if (taskInformation[1].equals("N")) {
            doneStatus = false;
        } else {
            throw new CorruptedFileException();
        }
        DateTime deadline = (taskInformation[2].isEmpty()) ? new DateTime() :
                DateTimeFormat.stringToDateTime(taskInformation[2]);
        int taskPriority = Integer.parseInt(taskInformation[3]);
        Task decodedTask = new Task(decodedCategory, taskDescription, deadline, taskPriority);
        decodedTask.setDone(doneStatus);
        decodedTask.getFiles().setFileList(decodeFileList(decodedTask));

        return decodedTask;
    }

    private TaskFile decodeFile(Task decodedTask, String[] fileInformation) {
        String fileName = fileInformation[0];
        String filePath = fileInformation[1];
        return new TaskFile(decodedTask, fileName, filePath);
    }
}

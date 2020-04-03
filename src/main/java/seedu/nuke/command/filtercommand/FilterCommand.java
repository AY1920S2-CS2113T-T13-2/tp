package seedu.nuke.command.filtercommand;

import seedu.nuke.command.Command;
import seedu.nuke.data.CategoryManager;
import seedu.nuke.data.ModuleManager;
import seedu.nuke.data.TaskFileManager;
import seedu.nuke.data.TaskManager;
import seedu.nuke.directory.Category;
import seedu.nuke.directory.DirectoryLevel;
import seedu.nuke.directory.DirectoryTraverser;
import seedu.nuke.directory.Module;
import seedu.nuke.directory.Module;
import seedu.nuke.directory.Task;
import seedu.nuke.directory.TaskFile;
import seedu.nuke.exception.IncorrectDirectoryLevelException;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.regex.Pattern;

import static seedu.nuke.parser.Parser.ALL_FLAG;
import static seedu.nuke.parser.Parser.CATEGORY_PREFIX;
import static seedu.nuke.parser.Parser.EXACT_FLAG;
import static seedu.nuke.parser.Parser.MODULE_PREFIX;
import static seedu.nuke.parser.Parser.TASK_PREFIX;

public abstract class FilterCommand extends Command {
    public static final Pattern MODULE_REGEX_FORMAT = Pattern.compile(
            "(?<identifier>(?:\\s+\\w\\S*)*)"
            + "(?<optional>(?:\\s+-[ea])*)"
            + "(?<invalid>.*)"
    );
    public static final Pattern CATEGORY_REGEX_FORMAT = Pattern.compile(
            "(?<identifier>(?:\\s+\\w\\S*)*)"
            + "(?<moduleCode>(?:\\s+" + MODULE_PREFIX + "(?:\\s+\\w\\S*)+)?)"
            + "(?<optional>(?:\\s+-[ea])*)"
            + "(?<invalid>.*)"
    );
    public static final Pattern TASK_REGEX_FORMAT = Pattern.compile(
            "(?<identifier>(?:\\s+\\w\\S*)*)"
            + "(?<moduleCode>(?:\\s+" + MODULE_PREFIX + "(?:\\s+\\w\\S*)+)?)"
            + "(?<categoryName>(?:\\s+" + CATEGORY_PREFIX + "(?:\\s+\\w\\S*)+)?)"
            + "(?<optional>(?:\\s+-[ea])*)"
            + "(?<invalid>.*)"
    );
    public static final Pattern FILE_REGEX_FORMAT = Pattern.compile(
            "(?<identifier>(?:\\s+\\w\\S*)*)"
            + "(?<moduleCode>(?:\\s+" + MODULE_PREFIX + "(?:\\s+\\w\\S*)+)?)"
            + "(?<categoryName>(?:\\s+" + CATEGORY_PREFIX + "(?:\\s+\\w\\S*)+)?)"
            + "(?<taskDescription>(?:\\s+" + TASK_PREFIX + "(?:\\s+\\w\\S*)+)?)"
            + "(?<optional>(?:\\s+-[ea])*)"
            + "(?<invalid>.*)"
    );
    public static final Pattern REGEX_OPTIONAL_FORMAT = Pattern.compile(
            "(?<exact>(?:\\s+" + EXACT_FLAG + ")?)"
            + "(?<all>(?:\\s+" + ALL_FLAG + ")?)"
    );

    protected ArrayList<Module> createFilteredModuleList(String moduleKeyword, boolean isExact) {
        return filterModules(moduleKeyword, isExact);
    }

    protected ArrayList<Category> createFilteredCategoryList(String moduleKeyword, String categoryKeyword,
          boolean isExact, boolean isAll) {
        if (isAll) {
            return filterCategories(moduleKeyword, categoryKeyword, isExact);
        }
        // Filters with respect of current level
        try {
            if (moduleKeyword.isEmpty()
                    && DirectoryTraverser.getCurrentDirectoryLevel().ordinal() >= DirectoryLevel.MODULE.ordinal()) {
                CategoryManager categoryList = DirectoryTraverser.getBaseModule().getCategories();
                return isExact ? categoryList.filterExact(categoryKeyword) : categoryList.filter(categoryKeyword);
            }
            return filterCategories(moduleKeyword, categoryKeyword, isExact);
        } catch (IncorrectDirectoryLevelException e) {
            return filterCategories(moduleKeyword, categoryKeyword, isExact);
        }
    }

    protected ArrayList<Task> createFilteredTaskList(String moduleKeyword, String categoryKeyword,
          String taskKeyword, boolean isExact, boolean isAll) {
        if (isAll) {
            return filterTasks(moduleKeyword, categoryKeyword, taskKeyword, isExact);
        }
        // Filters with respect of current level
        try {
            if (moduleKeyword.isEmpty() && categoryKeyword.isEmpty()
                    && DirectoryTraverser.getCurrentDirectoryLevel().ordinal() >= DirectoryLevel.CATEGORY.ordinal()) {
                TaskManager taskList = DirectoryTraverser.getBaseCategory().getTasks();
                return isExact ? taskList.filterExact(taskKeyword) : taskList.filter(taskKeyword);
            }
            if (moduleKeyword.isEmpty()
                    && DirectoryTraverser.getCurrentDirectoryLevel().ordinal() >= DirectoryLevel.MODULE.ordinal()) {
                CategoryManager categoryList = DirectoryTraverser.getBaseModule().getCategories();
                return isExact ? categoryList.filterExact(categoryKeyword, taskKeyword) :
                        categoryList.filter(categoryKeyword, taskKeyword);
            }
            return filterTasks(moduleKeyword, categoryKeyword, taskKeyword, isExact);
        } catch (IncorrectDirectoryLevelException e) {
            return filterTasks(moduleKeyword, categoryKeyword, taskKeyword, isExact);
        }
    }

    protected ArrayList<TaskFile> createFilteredFileList(String moduleKeyword, String categoryKeyword,
            String taskKeyword, String fileKeyword, boolean isExact, boolean isAll) {
        if (isAll) {
            return filterFiles(moduleKeyword, categoryKeyword, taskKeyword, fileKeyword, isExact);
        }
        // Filters with respect of current level
        try {
            if (moduleKeyword.isEmpty() && categoryKeyword.isEmpty() && taskKeyword.isEmpty()
                    && DirectoryTraverser.getCurrentDirectoryLevel().ordinal() >= DirectoryLevel.TASK.ordinal()) {
                TaskFileManager fileList = DirectoryTraverser.getBaseTask().getFiles();
                return isExact ? fileList.filterExact(fileKeyword) : fileList.filter(fileKeyword);
            }
            if (moduleKeyword.isEmpty() && categoryKeyword.isEmpty()
                    && DirectoryTraverser.getCurrentDirectoryLevel().ordinal() >= DirectoryLevel.CATEGORY.ordinal()) {
                TaskManager taskList = DirectoryTraverser.getBaseCategory().getTasks();
                return isExact ? taskList.filterExact(taskKeyword, fileKeyword) :
                        taskList.filter(taskKeyword, fileKeyword);
            }
            if (moduleKeyword.isEmpty()
                    && DirectoryTraverser.getCurrentDirectoryLevel().ordinal() >= DirectoryLevel.MODULE.ordinal()) {
                CategoryManager categoryList = DirectoryTraverser.getBaseModule().getCategories();
                return isExact ? categoryList.filterExact(categoryKeyword, taskKeyword, fileKeyword) :
                        categoryList.filter(categoryKeyword, taskKeyword, fileKeyword);
            }
            return filterFiles(moduleKeyword, categoryKeyword, taskKeyword, fileKeyword, isExact);
        } catch (IncorrectDirectoryLevelException e) {
            return filterFiles(moduleKeyword, categoryKeyword, taskKeyword, fileKeyword, isExact);
        }
    }

    private ArrayList<Module> filterModules(String moduleKeyword, boolean isExact) {
        return isExact ? ModuleManager.filterExact(moduleKeyword) : ModuleManager.filter(moduleKeyword);
    }

    private ArrayList<Category> filterCategories(String moduleKeyword, String categoryKeyword, boolean isExact) {
        return isExact ? ModuleManager.filterExact(moduleKeyword, categoryKeyword) :
                ModuleManager.filter(moduleKeyword, categoryKeyword);
    }

    private ArrayList<Task> filterTasks(String moduleKeyword, String categoryKeyword, String taskKeyword,
                                             boolean isExact) {
        return isExact ? ModuleManager.filterExact(moduleKeyword, categoryKeyword, taskKeyword) :
                ModuleManager.filter(moduleKeyword, categoryKeyword, taskKeyword);
    }

    private ArrayList<TaskFile> filterFiles(String moduleKeyword, String categoryKeyword, String taskKeyword,
             String fileKeyword, boolean isExact) {
        return isExact ? ModuleManager.filterExact(moduleKeyword, categoryKeyword, taskKeyword, fileKeyword) :
                ModuleManager.filter(moduleKeyword, categoryKeyword, taskKeyword, fileKeyword);
    }

    /**
     * Sorts modules in a list by their module codes.
     * @param toSort
     *  The list of modules to be sorted
     */
    protected void sortModuleList(ArrayList<Module> toSort) {
        Comparator<Module> sortByModule =
                Comparator.comparing(Module::getModuleCode);

        toSort.sort(sortByModule);
    }

    /**
     * Sorts categories in a list by their names.
     *
     * @param toSort
     *  The list of categories to be sorted
     */
    protected void sortCategoryList(ArrayList<Category> toSort) {
        Comparator<Category> sortByModule =
                Comparator.comparing(category -> category.getParent().getModuleCode());
        Comparator<Category> sortByCategory =
                Comparator.comparing(Category::getCategoryName);

        toSort.sort(sortByModule.thenComparing(sortByCategory));
    }

    /**
     * Sorts tasks in a list by their description, deadline or priority.
     *
     * @param toSort
     *  The list of tasks to be sorted
     */
    protected void sortTaskList(ArrayList<Task> toSort, boolean isSortDeadline, boolean isSortPriority) {
        Comparator<Task> sortByModule =
                Comparator.comparing(task -> task.getParent().getParent().getModuleCode());
        Comparator<Task> sortByCategory =
                Comparator.comparing(task -> task.getParent().getCategoryName());
        Comparator<Task> sortByTask =
                Comparator.comparing(Task::getDescription);
        Comparator<Task> sortByDeadline =
                Comparator.comparing(task -> task.getDeadline().getDateTimeSortFormat());
        Comparator<Task> sortByPriority =
                Comparator.comparing(Task::getPriority, Comparator.reverseOrder());

        if (isSortDeadline) {
            toSort.sort(sortByDeadline.thenComparing(sortByPriority).thenComparing(sortByModule)
                    .thenComparing(sortByCategory).thenComparing(sortByTask));
        } else if (isSortPriority) {
            toSort.sort(sortByPriority.thenComparing(sortByDeadline).thenComparing(sortByModule)
                    .thenComparing(sortByCategory).thenComparing(sortByTask));
        } else {
            toSort.sort(sortByModule.thenComparing(sortByCategory).thenComparing(sortByTask));
        }
    }

    /**
     * Sorts files in a list by their name.
     *
     * @param toSort
     *  The list of files to be sorted
     */
    protected void sortFileList(ArrayList<TaskFile> toSort) {
        Comparator<TaskFile> sortByModule =
                Comparator.comparing(file -> file.getParent().getParent().getParent().getModuleCode());
        Comparator<TaskFile> sortByCategory =
                Comparator.comparing(file -> file.getParent().getParent().getCategoryName());
        Comparator<TaskFile> sortByTask =
                Comparator.comparing(file -> file.getParent().getDescription());
        Comparator<TaskFile> sortByFile =
                Comparator.comparing(TaskFile::getFileName);

        toSort.sort(sortByModule.thenComparing(sortByCategory).thenComparing(sortByTask).thenComparing(sortByFile));
    }
}

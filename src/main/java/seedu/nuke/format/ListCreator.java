package seedu.nuke.format;

import seedu.nuke.data.category.Category;
import seedu.nuke.data.module.Module;
import seedu.nuke.data.task.Task;

import java.util.ArrayList;
import java.util.Comparator;

public class ListCreator {
    private static final String LIST_DIVIDER = String.format("%s%s%s\n", "+", "-".repeat(78), "+");
    private static final String SEPARATOR = "|";

    private static String addSpaces(int numberOfSpaces) {
        return " ".repeat(numberOfSpaces);
    }

    public static void sortModuleList(ArrayList<Module> toSort) {
        Comparator<Module> sortByModule =
                Comparator.comparing(Module::getModuleCode);

        toSort.sort(sortByModule);
    }

    public static void sortCategoryList(ArrayList<Category> toSort) {
        Comparator<Category> sortByModule =
                Comparator.comparing(category -> category.getParentModule().getModuleCode());
        Comparator<Category> sortByCategory =
                Comparator.comparing(Category::getCategoryName);

        toSort.sort(sortByModule.thenComparing(sortByCategory));
    }


    public static void sortTaskList(ArrayList<Task> toSort) {
        Comparator<Task> sortByModule =
                Comparator.comparing(task -> task.getParentCategory().getParentModule().getModuleCode());
        Comparator<Task> sortByCategory =
                Comparator.comparing(task -> task.getParentCategory().getCategoryName());
        Comparator<Task> sortByTask =
                Comparator.comparing(Task::getDescription);

        toSort.sort(sortByModule.thenComparing(sortByCategory).thenComparing(sortByTask));
    }

    public static String createModuleListTable(ArrayList<Module> moduleList) {
        sortModuleList(moduleList);
        StringBuilder moduleListTable = new StringBuilder();

        moduleListTable.append(createModuleListTableHeader());

        int id = 1;
        for (Module module : moduleList) {
            String moduleCode = module.getModuleCode();
            String moduleTitle = (module.getTitle()!=null) ? module.getTitle() : "-NIL-";

            moduleListTable.append(String.format("%s%s%s%s%s\n",
                    centraliseText(fitText(Integer.toString(id++), 4), 4), SEPARATOR,
                    centraliseText(fitText(moduleCode, 16), 16), SEPARATOR,
                    centraliseText(fitText(moduleTitle, 58), 58)
            ));
        }

        moduleListTable.append(LIST_DIVIDER);
        moduleListTable.append(String.format("Total modules: %d\n", moduleList.size()));
        moduleListTable.append(LIST_DIVIDER);

        return moduleListTable.toString();
    }

    private static String createModuleListTableHeader() {
        StringBuilder header = new StringBuilder();

        header.append(LIST_DIVIDER);
        header.append(String.format("%s%s%s%s%s\n",
                centraliseText("ID", 4), SEPARATOR,
                centraliseText("MODULE CODE", 16), SEPARATOR,
                centraliseText("MODULE TITLE", 58)
        ));
        header.append(LIST_DIVIDER);

        return header.toString();
    }

    public static String createCategoryListTable(ArrayList<Category> categoryList) {
        sortCategoryList(categoryList);
        StringBuilder categoryListTable = new StringBuilder();

        categoryListTable.append(createCategoryListTableHeader());

        int id = 1;
        for (Category category : categoryList) {
            String moduleCode = category.getParentModule().getModuleCode();
            String categoryName = category.getCategoryName();

            categoryListTable.append(String.format("%s%s%s%s%s\n",
                    centraliseText(fitText(Integer.toString(id++), 4), 4), SEPARATOR,
                    centraliseText(fitText(moduleCode, 16), 16), SEPARATOR,
                    centraliseText(fitText(categoryName, 58), 58)
            ));
        }

        categoryListTable.append(LIST_DIVIDER);
        categoryListTable.append(String.format("Total categories: %d\n", categoryList.size()));
        categoryListTable.append(LIST_DIVIDER);

        return categoryListTable.toString();
    }

    private static String createCategoryListTableHeader() {
        StringBuilder header = new StringBuilder();

        header.append(LIST_DIVIDER);
        header.append(String.format("%s%s%s%s%s\n",
                centraliseText("ID", 4), SEPARATOR,
                centraliseText("MODULE", 16), SEPARATOR,
                centraliseText("CATEGORY", 58)
        ));
        header.append(LIST_DIVIDER);

        return header.toString();
    }

    public static String createTaskListTable(ArrayList<Task> taskList) {
        sortTaskList(taskList);
        StringBuilder taskListTable = new StringBuilder();

        taskListTable.append(createTaskListTableHeader());

        int id = 1;
        for (Task task : taskList) {
            String moduleCode = task.getParentCategory().getParentModule().getModuleCode();
            String categoryName = task.getParentCategory().getCategoryName();
            String taskDescription = task.getDescription();
            String deadline = (task.getDeadline() != null) ? task.getDeadline().toString() : "-NIL-";

            taskListTable.append(String.format("%s%s%s%s%s%s%s%s%s\n",
                    centraliseText(fitText(Integer.toString(id++), 4), 4), SEPARATOR,
                    centraliseText(fitText(moduleCode, 10), 10), SEPARATOR,
                    centraliseText(fitText(categoryName, 18), 18), SEPARATOR,
                    centraliseText(fitText(taskDescription, 24), 24), SEPARATOR,
                    centraliseText(fitText(deadline, 24), 24)
            ));
        }

        taskListTable.append(LIST_DIVIDER);
        taskListTable.append(String.format("Total tasks: %d\n", taskList.size()));
        taskListTable.append(LIST_DIVIDER);

        return taskListTable.toString();
    }

    private static String createTaskListTableHeader() {
        StringBuilder header = new StringBuilder();

        header.append(LIST_DIVIDER);
        header.append(String.format("%s%s%s%s%s%s%s%s%s\n",
                centraliseText("ID", 4), SEPARATOR,
                centraliseText("MODULE", 10), SEPARATOR,
                centraliseText("CATEGORY", 18), SEPARATOR,
                centraliseText("TASK", 24), SEPARATOR,
                centraliseText("DEADLINE", 20)
        ));
        header.append(LIST_DIVIDER);

        return header.toString();
    }

    private static String centraliseText(String text, int maximumLength) {
        int numberOfLeftSpaces = (maximumLength - text.length()) / 2;
        int numberOfRightSpaces = (maximumLength - text.length() + 1) / 2;
        return addSpaces(numberOfLeftSpaces) + text + addSpaces(numberOfRightSpaces);
    }

    private static String fitText(String text, int maximumLength) {
        return text.length() > maximumLength ? text.substring(0 , maximumLength-3) + "..." : text;
    }
}

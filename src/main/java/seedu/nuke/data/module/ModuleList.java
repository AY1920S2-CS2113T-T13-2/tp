package seedu.nuke.data.module;

import seedu.nuke.data.category.Category;
import seedu.nuke.data.category.CategoryList;
import seedu.nuke.data.task.Task;
import seedu.nuke.data.task.TaskList;
import seedu.nuke.exception.DuplicateDataException;

import java.util.ArrayList;

public class ModuleList {
    /* The module list containing all the modules the user is enrolled in */
    private static ArrayList<Module> moduleList = new ArrayList<>();

    /**
     * @return all moduleList
     */
    public static ArrayList<Module> getModuleList() {
        return moduleList;
    }

    public static Module getModule(String moduleCode) throws ModuleNotFoundException {
        for (Module module : moduleList) {
            if (module.getModuleCode().equals(moduleCode.toUpperCase())) {
                return module;
            }
        }
        throw new ModuleNotFoundException();
    }

    public static Category getCategory(String moduleCode, String categoryName)
            throws ModuleNotFoundException, CategoryList.CategoryNotFoundException {
        return getModule(moduleCode).getCategories().getCategory(categoryName);
    }

    /**
     * Checks if the list contains the same module.
     * @param toCheck The module to check
     * @return true if the task exists
     */
    private static boolean contains(Module toCheck) {
        for (Module module : moduleList) {
            if (module.isSameModule(toCheck)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Add a module to the module List.
     *
     * @param toAdd the module to add
     */
    public static void add(Module toAdd) throws DuplicateModuleException {
        //check duplicate
        if (contains(toAdd)) {
            throw new DuplicateModuleException();
        } else {
            moduleList.add(toAdd);
        }
    }

    /**
     * Clears all tasks in list.
     */
    public void clear() {
        moduleList.clear();
    }

    /**
     * Deletes a single module to the list.
     * @param toDelete
     *  The module to remove
     */
    public static void delete(Module toDelete) {
        moduleList.remove(toDelete);
    }



    /**
     * Deletes a <b>Module</b> with the specified <code>module code</code> in the <b>Module List</b>.
     *
     * @param moduleCode    The module code of the <b>Module</b> to be deleted
     * @throws ModuleNotFoundException  If the module with the specified module code is not found in the <b>Module List</b>
     * @see Module
     */
    public static Module delete(String moduleCode) throws ModuleNotFoundException {
        Module toDelete = getModule(moduleCode);
        moduleList.remove(toDelete);
        return toDelete;
    }

    /* Retrieve a specific Data (Category / Task / File) List. Only 1 list is retrieved */
    public static CategoryList retrieve(String moduleCode) throws ModuleNotFoundException {
        return getModule(moduleCode).getCategories();
    }

    public static TaskList retrieve(String moduleCode, String categoryName)
            throws ModuleNotFoundException, CategoryList.CategoryNotFoundException {
        return retrieve(moduleCode).retrieve(categoryName);
    }

    /* Filters for data (module / task / category) that *contains* given keywords (i.e. not exact match)
    *  in a case-insensitive manner. There may be multiple data that matches. */
    public static ArrayList<Module> filter(String moduleKeyword) {
        ArrayList<Module> filteredModuleList = new ArrayList<>();
        for (Module module : moduleList) {
            if (module.getModuleCode().toLowerCase().contains(moduleKeyword.toLowerCase())) {
                filteredModuleList.add(module);
            }
        }
        return filteredModuleList;
    }

    public static ArrayList<Category> filter(String moduleKeyword, String categoryKeyword) {
        ArrayList<Category> filteredCategoryList = new ArrayList<>();
        for (Module module : filter(moduleKeyword)) {
            filteredCategoryList.addAll(module.getCategories().filter(categoryKeyword));
        }
        return filteredCategoryList;
    }

    public static ArrayList<Task> filter(String moduleKeyword, String categoryKeyword, String taskKeyword) {
        ArrayList<Task> filteredTaskList = new ArrayList<>();
        for (Category category : filter(moduleKeyword, categoryKeyword)) {
            filteredTaskList.addAll(category.getTasks().filter(taskKeyword));
        }
        return filteredTaskList;
    }

    /* Filters for data (module / task / category) that *matches exactly* the given keywords in a case-insensitive
     *  manner. Empty keywords, however, will instead collect all instances of the data.
     *  There may be multiple data that matches. */
    public static ArrayList<Module> filterExact(String moduleKeyword) {
        // Returns all modules in the Module List if no keyword is provided.
        if (moduleKeyword.isEmpty()) {
            return getModuleList();
        }

        ArrayList<Module> filteredModuleList = new ArrayList<>();
        for (Module module : moduleList) {
            if (module.getModuleCode().toLowerCase().equals(moduleKeyword.toLowerCase())) {
                filteredModuleList.add(module);
            }
        }
        return filteredModuleList;
    }

    public static ArrayList<Category> filterExact(String moduleKeyword, String categoryKeyword) {
        ArrayList<Category> filteredCategoryList = new ArrayList<>();
        for (Module module : filterExact(moduleKeyword)) {
            filteredCategoryList.addAll(module.getCategories().filterExact(categoryKeyword));
        }
        return filteredCategoryList;
    }

    public static ArrayList<Task> filterExact(String moduleKeyword, String categoryKeyword, String taskKeyword) {
        ArrayList<Task> filteredTaskList = new ArrayList<>();
        for (Category category : filterExact(moduleKeyword, categoryKeyword)) {
            filteredTaskList.addAll(category.getTasks().filterExact(taskKeyword));
        }
        return filteredTaskList;
    }

    public static class ModuleNotFoundException extends Exception {}
    public static class DuplicateModuleException extends DuplicateDataException {}
}

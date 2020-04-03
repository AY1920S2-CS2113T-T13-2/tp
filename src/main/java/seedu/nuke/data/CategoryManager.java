package seedu.nuke.data;

import seedu.nuke.directory.Category;
import seedu.nuke.directory.Module;
import seedu.nuke.directory.Task;
import seedu.nuke.directory.TaskFile;
import seedu.nuke.exception.DataNotFoundException;
import seedu.nuke.exception.DuplicateDataException;

import java.util.ArrayList;

public class CategoryManager {
    private ArrayList<Category> categoryList;

    private static final String NO_KEYWORD = "";

    public CategoryManager() {
        categoryList = new ArrayList<>();
    }

    /**
     * Constructs the Category Manager with initialised categories.
     *
     * @param parentModule
     *  The parent module of the category in the directory
     */
    public CategoryManager(Module parentModule) {
        categoryList = initialiseCategories(parentModule);
    }

    /**
     * Initialises the Category List with common default categories.
     *
     * @param parentModule
     *  The parent module of the category in the directory
     * @return
     *  The Category List with the initialised categories
     */
    public ArrayList<Category> initialiseCategories(Module parentModule) {
        ArrayList<Category> initialTaskCategories = new ArrayList<>();
        initialTaskCategories.add(new Category(parentModule, "Lecture", 1));
        initialTaskCategories.add(new Category(parentModule, "Tutorial", 2));
        initialTaskCategories.add(new Category(parentModule, "Assignment", 4));
        initialTaskCategories.add(new Category(parentModule, "Lab", 3));

        return initialTaskCategories;
    }

    /**
     * Returns the entire Category List.
     *
     * @return
     *  The Category List
     */
    public ArrayList<Category> getCategoryList() {
        return categoryList;
    }

    /**
     * Sets the entire Category List to a new list.
     *
     * @param categoryList
     *  The new Category List to be set
     */
    public void setCategoryList(ArrayList<Category> categoryList) {
        this.categoryList = categoryList;
    }

    /**
     * Finds a task with the specified category name in the Category List.
     *
     * @param categoryName
     *  The name of the category to be found
     * @return
     *  The found category with the specified name
     * @throws CategoryNotFoundException
     *  If the category is not found in the Category List
     */
    public Category getCategory(String categoryName) throws CategoryNotFoundException {
        for (Category category : categoryList) {
            if (category.getCategoryName().equals(categoryName)) {
                return category;
            }
        }
        throw new CategoryNotFoundException();
    }

    /**
     * Searches the Category List for the category with the specified category name, then searches the category's
     * Task List for the task with the specified description.
     *
     * @param categoryName
     *  The name of the category to be found
     * @param taskDescription
     *  The description of the task to be found
     * @return
     *  The found task with the specified description
     * @throws CategoryNotFoundException
     *  If the task is not found in the Task List
     */
    public Task getTask(String categoryName, String taskDescription)
            throws CategoryNotFoundException, TaskManager.TaskNotFoundException {
        return getCategory(categoryName).getTasks().getTask(taskDescription);
    }

    /**
     * Checks for duplicates of the same category name in the Category List.
     * @param categoryName
     *  The category name to check
     * @return
     *  <code>TRUE</code> if there exists a duplicate, and <code>FALSE</code> otherwise
     */
    private boolean contains(String categoryName) {
        for (Category category : categoryList) {
            if (category.isSameCategory(categoryName)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Add a category to the Category List.
     *
     * @param toAdd
     *  The category to be added
     */
    public void add(Category toAdd) throws DuplicateCategoryException {
        if (contains(toAdd.getCategoryName())) {
            throw new DuplicateCategoryException();
        } else {
            categoryList.add(toAdd);
        }
    }

    /**
     * Deletes the specified category from the Category List.
     *
     * @param toDelete
     *  The category to be deleted
     */
    public boolean delete(Category toDelete) {
        return categoryList.remove(toDelete);
    }

    /**
     * Deletes a <b>Category</b> with the specified <code>category name</code> from the <b>Category List</b>.
     *
     * @param categoryName
     *  The category name of the <b>Category</b> to be deleted
     * @throws CategoryNotFoundException
     *  If the category with the specified category name is not found in the <b>Category List</b>
     * @see Category
     */
    public Category delete(String categoryName) throws CategoryNotFoundException {
        Category toDelete = getCategory(categoryName);
        categoryList.remove(toDelete);
        return toDelete;
    }

    /**
     * Edits a category in the Category List.
     *
     * @param toEdit
     *  The category to be edited
     * @param newCategoryName
     *  The new category name of the category
     * @param newPriority
     * The new priority of the category
     * @throws DuplicateCategoryException
     *  If there are duplicate categories with the same category name as the new category name in the Category List
     */
    public void edit(Category toEdit, String newCategoryName, int newPriority)
            throws DuplicateCategoryException {
        if (!toEdit.isSameCategory(newCategoryName) && contains(newCategoryName)) {
            throw new DuplicateCategoryException();
        }
        toEdit.setCategoryName(newCategoryName);
        toEdit.setCategoryPriority(newPriority);
    }

    /**
     * Retrieves the Task List of the category with the specified name.
     *
     * @param categoryName
     *  The name of the category to retrieve the Task List from
     * @return
     *  The Task List of the found category
     * @throws CategoryNotFoundException
     *  If the category with the specified name is not found in the Category List
     */
    public TaskManager retrieveList(String categoryName) throws CategoryNotFoundException {
        return getCategory(categoryName).getTasks();
    }

    /**
     * Filter for categories in the Category List with name that contains the specified keyword.
     * Filtering is done in a case-insensitive manner.
     *
     * @param categoryKeyword
     *  The keyword to filter the categories
     * @return
     *  The list of filtered categories
     */
    public ArrayList<Category> filter(String categoryKeyword) {
        ArrayList<Category> filteredCategoryList = new ArrayList<>();
        for (Category category : categoryList) {
            if (category.getCategoryName().toLowerCase().contains(categoryKeyword.toLowerCase())) {
                filteredCategoryList.add(category);
            }
        }
        return filteredCategoryList;
    }

    /**
     * Filter for categories in the Category List that contains the specified category keyword, then for tasks in
     * the Task List of the filtered categories with description that contains the specified task keyword.
     * Filtering is done in a case-insensitive manner.
     *
     * @param categoryKeyword
     *  The keyword to filter the categories
     * @param taskKeyword
     * The keyword to filter the tasks
     * @return
     *  The list of filtered tasks
     */
    public ArrayList<Task> filter(String categoryKeyword, String taskKeyword) {
        ArrayList<Task> filteredTaskList = new ArrayList<>();
        for (Category category : filter(categoryKeyword)) {
            filteredTaskList.addAll(category.getTasks().filter(taskKeyword));
        }
        return filteredTaskList;
    }

    /**
     * Filter for categories in the Category List that contains the specified category keyword, then for tasks in
     * the Task List of the filtered categories with description that contains the specified task keyword, then for
     * files in the File List that contains the specified file keyword.
     * Filtering is done in a case-insensitive manner.
     *
     * @param categoryKeyword
     *  The keyword to filter the categories
     * @param taskKeyword
     * The keyword to filter the tasks
     * @param fileKeyword
     * The keyword to filter the files
     * @return
     *  The list of filtered files
     */
    public ArrayList<TaskFile> filter(String categoryKeyword, String taskKeyword, String fileKeyword) {
        ArrayList<TaskFile> filteredFileList = new ArrayList<>();
        for (Task task : filter(categoryKeyword, taskKeyword)) {
            filteredFileList.addAll(task.getFiles().filter(fileKeyword));
        }
        return filteredFileList;
    }

    /**
     * Filter for categories in the Category List with name that matches <b>exactly</b> the specified keyword.
     * Filtering is done in a case-insensitive manner.
     *
     * @param categoryKeyword
     *  The keyword to filter the categories
     * @return
     *  The list of filtered categories
     */
    public ArrayList<Category> filterExact(String categoryKeyword) {
        // Returns all categories in the Category List if no keyword is provided.
        if (categoryKeyword.equals(NO_KEYWORD)) {
            return this.getCategoryList();
        }

        ArrayList<Category> filteredCategoryList = new ArrayList<>();
        for (Category category : categoryList) {
            if (category.getCategoryName().toLowerCase().equals(categoryKeyword.toLowerCase())) {
                filteredCategoryList.add(category);
            }
        }
        return filteredCategoryList;
    }

    /**
     * Filter for categories in the Category List  with name that that matches <b>exactly</b> the specified category
     * keyword, then for tasks in the Task List of the filtered categories with description that that matches
     * <b>exactly</b> the specified task keyword.
     * Filtering is done in a case-insensitive manner.
     *
     * @param categoryKeyword
     *  The keyword to filter the categories
     * @param taskKeyword
     *  The keyword to filter the tasks
     * @return
     *  The list of filtered tasks
     */
    public ArrayList<Task> filterExact(String categoryKeyword, String taskKeyword) {
        ArrayList<Task> filteredTaskList = new ArrayList<>();
        for (Category category : filterExact(categoryKeyword)) {
            filteredTaskList.addAll(category.getTasks().filterExact(taskKeyword));
        }
        return filteredTaskList;
    }

    /**
     * Filter for categories in the Category List  with name that that matches <b>exactly</b> the specified category
     * keyword, then for tasks in the Task List of the filtered categories with description that that matches
     * <b>exactly</b> the specified task keyword, then for files in the File List of the filtered tasks with name
     * that matches <b>exactly</b> the specified file keyword.
     * Filtering is done in a case-insensitive manner.
     *
     * @param categoryKeyword
     *  The keyword to filter the categories
     * @param taskKeyword
     *  The keyword to filter the tasks
     * @param fileKeyword
     *  The keyword to filter the files
     * @return
     *  The list of filtered files
     */
    public ArrayList<TaskFile> filterExact(String categoryKeyword, String taskKeyword, String fileKeyword) {
        ArrayList<TaskFile> filteredFileList = new ArrayList<>();
        for (Task task : filterExact(categoryKeyword, taskKeyword)) {
            filteredFileList.addAll(task.getFiles().filterExact(fileKeyword));
        }
        return filteredFileList;
    }

    public static class CategoryNotFoundException extends DataNotFoundException {
    }

    public static class DuplicateCategoryException extends DuplicateDataException {
    }
}

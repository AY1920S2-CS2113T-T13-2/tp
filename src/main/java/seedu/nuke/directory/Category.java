package seedu.nuke.directory;

import seedu.nuke.data.TaskManager;

public class Category extends Directory {
    private String categoryName;
    private int categoryPriority;
    private TaskManager tasks;

    /**
     * Constructs the category.
     *
     * @param parentModule
     *  The parent module of the category
     * @param categoryName
     *  The name of the category
     * @param categoryPriority
     *  The priority of the category
     */
    public Category(Module parentModule, String categoryName, int categoryPriority) {
        super(parentModule);
        this.categoryName = categoryName;
        this.categoryPriority = categoryPriority;
        this.tasks = new TaskManager();
    }

    /**
     * Returns the name of the category.
     *
     * @return
     *  The name of the category
     */
    public String getCategoryName() {
        return categoryName;
    }

    /**
     * Returns the priority of the category.
     *
     * @return
     *  The priority of the category
     */
    public int getCategoryPriority() {
        return categoryPriority;
    }

    /**
     * Returns the Task List of the category.
     *
     * @return
     *  The Task List of the category
     */
    public TaskManager getTasks() {
        return tasks;
    }

    @Override
    public Module getParent() {
        return (Module) this.parent;
    }

    @Override
    public DirectoryLevel getLevel() {
        return DirectoryLevel.CATEGORY;
    }

    /**
     * Edit the name of the category.
     *
     * @param categoryName
     *  The name of the category
     */
    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    /**
     * Edit the priority of the category.
     *
     * @param categoryPriority
     *  The priority of the category
     */
    public void setCategoryPriority(int categoryPriority) {
        this.categoryPriority = categoryPriority;
    }

    /**
     * Edit the Task List of the category.
     *
     * @param tasks
     *  The Task List of the category
     */
    public void setTasks(TaskManager tasks) {
        this.tasks = tasks;
    }

    /**
     * Checks if one category has the same category name as another.
     *
     * @param categoryName
     *  The category name to check
     * @return
     *  <code>TRUE</code> if they are the same, and <code>FALSE</code> otherwise
     */
    public boolean isSameCategory(String categoryName) {
        return this.categoryName.equals(categoryName);
    }

    /**
     * Returns a string containing the standard Category attributes.
     *
     * @return
     *  A string containing the standard Category attributes
     */
    @Override
    public String toString() {
        return String.format("Category Name: %s\nModule Code: %s\nPriority: %d\nNumber of Tasks: %d\n",
                categoryName, getParent().getModuleCode(), categoryPriority, tasks.getTaskList().size());
    }
}

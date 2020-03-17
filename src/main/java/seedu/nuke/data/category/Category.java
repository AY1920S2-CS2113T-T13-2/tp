package seedu.nuke.data.category;

import seedu.nuke.data.module.Module;
import seedu.nuke.data.task.TaskList;

public class Category {
    private Module parentModule;
    private String categoryName;
    private int categoryPriority;
    private TaskList tasks;

    public Category(Module parentModule, String categoryName, int categoryPriority) {
        this.parentModule = parentModule;
        this.categoryName = categoryName;
        this.categoryPriority = categoryPriority;
        this.tasks = new TaskList();
    }

    public Module getParentModule() {
        return parentModule;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public int getCategoryPriority() {
        return categoryPriority;
    }

    public TaskList getTasks() {
        return tasks;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public void setCategoryPriority(int categoryPriority) {
        this.categoryPriority = categoryPriority;
    }

    /**
     * Checks if one category has the same category name as another.
     *
     * @param category  The category to check
     * @return  <code>TRUE</code> if they are the same, and <code>FALSE</code> otherwise
     */
    public boolean isSameCategory(Category category) {
        return this.categoryName.equals(category.categoryName);
    }
}

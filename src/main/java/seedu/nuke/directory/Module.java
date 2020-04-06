package seedu.nuke.directory;

import seedu.nuke.data.CategoryManager;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class Module extends Directory {
    private static final Root root = new Root();
    private String moduleCode;
    private String title;
    private String description;
    private CategoryManager categories;

    /**
     * Constructs the module.
     *
     * @param moduleCode
     *  The module code of the module
     * @param title
     *  The title of the module
     * @param description
     *  The description of the module
     */
    public Module(String moduleCode, String title, String description) {
        super(root);
        this.moduleCode = moduleCode.toUpperCase();
        this.title = title;
        this.description = description;
        this.categories = new CategoryManager(this);
    }

    /**
     * Constructs the module but with limited information.
     *
     * @param moduleCode
     *  The module code of the module
     */
    public Module(String moduleCode) {
        this(moduleCode, "", "NIL");
    }

    /**
     * Return the module code of the module.
     *
     * @return
     *  The module code of the module
     */
    public String getModuleCode() {
        return moduleCode;
    }

    /**
     * Returns the title of the module.
     *
     * @return
     *  The title of the module
     */
    public String getTitle() {
        return title;
    }

    /**
     * Returns the description of the module.
     *
     * @return
     *  The description of the module
     */
    public String getDescription() {
        return description;
    }

    /**
     * Returns the Category List of the module.
     *
     * @return
     *  The Category List of the module
     */
    public CategoryManager getCategories() {
        return categories;
    }

    @Override
    public Root getParent() {
        return (Root) this.parent;
    }

    @Override
    public DirectoryLevel getLevel() {
        return DirectoryLevel.MODULE;
    }

    /**
     * Edits the module code of the module.
     *
     * @param moduleCode
     *  The module code of the module
     */
    public void setModuleCode(String moduleCode) {
        this.moduleCode = moduleCode;
    }

    /**
     * Edits the title of the module.
     *
     * @param title
     *  The title of the module
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Edits the description of the module.
     *
     * @param description
     *  The description of the module
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Edit the Category List of the module.
     *
     * @param categories
     *  The Category List of the module
     */
    public void setCategories(CategoryManager categories) {
        this.categories = categories;
    }

    /**
     * Checks if one module has the same module code as another.
     *
     * @param moduleCode
     *  The module code to check
     * @return
     *  <code>TRUE</code> if they are the same, and <code>FALSE</code> otherwise
     */
    public boolean isSameModule(String moduleCode) {
        return this.moduleCode.equalsIgnoreCase(moduleCode);
    }

    /**
     * Returns a string containing the standard Module attributes.
     *
     * @return
     *  A string containing the standard Module attributes
     */
    @Override
    public String toString() {
        return String.format("Module Code: %s\nModule Title: %s\nNumber of Categories: %d\n",
                moduleCode, title, categories.getCategoryList().size());
    }
}

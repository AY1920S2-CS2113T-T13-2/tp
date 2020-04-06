package seedu.nuke.gui.util.tablecreator.basicdirectory;

public class BasicCategory {

    private Integer id;
    private String moduleCode;
    private String categoryName;
    private Integer priority;

    /**
     * Constructs the basic Category class with only its attributes for creating a Category Table.
     *
     * @param id
     *  The id of the category in the list
     * @param moduleCode
     *  The module code of the parent module
     * @param categoryName
     *  The name of the category
     * @param priority
     *  The priority of the category
     */
    public BasicCategory(Integer id, String moduleCode, String categoryName, Integer priority) {
        this.id = id;
        this.moduleCode = moduleCode;
        this.categoryName = categoryName;
        this.priority = priority;
    }

    /**
     * Returns the id of the category.
     *
     * @return
     *  The id of the category
     */
    public Integer getId() {
        return id;
    }

    /**
     * Returns the module code of the parent module.
     *
     * @return
     *  The module code of the parent module
     */
    public String getModuleCode() {
        return moduleCode;
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
    public Integer getPriority() {
        return priority;
    }
}


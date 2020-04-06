package seedu.nuke.gui.util.tablecreator.basicdirectory;

public class BasicTask {
    private Integer id;
    private String moduleCode;
    private String categoryName;
    private String description;
    private String deadline;
    private Integer priority;
    private String doneStatus;

    /**
     * Constructs the basic Category class with only its attributes for creating a Category Table.
     *
     * @param id
     *  The id of the task in the list
     * @param moduleCode
     *  The module code of the parent module
     * @param categoryName
     *  The name of the parent category
     * @param description
     *  The description of the task
     * @param deadline
     *  The deadline of the task
     * @param priority
     *  The priority of the task
     * @param doneStatus
     *  The done status of the task
     */
    public BasicTask(Integer id, String moduleCode, String categoryName, String description, String deadline,
                     Integer priority, String doneStatus) {
        this.id = id;
        this.moduleCode = moduleCode;
        this.categoryName = categoryName;
        this.description = description;
        this.deadline = deadline;
        this.priority = priority;
        this.doneStatus = doneStatus;
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
     * Returns the description of the task.
     *
     * @return
     *  The description of the task
     */
    public String getDescription() {
        return description;
    }

    /**
     * Returns the deadline of the task.
     *
     * @return
     *  The deadline of the task
     */
    public String getDeadline() {
        return deadline;
    }

    /**
     * Returns the priority of the task.
     *
     * @return
     *  The priority of the task
     */
    public Integer getPriority() {
        return priority;
    }

    /**
     * Returns the done status of the task.
     *
     * @return
     *  The done status of the task
     */
    public String getDoneStatus() {
        return doneStatus;
    }
}
